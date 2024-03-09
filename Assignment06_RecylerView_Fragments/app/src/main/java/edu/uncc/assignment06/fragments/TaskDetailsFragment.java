package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentTaskDetailsBinding;
import edu.uncc.assignment06.models.Task;


public class TaskDetailsFragment extends Fragment {


    private static final String ARG_PARAM2 = "TASK";



    private Task mParam2;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }


    public static TaskDetailsFragment newInstance(Task param2) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();

        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mParam2 = (Task) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewName.setText(mParam2.getName());
        binding.textViewCategory.setText(mParam2.getCategory());
        binding.textViewPriority.setText(mParam2.getPriorityStr());

        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteTask(mParam2);
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.back();
            }
        });

    }

    FragmentTaskDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
        // Inflate the layout for this fragment

    }

    TaskDetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TaskDetailsListener) context;
    }

    public interface TaskDetailsListener{
        void deleteTask(Task task);

        void back();
    }
}