package com.example.assignment12;

import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.example.assignment12.databinding.FragmentAnyChartBinding;
import com.example.assignment12.databinding.FragmentVisualizeProgressBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VisualizeProgressFragment extends Fragment {



    public VisualizeProgressFragment() {
        // Required empty public constructor
    }


    FragmentVisualizeProgressBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVisualizeProgressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    AppDatabase db;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yy HH:mm", Locale.getDefault());


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "details-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();


        List<DetailsModel> chartData = db.dao().getAll();

        setFirstChart(chartData);
        setSecondChart(chartData);
        setThirdChart(chartData);
        setFourthChart(chartData);

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.backToMainFragment();
            }
        });


    }

    private void setFirstChart(List<DetailsModel> chartsData){
        APIlib.getInstance().setActiveAnyChartView(binding.anyChartView1);
        Cartesian line = AnyChart.line();
        ArrayList<DataEntry> data = new ArrayList<>();





        for (DetailsModel tempChartData : chartsData) {
            String formattedDate = dateFormat.format(tempChartData.getDate());
            data.add(new ValueDataEntry(formattedDate, Double.parseDouble(tempChartData.getSleepHrs())));
        }


        Log.d("TAG", "onViewCreated: Sleep Hours"+data);

        line.data(data);
        line.xAxis(0).title("Date");
        line.yAxis(0).title("Sleep Hours");

        binding.anyChartView1.setChart(line);

    }

    private void setSecondChart(List<DetailsModel> chartsData){

        APIlib.getInstance().setActiveAnyChartView(binding.anyChartView2);

        Cartesian line2 = AnyChart.line();

        ArrayList<DataEntry> data2 = new ArrayList<>();



        for (DetailsModel tempChartData : chartsData) {
            String formattedDate = dateFormat.format(tempChartData.getDate());
            data2.add(new ValueDataEntry(formattedDate, Double.parseDouble(tempChartData.getExerciseTime())));
        }

        Log.d("TAG", "onViewCreated: Exercise "+data2);

        line2.data(data2);
        line2.xAxis(0).title("Date");
        line2.yAxis(0).title("Exercise Time");

        binding.anyChartView2.setChart(line2);

    }

    private void setThirdChart(List<DetailsModel> chartsData){
        APIlib.getInstance().setActiveAnyChartView(binding.anyChartView3);
        Cartesian line3 = AnyChart.line();
        ArrayList<DataEntry> data3 = new ArrayList<>();


        for (DetailsModel tempChartData : chartsData) {
            int sleepQualityValue;
            switch (tempChartData.getSleepQuality())
            {
                case "Excellent": sleepQualityValue = 5;
                    break;
                case "Very good":  sleepQualityValue = 4;
                    break;
                case "Good": sleepQualityValue = 3;
                    break;
                case "Fair":  sleepQualityValue = 2;
                    break;
                case "Poor": sleepQualityValue = 1;
                    break;

                default: sleepQualityValue = 0;
                    break;
            }
            String formattedDate = dateFormat.format(tempChartData.getDate());
            data3.add(new ValueDataEntry(formattedDate,sleepQualityValue ) );
            Log.d("TAG", "setThirdChart: "+sleepQualityValue);
        }

        Log.d("TAG", "onViewCreated: Sleep Quality"+data3);

        line3.data(data3);
        line3.xAxis(0).title("Date");
        line3.yAxis(0).title("Sleep Quality");

        binding.anyChartView3.setChart(line3);

    }

    private void setFourthChart(List<DetailsModel> chartsData){
        APIlib.getInstance().setActiveAnyChartView(binding.anyChartView4);
        Cartesian line4 = AnyChart.line();
        ArrayList<DataEntry> data = new ArrayList<>();

        for (DetailsModel tempChartData : chartsData) {
            String formattedDate = dateFormat.format(tempChartData.getDate());
            data.add(new ValueDataEntry(formattedDate, tempChartData.getWeight()));
        }

        Log.d("TAG", "onViewCreated: Weight"+data);

        line4.data(data);
        line4.xAxis(0).title("Date");
        line4.yAxis(0).title("Weight");

        binding.anyChartView4.setChart(line4);
    }


    FragmentVisualizeProgressListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener =(FragmentVisualizeProgressListener) context;
    }

    public interface FragmentVisualizeProgressListener{
        void backToMainFragment();
    }


}