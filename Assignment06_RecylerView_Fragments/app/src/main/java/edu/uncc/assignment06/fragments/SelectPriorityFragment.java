package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentSelectPriorityBinding;
import edu.uncc.assignment06.databinding.SelectionListItemBinding;
import edu.uncc.assignment06.models.Data;


public class SelectPriorityFragment extends Fragment {


    public SelectPriorityFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectPriorityBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSelectPriorityBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    SelectPriorityListener mListener;
    String priority="N/A";
    String[] priorityList;

    PriorityAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        priorityList = Data.priorities;
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PriorityAdapter();
        binding.recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelPriority();
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectPriorityListener) context;
    }

    public interface SelectPriorityListener {
        void sendPriority(String priority);
        void cancelPriority();
    }


    class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.PriorityViewHolder>{

        @NonNull
        @Override
        public PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            SelectionListItemBinding binding= SelectionListItemBinding.inflate(getLayoutInflater(),parent,false);
            return new PriorityViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PriorityViewHolder holder, int position) {
        priority = priorityList[position];
        holder.setPriorityUI(priority);
        }

        @Override
        public int getItemCount() {
            return priorityList.length;
        }

        class PriorityViewHolder extends RecyclerView.ViewHolder{
            SelectionListItemBinding mbinding;
            public PriorityViewHolder(SelectionListItemBinding binding) {
                super(binding.getRoot());
                mbinding = binding;
            }

                public void setPriorityUI(String priority){
                    mbinding.textView.setText(priority);

                    mbinding.textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.sendPriority(priority);
                        }
                    });

                }



        }
    }

}