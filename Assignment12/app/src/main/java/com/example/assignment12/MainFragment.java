package com.example.assignment12;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.assignment12.databinding.DetailItemBinding;
import com.example.assignment12.databinding.FragmentMainBinding;

import java.util.ArrayList;
import java.util.Collections;

public class MainFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }
//
//    public static MainFragment newInstance(String param1, String param2) {
//        MainFragment fragment = new MainFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    FragmentMainBinding binding;
    DetailsAdapter adapter;
    AppDatabase db;
    private ArrayList<DetailsModel> mdetails = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DetailsAdapter();
        binding.recyclerView.setAdapter(adapter);

        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "details-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        loadandDisplayData();

        binding.buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddNewLog();
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoVisualize();
            }
        });
    }

    void loadandDisplayData(){

        mdetails.clear();
        mdetails.addAll(db.dao().getAll());
        Collections.reverse(mdetails);

        adapter.notifyDataSetChanged();
    }
    class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>{

        @NonNull
        @Override
        public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            DetailItemBinding mbinding = DetailItemBinding.inflate(getLayoutInflater(), parent, false);
            DetailsViewHolder holder = new DetailsViewHolder(mbinding);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
            DetailsModel detailsModel = mdetails.get(position);
            holder.setupUI(detailsModel);
        }

        @Override
        public int getItemCount() {
            return mdetails.size();
        }

        class DetailsViewHolder extends RecyclerView.ViewHolder{
            DetailItemBinding mBinding;
            public DetailsViewHolder(DetailItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(DetailsModel detailsModel){

                mBinding.textViewhrs.setText("Steep Hrs: " + detailsModel.getSleepHrs());
                mBinding.textViewQualityS.setText("Sleep Quality: " + detailsModel.getSleepQuality());
                mBinding.textViewExerciseT.setText("Exercise Time: " + detailsModel.getExerciseTime());
                mBinding.textViewWeight.setText("Weight in pounds: " + String.valueOf(detailsModel.getWeight()));
                mBinding.textViewDate.setText("Date: " + detailsModel.getDate());

                mBinding.buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.dao().delete(detailsModel);
                        loadandDisplayData();
                    }
                });
            }
        }
    }

    MainFragListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (MainFragListener) context;
    }

    interface MainFragListener{
        void gotoAddNewLog();
        void gotoVisualize();

    }


}