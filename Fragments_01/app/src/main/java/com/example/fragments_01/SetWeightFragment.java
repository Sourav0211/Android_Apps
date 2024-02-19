package com.example.fragments_01;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragments_01.databinding.FragmentSetWeightBinding;


public class SetWeightFragment extends Fragment {


    public SetWeightFragment() {
        // Required empty public constructor
    }

    FragmentSetWeightBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSetWeightBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSetWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int weight = Integer.parseInt(binding.editTextSetWeight.getText().toString());
                    mListener.setWeight(weight);
                }catch (NumberFormatException ex)
                {
                    Toast.makeText(getActivity(),"Enter valid weight", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SetWeightFragmentListener) context;
    }

    SetWeightFragmentListener mListener;

    interface SetWeightFragmentListener{
        void setWeight(int weight);
    }
}