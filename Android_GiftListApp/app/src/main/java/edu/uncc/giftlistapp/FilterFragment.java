package edu.uncc.giftlistapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.giftlistapp.databinding.FragmentFilterBinding;
import edu.uncc.giftlistapp.databinding.ListItemGiftlistBinding;
import models.GiftList;

public class FilterFragment extends Fragment {
    ArrayList<String> selectedTags = new ArrayList<>();

    public void updateSelectedTags(ArrayList<String> tags){
        selectedTags.clear();
        selectedTags.addAll(tags);
    }

    ArrayList<GiftList> mGiftLists = new ArrayList<>();
    GiftlistsAdapter adapter;


    public FilterFragment() {// Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    FragmentFilterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void getFilteredGiftLists(){
        if (selectedTags.size() == 0){

        } else {

        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Filter Gift Lists");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GiftlistsAdapter();
        binding.recyclerView.setAdapter(adapter);

        getFilteredGiftLists();

        if(selectedTags.size() == 0){
            binding.textViewSelectedTags.setText("None (Show All)");
        } else {
            binding.textViewSelectedTags.setText(String.join(", ", selectedTags));
        }

        binding.buttonClearTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTags.clear();
                binding.textViewSelectedTags.setText("None (Show All)");
                getFilteredGiftLists();
            }
        });

        binding.buttonSelectTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectTags();
            }
        });
    }

    class GiftlistsAdapter extends RecyclerView.Adapter<GiftlistsAdapter.GiftListViewHolder>{
        @NonNull
        @Override
        public GiftlistsAdapter.GiftListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemGiftlistBinding itemBinding = ListItemGiftlistBinding.inflate(getLayoutInflater(), parent, false);
            return new GiftlistsAdapter.GiftListViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GiftlistsAdapter.GiftListViewHolder holder, int position) {
            holder.setupUI(mGiftLists.get(position));
        }

        @Override
        public int getItemCount() {
            return mGiftLists.size();
        }

        class GiftListViewHolder extends RecyclerView.ViewHolder{
            ListItemGiftlistBinding itemBinding;
            GiftList mGiftList;
            public GiftListViewHolder(ListItemGiftlistBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(GiftList giftList){
                this.mGiftList = giftList;

            }
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_cancel){
            mListener.doneFilterGiftlists();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    FilterListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FilterListener){
            mListener = (FilterListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FilterListener");
        }
    }

    interface FilterListener{
        void doneFilterGiftlists();
        void gotoSelectTags();
    }
}