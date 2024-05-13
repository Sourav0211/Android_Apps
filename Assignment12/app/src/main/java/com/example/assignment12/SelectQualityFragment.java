package com.example.assignment12;

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

//import com.example.assignment12.databinding.FragmentLoginBinding;
import com.example.assignment12.databinding.FragmentSelectQualityBinding;

public class SelectQualityFragment extends Fragment {

    public SelectQualityFragment() {
        // Required empty public constructor
    }

    FragmentSelectQualityBinding binding;
    public static final String[] qualitylist = {
            "Excellent", "Very good", "Good", "Fair", "Poor"
    };
    ArrayAdapter<String> adapter;
    String quality;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectQualityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, qualitylist);
        binding.listView.setAdapter(adapter);
        getActivity().setTitle("Select Quality of Sleep");

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                quality = adapter.getItem(position);
                mListener.sendQualitySelection(quality);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelSelection();
            }
        });
    }
    QualityListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (QualityListener) context;
    }

    public interface QualityListener{
        void cancelSelection();
        void sendQualitySelection(String quality);
    }
}