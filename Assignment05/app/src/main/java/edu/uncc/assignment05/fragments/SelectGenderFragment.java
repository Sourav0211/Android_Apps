package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentSelectGenderBinding;
import edu.uncc.assignment05.models.Data;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectGenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectGenderFragment extends Fragment {


    public SelectGenderFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectGenderBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentSelectGenderBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    String[] genders;
    ArrayAdapter<String> adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        genders =  Data.genders;
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,genders);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String gender = genders[position];

                mListener.sendGender(gender);

            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentSelectGenderListener)context;
    }

    FragmentSelectGenderListener mListener;
    public interface FragmentSelectGenderListener {
        void sendGender(String gender);
    }
}