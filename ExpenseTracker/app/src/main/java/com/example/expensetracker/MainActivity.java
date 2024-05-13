package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements EnterPinFragment.EnterPinListener, SetupPinFragment.SetupPinListener , ExpenseFragment.ExpenseFragmentListener, AddExpenseFragment.AddExpenseListener {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("auth_data", Context.MODE_PRIVATE);
        String authPin = sharedPreferences.getString("auth_pin","null");
        if(authPin.equals("null"))
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerView, new SetupPinFragment())
                    .commit();
            Log.d("TAG", "onCreate: Setup "+authPin);

        }else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerView,new EnterPinFragment())
                    .commit();
            Log.d("TAG", "onCreate: Setup "+authPin);
        }


    }

    @Override
    public void gotoExpenseFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new ExpenseFragment())
                .commit();
    }

    @Override
    public void setupCompleted(String pin) {

        SharedPreferences sharedPreferences = getSharedPreferences("auth_data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("auth_pin", pin);
        Log.d("TAG", "setupCompleted: " +pin);
        editor.apply();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new ExpenseFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSetUpFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SetupPinFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoAddExpenseFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new AddExpenseFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void doneAddingExpense() {
        getSupportFragmentManager().popBackStack();
    }
}