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
import edu.uncc.assignment05.databinding.FragmentSelectGroupBinding;
import edu.uncc.assignment05.models.Data;


public class SelectGroupFragment extends Fragment {


    public SelectGroupFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    String[] group;
    ArrayAdapter<String> adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        group = Data.groups;
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,group);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  selectedGroup = group[position];

                mListener.sendSelectedGroup(selectedGroup);

            }
        });

    }

    SelectGroupListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectGroupListener) context;
    }

    public interface SelectGroupListener{
        void sendSelectedGroup(String selectedGroup);
    }

    FragmentSelectGroupBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSelectGroupBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }
}