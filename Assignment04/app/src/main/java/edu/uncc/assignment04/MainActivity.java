package edu.uncc.assignment04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.uncc.assignment04.fragments.DemographicFragment;
import edu.uncc.assignment04.fragments.IdentificationFragment;
import edu.uncc.assignment04.fragments.MainFragment;
import edu.uncc.assignment04.fragments.ProfileFragment;
import edu.uncc.assignment04.fragments.SelectEducationFragment;
import edu.uncc.assignment04.fragments.SelectIncomeFragment;
import edu.uncc.assignment04.fragments.SelectLivingStatusFragment;
import edu.uncc.assignment04.fragments.SelectMaritalStatusFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener,IdentificationFragment.IdentificationFragmentListener, SelectEducationFragment.FragmentSetEducationListener ,
        DemographicFragment.FragmentDemographicListener , SelectIncomeFragment.FragmentSetIncomeListener,SelectMaritalStatusFragment.FragmentSetMaritalStatusListener,
        SelectLivingStatusFragment.FragmentSetLivingStatusListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new MainFragment())
                .commit();
    }

    @Override
    public void gotoIdentificationFragment() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new IdentificationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendProfile(Profile profile) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, DemographicFragment.newInstance(profile),"demographic_fragment_tag")
                .addToBackStack(null)
                .commit();
    }



    @Override
    public void setEducation(String education) {
       DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic_fragment_tag");
        if (fragment != null) {
            fragment.displayEducation(education);
        }

       getSupportFragmentManager().popBackStack();

    }
    public void setIncome(String income)
    {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic_fragment_tag");
        if(fragment !=null) {
            fragment.displayIncome(income);
        }

        getSupportFragmentManager().popBackStack();
    }
    @Override
    public void setMaritalStatus(String mStatus) {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic_fragment_tag");
        if(fragment != null)
        {
            fragment.displayMaritalStatus(mStatus);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void setLivingStatus(String lStatus) {
        DemographicFragment fragment = (DemographicFragment) getSupportFragmentManager().findFragmentByTag("demographic_fragment_tag");
        if(fragment !=null)
        {
            fragment.displayLivingStatus(lStatus);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectEducationFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new SelectEducationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectIncomeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectIncomeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectMaritalStatusFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new SelectMaritalStatusFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectLivingStatusFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new SelectLivingStatusFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoProfileFragment(Profile profile) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,ProfileFragment.newInstance(profile))
                .addToBackStack(null)
                .commit();
    }


}