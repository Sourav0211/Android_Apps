package edu.uncc.evaluation02.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.databinding.FragmentTaskDetailsBinding;
import edu.uncc.evaluation02.models.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_TASK = "ARG_PARAM_TASK";

    // TODO: Rename and change types of parameters
    private Task mParam1;


    public TaskDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TaskDetailsFragment newInstance(Task param1) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_TASK, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 =(Task) getArguments().getSerializable(ARG_PARAM_TASK);
        }
    }

    FragmentTaskDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTaskDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewName.setText(mParam1.getName());
        binding.textViewCategory.setText(mParam1.getCategory());
        binding.textViewPriority.setText(mParam1.getPriorityStr());

        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.delete(mParam1);

            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.back();
            }
        });

    }

    TaskDetailsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TaskDetailsListener) context;
    }

    public interface TaskDetailsListener{
        void delete(Task mParam1);
        void back();
    }



//    class DetailsAdapter extends ArrayAdapter<Task>{
//
//
//        public DetailsAdapter(@NonNull Context context, @NonNull List<Task> objects) {
//            super(context, R.layout.fragment_task_details,objects);
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//            if(convertView == null)
//            {
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_task_details,parent,false);
//            }
//
//            Task task = getItem(position);
//
//            TextView textViewCategory = convertView.findViewById(R.id.textViewCategory);
//            TextView textViewName = convertView.findViewById(R.id.textViewName);
//            TextView textViewPriority = convertView.findViewById(R.id.textViewPriority);
//
//            textViewCategory.setText(task.getCategory());
//            textViewName.setText(task.getName());
//            textViewPriority.setText(task.getPriorityStr());
//
//            return convertView;
//        }
//    }
}