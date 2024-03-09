package edu.uncc.evaluation02.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.databinding.FragmentAddTaskBinding;
import edu.uncc.evaluation02.databinding.FragmentTasksBinding;
import edu.uncc.evaluation02.models.Task;

//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AddTaskFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class AddTaskFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;


    String category="N/A",name,priority;
    Task task;
    int priorityValue;
    RadioGroup radioGroup;

    public void setCategoryView(String category)
    {
        this.category = category;
    }





    public AddTaskFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddTaskFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddTaskFragment newInstance(String param1, String param2) {
//        AddTaskFragment fragment = new AddTaskFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }


    FragmentAddTaskBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater,container,false);
        return binding.getRoot();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewCategory.setText(category);
        binding.buttonSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCategoryFragment();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = binding.editTextName.getText().toString();
                int select = binding.radioGroup.getCheckedRadioButtonId();
//                RadioButton radioButton = binding.radioGroup.findViewById(select);
                priority = "Very High";
                priorityValue = 5;

                if(select == R.id.radioButtonHigh)
                {
                    priority = "High";
                    priorityValue=4;
                }else if(select == R.id.radioButtonMedium)
                {
                    priority = "Medium";
                    priorityValue = 3;
                }else if(select == R.id.radioButtonLow)
                {
                    priority = "Low";
                    priorityValue = 2;
                }else if(select == R.id.radioButtonVeryLow)
                {
                    priority = "Very Low";
                    priorityValue = 1;
                }

                if(category.equals("N/A") || name.isEmpty())
                {
                    Toast.makeText(getActivity(),"Enter all the values",Toast.LENGTH_SHORT).show();
                }
                else{
                    task = new Task(name,category,priority,priorityValue);
                    mListener.sendTask(task);
                }


            }
        });


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddTaskListener) context;
    }

    AddTaskListener mListener;
    public interface AddTaskListener{
        void gotoSelectCategoryFragment();

        void sendTask(Task task);
    }

}