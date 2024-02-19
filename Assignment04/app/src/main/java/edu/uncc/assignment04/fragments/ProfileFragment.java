package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment04.Profile;
import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentDemographicBinding;
import edu.uncc.assignment04.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {


    private static final String ARG_PARAM_FINAL = "ARG_PARAM_FINAL";





    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(Profile profile) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_FINAL, profile);
        fragment.setArguments(args);
        return fragment;
    }


    String name,email,role,education,maritalStatus,livingStatus,income;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null) {
            Profile profile = (Profile) getArguments().getSerializable(ARG_PARAM_FINAL);
            if(profile !=null)
            {
                name = profile.getName();
                email = profile.getEmail();
                role = profile.getRole();
                education = profile.getEducation();
                maritalStatus = profile.getMaritalStatus();
                livingStatus = profile.getLivingStatus();
                income = profile.getIncome();
            }
        }

        binding.textViewName.setText(name);
        binding.textViewEmail.setText(email);
        binding.textViewEdu.setText(education);
        binding.textViewMaritalStatus.setText(maritalStatus);
        binding.textViewLivingStatus.setText(livingStatus);
        binding.textViewIncomeValue.setText(income);



    }

    FragmentProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


}