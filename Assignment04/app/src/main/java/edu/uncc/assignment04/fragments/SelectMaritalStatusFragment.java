package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentSelectMaritalStatusBinding;


public class SelectMaritalStatusFragment extends Fragment {



    public SelectMaritalStatusFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectMaritalStatusBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectMaritalStatusBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    String mStatus="";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int select = binding.radioGroup.getCheckedRadioButtonId();

                if(select == R.id.radioButtonMarried)
                {
                    mStatus= "Married";
                }else if(select == R.id.radioButtonNotMarried)
                {
                    mStatus = "Not Married";
                }else if(select == R.id.radioButtonPreferNotToSay)
                {
                    mStatus = "Prefer Not to Say";
                }
                mListener.setMaritalStatus(mStatus);
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentSetMaritalStatusListener) context;
    }

    FragmentSetMaritalStatusListener mListener;

    public interface FragmentSetMaritalStatusListener{
        void setMaritalStatus(String mStatus);
    }
}