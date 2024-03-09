package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentAddTaskBinding;
import edu.uncc.assignment06.models.Task;

public class AddTaskFragment extends Fragment {


    public AddTaskFragment() {
        // Required empty public constructor
    }
    String category="N/A";
    String priority="N/A";

    String name;
    public void setSelectedCategory(String category)
    {
        this.category =category;
    }

    public void setSelectedPriority(String priority)
    {
        this.priority = priority;

    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentAddTaskBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    Task task;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewCategory.setText(category);
        binding.textViewPriority.setText(priority);




        binding.buttonSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCategory();
            }
        });

        binding.buttonSelectPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectPriority();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = binding.editTextName.getText().toString();

                if(name.isEmpty() || priority.equals("N/A") || category.equals("N/A"))
                {
                    Toast.makeText(getContext(),"Enter All Values",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (priority.equals("Very High")) {
                        mListener.submitTask(new Task(name, category, priority, 1));
                    } else if (priority.equals("High")) {
                        mListener.submitTask(new Task(name, category, priority, 2));
                    } else if (priority.equals("Medium")) {
                        mListener.submitTask(new Task(name, category, priority, 3));
                    } else if (priority.equals("Low")) {
                        mListener.submitTask(new Task(name, category, priority, 4));
                    } else if (priority.equals("Very Low")) {
                        mListener.submitTask(new Task(name, category, priority, 5));
                    }
                }

            }
        });


    }

    AddFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddFragmentListener) context;
    }

    public interface AddFragmentListener{
        void gotoSelectCategory();
        void gotoSelectPriority();

        void submitTask(Task task);

    }

}