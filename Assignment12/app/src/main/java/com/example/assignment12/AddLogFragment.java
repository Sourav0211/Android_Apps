package com.example.assignment12;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.assignment12.databinding.FragmentAddLogBinding;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddLogFragment extends Fragment {

    public AddLogFragment() {
        // Required empty public constructor
    }

    FragmentAddLogBinding binding;
    Date date;
    String sleepHrs, sleepQuality, exerciseTime;

    public void setSleepHrs(String sleepHrs) {
        this.sleepHrs = sleepHrs;
    }

    public void setSleepQuality(String sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public void setExerciseTime(String exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddLogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(sleepHrs == null){
            binding.textViewSleepHrs.setText("Not Set");
        }else{
            binding.textViewSleepHrs.setText(sleepHrs);
        }

        if(sleepQuality == null){
            binding.textViewQualitySleep.setText("Not Set");
        }else{
            binding.textViewQualitySleep.setText(sleepQuality);
        }

        if(exerciseTime == null){
            binding.textViewExerciseTime.setText("Not Set");
        }else{
            binding.textViewExerciseTime.setText(exerciseTime);
        }

        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
            binding.textViewDate.setText(sdf.format(date));
        }
        binding.buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateTimePicker();
            }
        });
        binding.buttonHrsOfSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectHrsOfSleep();
            }
        });
        binding.buttonQualitySleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectQuality();
            }
        });
        binding.buttonExerciseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectExerciseTime();
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancel();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.textViewDate.getText().toString().equals("Not Set")){
                    Toast.makeText(getActivity(), "Enter date", Toast.LENGTH_SHORT).show();
                } else if (binding.textViewSleepHrs.getText().toString().equals("Not Set")) {
                    Toast.makeText(getActivity(), "Select Sleep Hours", Toast.LENGTH_SHORT).show();
                } else if (binding.textViewQualitySleep.getText().toString().equals("Not Set")) {
                    Toast.makeText(getActivity(), "Select Quality of Sleep", Toast.LENGTH_SHORT).show();
                } else if (binding.textViewExerciseTime.getText().toString().equals("Not Set")) {
                    Toast.makeText(getActivity(), "Select Exercise Time", Toast.LENGTH_SHORT).show();
                } else if (binding.editTextNumber.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Weight", Toast.LENGTH_SHORT).show();
                } else{
//                    date = binding.textViewDate.getText().toString();
                    int weight = Integer.parseInt(binding.editTextNumber.getText().toString());

                    AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "details-db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();

                    //String date, String sleepHrs, String sleepQuality, String exerciseTime, int weight
                    DetailsModel detailsModel = new DetailsModel(date, sleepHrs, sleepQuality, exerciseTime, weight);
                    db.dao().insertAll(detailsModel);
                    mListener.Submit();
                }
            }
        });

    }
    private void openDateTimePicker() {

        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentDate.get(Calendar.MINUTE);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Calendar selectedDateTime = Calendar.getInstance();
                        selectedDateTime.set(year, month, day, hourOfDay, minute);


                        if (selectedDateTime.compareTo(currentDate) > 0) {
                            Toast.makeText(getContext(), "Future dates are not allowed", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day, hourOfDay, minute);
                        date = calendar.getTime();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
                        String formattedDate = sdf.format(date);

                        binding.textViewDate.setText(formattedDate);

                        binding.textViewDate.setText(formattedDate);
                    }
                }, currentHour, currentMinute, DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        }, currentYear, currentMonth, currentDay);

        datePickerDialog.getDatePicker().setMaxDate(currentDate.getTimeInMillis());
        datePickerDialog.show();
    }
    AddLogFragListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddLogFragListener) context;
    }

    public interface AddLogFragListener{
        void gotoSelectHrsOfSleep();
        void gotoSelectQuality();
        void gotoSelectExerciseTime();
        void cancel();
        void Submit();

    }
}