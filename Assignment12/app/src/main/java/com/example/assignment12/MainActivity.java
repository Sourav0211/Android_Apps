//Name: Sourav Shetye , Mrudul Shirodkar
//Assignment12




package com.example.assignment12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragListener,
    AddLogFragment.AddLogFragListener, SelectSleepHrsFragment.SelectHrsOfSleepListener,
    SelectQualityFragment.QualityListener, SelectExerciseTimeFragment.ExerciseTimeListener, VisualizeProgressFragment.FragmentVisualizeProgressListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new MainFragment())
                .commit();
    }

    @Override
    public void gotoAddNewLog() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddLogFragment(), "addnew")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoVisualize() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new VisualizeProgressFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectHrsOfSleep() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectSleepHrsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectQuality() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectQualityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectExerciseTime() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectExerciseTimeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void Submit() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendExerciseTime(String hrs) {
        AddLogFragment addLogFragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("addnew");

        if(addLogFragment != null){
            addLogFragment.setExerciseTime(hrs);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void sendQualitySelection(String quality) {
        AddLogFragment addLogFragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("addnew");

        if(addLogFragment != null){
            addLogFragment.setSleepQuality(quality);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void sendSelectHrsOfSleep(String hrs) {
        AddLogFragment addLogFragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("addnew");

        if(addLogFragment != null){
            addLogFragment.setSleepHrs(hrs);
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void backToMainFragment() {
        getSupportFragmentManager().popBackStack();
    }
}