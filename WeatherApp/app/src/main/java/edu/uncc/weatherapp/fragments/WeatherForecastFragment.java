package edu.uncc.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.uncc.weatherapp.R;
import edu.uncc.weatherapp.databinding.ForecastListItemBinding;
import edu.uncc.weatherapp.databinding.FragmentWeatherForecastBinding;
import edu.uncc.weatherapp.models.City;
import edu.uncc.weatherapp.models.Forecast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WeatherForecastFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private final OkHttpClient client = new OkHttpClient();
    private City mCity;
    ArrayList<Forecast> forecasts = new ArrayList<>();
    String url;
    ForecastAdapter adapter;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }


    FragmentWeatherForecastBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getForcastUrl();
    }

    void getForcastUrl()
    {
        double lat =mCity.getLat();
        double lng =mCity.getLng();
        Request request = new Request.Builder()
                .url("https://api.weather.gov/points/" + lat + "," + lng)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful())
                {
                    String body = response.body().string();
                    try {
                        JSONObject rootJson = new JSONObject(body);
                        JSONObject propertiesJson = rootJson.getJSONObject("properties");
                        url = propertiesJson.getString("forecast");

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getForcastDetails();
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    Log.d("URL", "onResponse: "+ url);

                }


            }
        });

    }

    void getForcastDetails(){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    String body = response.body().string();
                    try {
                        JSONObject details= new JSONObject(body);
                        JSONObject propertiesJson = details.getJSONObject("properties");
                        JSONArray periodsJson = propertiesJson.getJSONArray("periods");
//                        Log.d("periods", "onResponse: "+ periodsJson);

                        for (int i = 0; i <periodsJson.length() ; i++) {

                            JSONObject temp = periodsJson.getJSONObject(i);
                            Forecast forecast = new Forecast();
                            forecast.setStartTime(temp.getString("startTime"));
                            forecast.setTemperature(temp.getString("temperature"));
                            forecast.setShortforcast(temp.getString("shortForecast"));
                            forecast.setWindspeed(temp.getString("windSpeed"));
                            forecast.setIcon(temp.getString("icon"));
                            JSONObject humidity = temp.getJSONObject("relativeHumidity");
                            forecast.setHumidy(humidity.getString("value"));
                            Log.d("Forecast", "onResponse: "+forecast);

                            forecasts.add(forecast);

                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                adapter= new ForecastAdapter();
                                binding.recyclerView.setAdapter(adapter);
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>{
    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ForecastListItemBinding vhBinding = ForecastListItemBinding.inflate(getLayoutInflater(),parent,false);
        return new ForecastViewHolder(vhBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast forecast = forecasts.get(position);
        holder.setUpUI(forecast);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder{
        ForecastListItemBinding mBinding;
        public ForecastViewHolder(ForecastListItemBinding vhBinding) {
            super(vhBinding.getRoot());
            mBinding = vhBinding;

        }

        public void setUpUI(Forecast forecast)
        {
            mBinding.textViewTemperature.setText(forecast.getTemperature());
            mBinding.textViewDateTime.setText(forecast.getStartTime());
            mBinding.textViewForecast.setText(forecast.getShortforcast());
            mBinding.textViewHumidity.setText(forecast.getHumidy());
            mBinding.textViewWindSpeed.setText((forecast.getWindspeed()));
            Picasso.get().load(forecast.getIcon()).into(mBinding.imageView);
        }


    }
}

}