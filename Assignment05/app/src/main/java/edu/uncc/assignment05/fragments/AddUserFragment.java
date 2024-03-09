package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentAddUserBinding;
import edu.uncc.assignment05.models.User;


public class AddUserFragment extends Fragment {



    public AddUserFragment() {
        // Required empty public constructor
    }

    String gender="N/A",age="N/A",group="N/A",state="N/A";
    public void setGender(String gender)
    {
        this.gender = gender;
    }
    public void setAge(String age) {this.age = age;}
    public void setGroup(String group) {this.group = group;}
    public void setState(String state) {this.state = state;}





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentAddUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
        // Inflate the layout for this fragment

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.textViewGender.setText(gender);
        binding.textViewAge.setText(age);
        binding.textViewGroup.setText(group);
        binding.textViewState.setText(state);


        binding.buttonSelectAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectAgeFragment();
            }
        });

        binding.buttonSelectGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectGenderFragment();
            }
        });

        binding.buttonSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectGroupFragment();
            }
        });

        binding.buttonSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectStateFragment();
            }
        });


        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                int mAge = Integer.valueOf(age);

                User user = new User(name,email,gender,mAge,state,group);
                mListener.sendUser(user);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener =(FragmentAddUserListener) context;
    }

    FragmentAddUserListener mListener;

    public interface FragmentAddUserListener{
        void gotoSelectAgeFragment();
        void gotoSelectGenderFragment();
        void gotoSelectGroupFragment();
        void gotoSelectStateFragment();

        void sendUser(User user);


    }
}