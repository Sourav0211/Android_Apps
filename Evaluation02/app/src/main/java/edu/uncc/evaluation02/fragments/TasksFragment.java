package edu.uncc.evaluation02.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.databinding.FragmentTasksBinding;
import edu.uncc.evaluation02.models.Task;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link TasksFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TasksFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
   private ArrayList<Task> mTasks = new ArrayList<>();


//   public void setTask(ArrayList<Task> task)
//   {
//       this.mTasks =task;
//   }

    public TasksFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment TasksFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static TasksFragment newInstance(String param1, String param2) {
//        TasksFragment fragment = new TasksFragment();
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

    FragmentTasksBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater,container,false);
        return binding.getRoot();
        // Inflate the layout for this fragment
    }

    TasksAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTasks = mListener.getAllTasks();
        adapter = new TasksAdapter(getActivity(),mTasks);
        binding.listView.setAdapter(adapter);

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddTask();
            }
        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAll();
                adapter.notifyDataSetChanged();
            }
        });

       binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               binding.textViewSortIndicator.setText("Sort by Priority(DSCE)");
               sortAsc();
               adapter.notifyDataSetChanged();
           }
       });

       binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               binding.textViewSortIndicator.setText("Sort by Priority(ASCE)");
               sortDsc();
               adapter.notifyDataSetChanged();
           }
       });

       binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Task viewTask = mTasks.get(position);
               mListener.gotoTaskDetails(viewTask);
           }
       });
    }


    public void sortAsc()
    {
        Collections.sort(mTasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o2.getPriority() - o1.getPriority();
            }
        });
    }

    public void sortDsc()
    {
        Collections.sort(mTasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });
    }





    TasksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TasksListener) context;
    }

    //TODO: The interface for the TasksFragment
    public interface TasksListener{
        ArrayList<Task> getAllTasks();
        void gotoAddTask();
        void gotoTaskDetails(Task viewTask);
        void clearAll();
    }




    class TasksAdapter extends ArrayAdapter<Task>{


        public TasksAdapter(@NonNull Context context, @NonNull List<Task> objects) {
            super(context, R.layout.task_list_item,objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item,parent,false);
            }

            Task task = getItem(position);

            TextView textViewCategory = convertView.findViewById(R.id.textViewCategory);
            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewPriority = convertView.findViewById(R.id.textViewPriority);

            textViewCategory.setText(task.getCategory());
            textViewName.setText(task.getName());
           textViewPriority.setText(task.getPriorityStr());

            return convertView;
        }
    }
}