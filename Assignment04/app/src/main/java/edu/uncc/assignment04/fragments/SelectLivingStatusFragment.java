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
import edu.uncc.assignment04.databinding.FragmentSelectLivingStatusBinding;


public class SelectLivingStatusFragment extends Fragment {


    public SelectLivingStatusFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    FragmentSelectLivingStatusBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectLivingStatusBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    String lStatus="";
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int select = binding.radioGroup.getCheckedRadioButtonId();

                if(select == R.id.radioButtonHomeOwner)
                {
                    lStatus = "Home Owner";
                } else if (select == R.id.radioButtonRenter) {
                    lStatus = "Renter";
                }else if (select == R.id.radioButtonLessee) {
                    lStatus = "Lessee";
                }else if (select == R.id.radioButtonOther) {
                    lStatus = "Other";
                }else if (select == R.id.radioButtonPreferNotToSay) {
                    lStatus = "Prefer Not To Say";
                }

                mListener.setLivingStatus(lStatus);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentSetLivingStatusListener) context;
    }

    FragmentSetLivingStatusListener mListener;
    public interface FragmentSetLivingStatusListener {
        void setLivingStatus(String lStatus);
    }

}