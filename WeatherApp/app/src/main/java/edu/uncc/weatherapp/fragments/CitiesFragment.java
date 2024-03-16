package edu.uncc.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.weatherapp.R;
import edu.uncc.weatherapp.databinding.FragmentCitiesBinding;
import edu.uncc.weatherapp.models.City;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CitiesFragment extends Fragment {
    public CitiesFragment() {
        // Required empty public constructor
    }
    FragmentCitiesBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<City> cities = new ArrayList<>();
    String[] cityName;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCitiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        getCities();

    }



    void getCities(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/cities")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                   if(response.isSuccessful()) {
                       String body = response.body().string();
                       Log.d("demo", "onResponse: "+body);

                       try {
                           JSONObject rootJson = new JSONObject(body);
                           JSONArray citiesJson = rootJson.getJSONArray("cities");
                           cities.clear();

                           cityName = new String[citiesJson.length()];

                           for (int i = 0; i < citiesJson.length(); i++) {
                               City city = new City(citiesJson.getJSONObject(i));
                               cities.add(city);
                               cityName[i] = city.getName()+", "+city.getState();

                           }

                           getActivity().runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,cityName);
                                   binding.listView.setAdapter(adapter);
                                   adapter.notifyDataSetChanged();

                                   binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                       @Override
                                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                           City city = cities.get(position);
                                           mListener.gotoWeatherForcastFragment(city);
                                       }
                                   });
                               }
                           });


                       } catch (JSONException e) {
                           throw new RuntimeException(e);
                       }


                   }else{

                   }

            }
        });
    }


    FragmentCityListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentCityListener) context;
    }

    public interface FragmentCityListener{
        void gotoWeatherForcastFragment(City city);
    }
}