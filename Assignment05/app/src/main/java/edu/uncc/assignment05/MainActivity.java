package edu.uncc.assignment05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import edu.uncc.assignment05.fragments.AddUserFragment;
import edu.uncc.assignment05.fragments.SelectAgeFragment;
import edu.uncc.assignment05.fragments.SelectGenderFragment;
import edu.uncc.assignment05.fragments.SelectGroupFragment;
import edu.uncc.assignment05.fragments.SelectSortFragment;
import edu.uncc.assignment05.fragments.SelectStateFragment;
import edu.uncc.assignment05.fragments.UsersFragment;
import edu.uncc.assignment05.models.User;

public class MainActivity extends AppCompatActivity implements UsersFragment.FragmentUserListener , AddUserFragment.FragmentAddUserListener , SelectGenderFragment.FragmentSelectGenderListener
, SelectAgeFragment.selectedAgeListener, SelectGroupFragment.SelectGroupListener, SelectStateFragment.SelectStateListener, SelectSortFragment.SelectSortListener {

   private ArrayList<User> mUsers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new UsersFragment() , "User-Fragment")
                .commit();
    }

    @Override
    public void gotoAddUserFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new AddUserFragment(),"Add-User-Tag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectSortFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new SelectSortFragment(),"Sort-User-Tag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectAgeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new SelectAgeFragment(),"Select-Age-Tag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectGenderFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new SelectGenderFragment(),"Select-Gender-Tag")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void gotoSelectGroupFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectGroupFragment(),"Select-Group-Tag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectStateFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,new SelectStateFragment(),"Select-State-Tag")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendUser(User user) {
        mUsers.add(user);
        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("User-Fragment");
        if(fragment!= null)
        {
            fragment.setUsers( mUsers);
        }

        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void sendGender(String gender) {

        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("Add-User-Tag") ;
        if(fragment != null)
        {
            fragment.setGender(gender);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSelectedAge(String selectedAge) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("Add-User-Tag");
        if(fragment!= null)
        {
            fragment.setAge(selectedAge);
        }

        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendSelectedGroup(String selectedGroup) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("Add-User-Tag");
        if(fragment!=null)
        {
            fragment.setGroup(selectedGroup);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendState(String selectedState) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("Add-User-Tag");
        if(fragment!= null)
        {
            fragment.setState(selectedState);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sortUserList(String sortType) {
        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("User-Fragment");
        if(fragment != null)
        {
            fragment.setType(sortType);
        }
        getSupportFragmentManager().popBackStack();
    }
}