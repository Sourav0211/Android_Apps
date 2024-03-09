package edu.uncc.assignment06;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import java.util.ArrayList;

import edu.uncc.assignment06.fragments.AddTaskFragment;
import edu.uncc.assignment06.fragments.SelectCategoryFragment;
import edu.uncc.assignment06.fragments.SelectPriorityFragment;
import edu.uncc.assignment06.fragments.TaskDetailsFragment;
import edu.uncc.assignment06.fragments.TasksFragment;
import edu.uncc.assignment06.models.Data;
import edu.uncc.assignment06.models.Task;

public class MainActivity extends AppCompatActivity implements TasksFragment.TasksListener, AddTaskFragment.AddFragmentListener ,
        SelectCategoryFragment.SelectCategoryListener , SelectPriorityFragment.SelectPriorityListener
, TaskDetailsFragment.TaskDetailsListener {

    private ArrayList<Task> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mTasks.addAll(Data.sampleTestTasks); //adding for testing

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new TasksFragment(),"TASK_FRAGMENT")
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
    public void gotoSelectCategory() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectCategoryFragment(),"SELECT_CATEGORY")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectPriority() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectPriorityFragment(),"SELECT_PRIORITY")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void submitTask(Task task) {

        mTasks.add(task);
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void sendCategory(String category) {
        AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("ADD_TASK");

        if(addTaskFragment != null){
            addTaskFragment.setSelectedCategory(category);

        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelCategory() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendPriority(String priority) {
        AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("ADD_TASK");

        if(addTaskFragment != null){
            addTaskFragment.setSelectedPriority(priority);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelPriority() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void deleteTask(Task task) {
        mTasks.remove(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void back() {
        getSupportFragmentManager().popBackStack();
    }
}