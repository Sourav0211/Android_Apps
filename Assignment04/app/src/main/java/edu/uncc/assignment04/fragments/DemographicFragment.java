package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment04.Profile;
import edu.uncc.assignment04.databinding.FragmentDemographicBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DemographicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DemographicFragment extends Fragment {

    private static final String ARG_PARAM_DEMO = "ARG_PARAM_DEMO";


    // TODO: Rename and change types of parameters
    private Profile profile;
    String name, email,role;
    String education,income ,mStatus , lStatus;
    public DemographicFragment() {
        // Required empty public constructor
    }


    public static DemographicFragment newInstance(Profile profile) {
        DemographicFragment fragment = new DemographicFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_DEMO, profile);
        fragment.setArguments(args);
        return fragment;
    }

    public void displayEducation(String education) {
        this.education = education;
    }
    public void displayIncome(String income) {
        this.income = income;
    }
    public void displayMaritalStatus(String mStatus){
        this.mStatus = mStatus;
    }

    public void displayLivingStatus(String lStatus) {
        this.lStatus = lStatus;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Profile profile = (Profile) getArguments().getSerializable(ARG_PARAM_DEMO);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


            Profile profile = (Profile) getArguments().getSerializable(ARG_PARAM_DEMO);
            if (profile != null) {

                name = profile.getName();
                email = profile.getEmail();
                role = profile.getRole();

            }


        binding.buttonSelectEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectEducationFragment();
            }
        });


        binding.buttonSelectIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectIncomeFragment();
            }
        });

        binding.buttonSelectMarital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectMaritalStatusFragment();
            }
        });

        binding.buttonSelectLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectLivingStatusFragment();
            }
        });

        binding.textViewEducation.setText(education);

        binding.textViewIncomeStatus.setText(income);

        binding.textViewMaritalStatus.setText(mStatus);

        binding.textViewLivingStatus.setText(lStatus);


        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profile != null) {
                    profile.setEducation(education);
                    profile.setIncome(income);
                    profile.setMaritalStatus(mStatus);
                    profile.setLivingStatus(lStatus);
                    mListener.gotoProfileFragment(profile);
                }
            }
        });
    }

    FragmentDemographicBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDemographicBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentDemographicListener) context;
    }

    FragmentDemographicListener mListener;
    public interface FragmentDemographicListener{
        void gotoSelectEducationFragment();
        void gotoSelectIncomeFragment();

        void gotoSelectMaritalStatusFragment();

        void gotoSelectLivingStatusFragment();

        void gotoProfileFragment(Profile profile);
    }


}