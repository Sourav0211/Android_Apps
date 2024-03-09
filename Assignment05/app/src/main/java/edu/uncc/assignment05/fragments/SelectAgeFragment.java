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

import java.util.ArrayList;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentSelectAgeBinding;
import edu.uncc.assignment05.models.Data;


public class SelectAgeFragment extends Fragment {


    public SelectAgeFragment() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentSelectAgeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectAgeBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    ArrayList<String> age = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for(int i =18;i<=100;i++)
        {
            age.add(Integer.toString(i));
        }
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,age);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAge = age.get(position);

                mListener.sendSelectedAge(selectedAge);
            }
        });

    }
    selectedAgeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener=(selectedAgeListener) context;
    }

    public interface selectedAgeListener{
        void sendSelectedAge(String selectedAge);
    }

}