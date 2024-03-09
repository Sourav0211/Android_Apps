package edu.uncc.evaluation02;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import edu.uncc.evaluation02.fragments.AddTaskFragment;
import edu.uncc.evaluation02.fragments.SelectCategoryFragment;
import edu.uncc.evaluation02.fragments.TaskDetailsFragment;
import edu.uncc.evaluation02.fragments.TasksFragment;
import edu.uncc.evaluation02.models.Data;
import edu.uncc.evaluation02.models.Task;

public class MainActivity extends AppCompatActivity implements TasksFragment.TasksListener, AddTaskFragment.AddTaskListener, SelectCategoryFragment.SelectCategoryListener, TaskDetailsFragment.TaskDetailsListener {

    private ArrayList<Task> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mTasks.addAll(Data.sampleTestTasks); //adding for testing

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView,new TasksFragment(),"TASK_FRAGMENT")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return mTasks;
    }

    @Override
    public void gotoAddTask() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddTaskFragment(),"ADD_TASK")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoTaskDetails(Task task) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, TaskDetailsFragment.newInstance(task))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void clearAll() {
        mTasks.clear();
    }

    @Override
    public void gotoSelectCategoryFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectCategoryFragment(),"SELECT_CATEGORY")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendTask(Task task) {

        mTasks.add(task);
//        TasksFragment fragment = (TasksFragment) getSupportFragmentManager().findFragmentByTag("TASK_FRAGMENT");
//        if(fragment!=null)
//        {
//            fragment.setTask(mTasks);
//        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendCategory(String category) {
        AddTaskFragment fragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("ADD_TASK");
        if(fragment!=null)
        {
            fragment.setCategoryView(category);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelSelectCategory() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void delete(Task mParam1) {
        mTasks.remove(mParam1);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void back() {
        getSupportFragmentManager().popBackStack();
    }
}