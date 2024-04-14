package edu.uncc.giftlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import edu.uncc.giftlistapp.auth.LoginFragment;
import edu.uncc.giftlistapp.auth.RegisterFragment;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, RegisterFragment.SignUpListener,
        GiftListsFragment.GiftListsListener, CreateGiftListFragment.CreateGiftListListener, GiftListFragment.GiftListListener,
        TagsFragment.TagsListener, FilterFragment.FilterListener {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(mAuth.getCurrentUser() == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new LoginFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new GiftListsFragment())
                    .commit();
        }
    }


    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new RegisterFragment())
                .commit();
    }

    @Override
    public void login() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void authCompleted() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new GiftListsFragment())
                .commit();
    }

    @Override
    public void gotoAddNewGiftList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new CreateGiftListFragment(), "add-giftlist-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void performLogout() {
        mAuth.signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoGiftListDetails(String giftListDocId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, GiftListFragment.newInstance(giftListDocId))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoFilterGiftlists() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new FilterFragment(), "filter-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelCreateGiftList() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void doneCreateGiftList() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void doneFilterGiftlists() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectTags() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new TagsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelGiftListDetail() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onTagsSelected(ArrayList<String> selectedTags) {
        //"add-giftlist-fragment"
        CreateGiftListFragment fragment = (CreateGiftListFragment) getSupportFragmentManager().findFragmentByTag("add-giftlist-fragment");
        if(fragment != null){
            fragment.updateSelectedTags(selectedTags);
            getSupportFragmentManager().popBackStack();
            return;
        }

        //"filter-fragment"
        FilterFragment filterFragment = (FilterFragment) getSupportFragmentManager().findFragmentByTag("filter-fragment");
        if(filterFragment != null){
            filterFragment.updateSelectedTags(selectedTags);
            getSupportFragmentManager().popBackStack();
            return;
        }
    }

    @Override
    public void onCancelTagSelection() {
        getSupportFragmentManager().popBackStack();
    }
}