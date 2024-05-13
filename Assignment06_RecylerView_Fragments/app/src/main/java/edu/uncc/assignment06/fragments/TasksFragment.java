package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentTasksBinding;
import edu.uncc.assignment06.databinding.TaskListItemBinding;
import edu.uncc.assignment06.models.Task;


public class TasksFragment extends Fragment {


    private ArrayList<Task> mTasks = new ArrayList<>();

    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentTasksBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    TaskAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTasks = mListener.getAllTasks();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter= new TaskAdapter();
        binding.recyclerView.setAdapter(adapter);



        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddTask();
            }
        });

        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return o2.getPriority() - o1.getPriority();
                    }
                });
                adapter.notifyDataSetChanged();
                binding.textViewSortIndicator.setText("Sort by Priority(ASC)");
            }
        });

        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mTasks, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return o1.getPriority() - o2.getPriority();
                    }
                });
                adapter.notifyDataSetChanged();
                binding.textViewSortIndicator.setText("Sort by Priority(DSC)");
            }
        });


    }



    TasksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TasksListener) context;

    }


    public interface TasksListener{
        ArrayList<Task> getAllTasks();
        void gotoAddTask();
        void gotoTaskDetails(Task task);
    }

    class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TaskListItemBinding binding = TaskListItemBinding.inflate(getLayoutInflater(),parent,false);
            return new TaskViewHolder(binding);
        }
        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.setupUI(task);
        }
        @Override
        public int getItemCount() {
            return mTasks.size();
        }
        class TaskViewHolder extends RecyclerView.ViewHolder{
            TaskListItemBinding mbinding;
            Task mTask;
            public TaskViewHolder(TaskListItemBinding binding) {
                super(binding.getRoot());
                mbinding = binding;
            }
            public void setupUI(Task task)
            {
                mbinding.textViewName.setText(task.getName());
                mbinding.textViewCategory.setText(task.getCategory());
                mbinding.textViewPriority.setText(task.getPriorityStr());

                mTask = task;

                mbinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTasks.remove(mTask);
                        notifyDataSetChanged();
                    }
                });

                mbinding.taskItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoTaskDetails(mTask);
                    }
                });
            }

        }
    }







}