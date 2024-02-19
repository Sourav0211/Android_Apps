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
import edu.uncc.assignment04.databinding.FragmentSelectEducationBinding;


public class SelectEducationFragment extends Fragment {



    public SelectEducationFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentSelectEducationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectEducationBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
    String education = "";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int select = binding.radioGroup.getCheckedRadioButtonId();

                if(select == R.id.radioButtonBHS)
                {
                    education = "Below High School";
                } else if(select == R.id.radioButtonHS)
                {
                    education = "High School";
                }else if(select == R.id.radioButtonBS)
                {
                    education = "Bachelors";
                }else if(select == R.id.radioButtonMS)
                {
                    education = "Masters";
                }else if(select == R.id.radioButtonPHD)
                {
                    education = "Phd";
                }else if(select == R.id.radioButtonTS)
                {
                    education = "Trade school";
                }else if(select == R.id.radioButtonPreferNotToSay)
                {
                    education = "Prefer not to say";
                }

                mListener.setEducation(education);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener= (FragmentSetEducationListener) context;
    }

    FragmentSetEducationListener mListener;
    public interface FragmentSetEducationListener{
        void setEducation(String education);
    }
}