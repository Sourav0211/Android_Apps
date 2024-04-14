package edu.uncc.giftlistapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.uncc.giftlistapp.databinding.FragmentGiftListsBinding;
import edu.uncc.giftlistapp.databinding.ListItemGiftlistBinding;
import models.GiftList;


public class GiftListsFragment extends Fragment {
    public GiftListsFragment() {
        // Required empty public constructor
    }

    FragmentGiftListsBinding binding;
    ArrayList<GiftList> mGiftLists = new ArrayList<>();
    GiftlistsAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ListenerRegistration listenerRegistration;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGiftListsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Gift Lists");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GiftlistsAdapter();
        binding.recyclerView.setAdapter(adapter);

        listenerRegistration = db.collection("giftlists").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mGiftLists.clear();
                if(error != null)
                {
                    error.printStackTrace();
                }else{

                    for(QueryDocumentSnapshot doc : value)
                    {
                        GiftList giftList = doc.toObject(GiftList.class);
                        mGiftLists.add(giftList);
                    }
                }
                Log.d("GIft", "onEvent: gifts list"+mGiftLists);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration !=null)
        {
            listenerRegistration.remove();
            mGiftLists.clear();
        }
    }

    class GiftlistsAdapter extends RecyclerView.Adapter<GiftlistsAdapter.GiftListViewHolder>{
        @NonNull
        @Override
        public GiftListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemGiftlistBinding itemBinding = ListItemGiftlistBinding.inflate(getLayoutInflater(), parent, false);
            return new GiftListViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GiftListViewHolder holder, int position) {
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
                itemBinding.progressBar.setMax(100);

                double amountPledged = mGiftList.getAmountPledged();
                double totalAmount = mGiftList.getTotalAmount();

                if (totalAmount > 0) {
                    int progress = (int) Math.round((amountPledged / totalAmount) * 100);
                    itemBinding.progressBar.setProgress(progress);

                    itemBinding.textViewProgress.setText(amountPledged + " Out of " + totalAmount);
                } else {
                    itemBinding.progressBar.setProgress(0);
                    itemBinding.textViewProgress.setText("Total amount not available");
                }
                //set the progress to a value from 0 to 100 based on the total pledged products
                //itemBinding.progressBar.setProgress();

                if(mGiftLists !=null) {
                    itemBinding.textViewName.setText(mGiftList.getGiftListName());
                    itemBinding.textViewCreatedBy.setText(mGiftList.getUserName());
                    itemBinding.textViewNumItems.setText(Integer.toString(mGiftList.getNumberOfItem()));
                    String[] selectedTagsArray = mGiftList.getSelectedTags().toArray(new String[0]);
                    String selectedTagsString = TextUtils.join(", ", selectedTagsArray);
                    itemBinding.textViewSelectedTags.setText(selectedTagsString);
                }


                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoGiftListDetails(mGiftList.getDocId());
                    }
                });
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout){
            mListener.performLogout();
            return true;
        } else if (item.getItemId() == R.id.action_add){
            mListener.gotoAddNewGiftList();
            return true;
        } else if (item.getItemId() == R.id.action_filter){
            mListener.gotoFilterGiftlists();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    GiftListsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GiftListsListener){
            mListener = (GiftListsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GiftListsListener");
        }
    }

    interface GiftListsListener{
        void gotoAddNewGiftList();
        void performLogout();
        void gotoGiftListDetails(String giftListDocId);
        void gotoFilterGiftlists();
    }
}