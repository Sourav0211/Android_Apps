package com.example.assignment11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.assignment11.auth.LoginFragment;
import com.example.assignment11.auth.SignUpFragment;
import com.example.assignment11.fragments.BlockedUserListFragment;
import com.example.assignment11.fragments.CreateMessage;
import com.example.assignment11.fragments.InboxFragment;
import com.example.assignment11.fragments.SearchFragment;
import com.example.assignment11.fragments.ViewMessageFragment;
import com.example.assignment11.fragments.ViewUserListFragment;
import com.example.assignment11.models.MessageItem;
import com.example.assignment11.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener ,
        SignUpFragment.FragmentSignUpListener, InboxFragment.FragmentInboxListener
, CreateMessage.FragmentCreateMessageListener, ViewUserListFragment.FragmentUserListListener
, BlockedUserListFragment.FragmentBlockedUserListener , ViewMessageFragment.FragmentViewMessageListener
, SearchFragment.SearchFragmentListener {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(mAuth.getCurrentUser() == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView,new LoginFragment())
                    .commit();
        }
        else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new InboxFragment())
                    .commit();

        }



    }

    @Override
    public void gotoSignUpFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SignUpFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void LoginInSuccessfull() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new InboxFragment())
                .commit();
    }

    @Override
    public void gotoLoginFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void SignUpSuccessfull() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new InboxFragment(),"Inbox_Fragment")
                .commit();
    }

    @Override
    public void signOutSuccessful() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void createNewMessage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new CreateMessage(),"Create_Message")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMessageSelected(MessageItem messageItem) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ViewMessageFragment.newInstance(messageItem))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void gotoSearch() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SearchFragment())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void createMessage(MessageItem mMessage) {

    }

    @Override
    public void selectUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView,new ViewUserListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMessageCreated() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onUserSelected(User user) {
         CreateMessage fragment = (CreateMessage) getSupportFragmentManager().findFragmentByTag("Create_Message");
         if(fragment!= null)
         {
             fragment.selecterUser = user;
         }

         getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoBlockedUserList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new BlockedUserListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelSelectUser() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onReplyCreated() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelSearch() {
        getSupportFragmentManager().popBackStack();
    }
}