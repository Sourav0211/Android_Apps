package com.example.fragments_01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements BACFragment.BACFragmentListener,SetWeightFragment.SetWeightFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView,new BACFragment(),"BAC-fragment")
                .commit();

    }

    @Override
    public void gotoSetWight() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new SetWeightFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setWeight(int weight) {
        BACFragment fragment = (BACFragment) getSupportFragmentManager().findFragmentByTag("BAC-fragment");
        if(fragment !=null)
        {
            fragment.displayWeight(weight);
        }
        getSupportFragmentManager().popBackStack();
    }
}