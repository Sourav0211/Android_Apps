package edu.uncc.assignment05.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment05.R;
import edu.uncc.assignment05.databinding.FragmentSelectSortBinding;


public class SelectSortFragment extends Fragment {


    public SelectSortFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    String sortType;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imageViewAgeAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortType="AgeAsc";

                mListener.sortUserList(sortType);
            }
        });

        binding.imageViewAgeDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortType="AgeDsc";

                mListener.sortUserList(sortType);
            }
        });

        binding.imageViewNameAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortType="NameAsc";

                mListener.sortUserList(sortType);
            }
        });

        binding.imageViewNameDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortType= "NameDsc";
                mListener.sortUserList(sortType);
            }
        });
    }


    SelectSortListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectSortListener) context;
    }

    public interface SelectSortListener{
        void sortUserList(String sortType);
    }







    FragmentSelectSortBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectSortBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }
}