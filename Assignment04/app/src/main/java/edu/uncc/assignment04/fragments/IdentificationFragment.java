package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment04.Profile;
import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentIdentificationBinding;


public class IdentificationFragment extends Fragment {



    public IdentificationFragment() {
        // Required empty public constructor
    }



    String name,email,role;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = binding.editTextName.getText().toString().trim();
                email = binding.editTextEmail.getText().toString().trim();
                role = "Student";
                int select = binding.radioGroup.getCheckedRadioButtonId();
                if(select == R.id.radioButtonEmployee) {
                    role = "Employee";
                } else if(select == R.id.radioButtonOther) {
                    role = "Other";
                }

                if (name.isEmpty() || email.isEmpty() || role.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Profile profile = new Profile(name, email, role);
                    mListener.sendProfile(profile);
                }
            }
        });
    }

    FragmentIdentificationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentIdentificationBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (IdentificationFragmentListener) context;
    }

    IdentificationFragmentListener mListener;
    public interface IdentificationFragmentListener{
        void sendProfile( Profile profile);


    }


}