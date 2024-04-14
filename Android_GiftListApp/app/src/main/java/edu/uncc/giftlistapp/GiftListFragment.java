package edu.uncc.giftlistapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import edu.uncc.giftlistapp.databinding.FragmentGiftListBinding;
import edu.uncc.giftlistapp.databinding.ListItemGiftlistProductBinding;
import models.GiftList;
import models.GiftListProduct;

public class GiftListFragment extends Fragment {

    private static final String ARG_PARAM_GIFTLIST_DOCID = "ARG_PARAM_GIFTLIST_DOCID";

    private String mGiftListDocId;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ListenerRegistration listenerRegistration;
    ListenerRegistration listenerRegistration2;


    public static GiftListFragment newInstance(String giftListDocId) {
        GiftListFragment fragment = new GiftListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_GIFTLIST_DOCID, giftListDocId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mGiftListDocId = getArguments().getString(ARG_PARAM_GIFTLIST_DOCID);
        }
    }

    public GiftListFragment() {
        // Required empty public constructor
    }

    FragmentGiftListBinding binding;
    ArrayList<GiftListProduct> mGiftListProducts = new ArrayList<>();

    GiftList mGiftList;
    GiftlistProductsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGiftListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Gift List");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GiftlistProductsAdapter();
        binding.recyclerView.setAdapter(adapter);


        listenerRegistration = db.collection("giftlists").document(mGiftListDocId).collection("giftlist-poducts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                mGiftListProducts.clear();
                if(error != null)
                {
                    error.printStackTrace();
                }else{
                    for(QueryDocumentSnapshot doc : value)
                    {
                        GiftListProduct giftList = doc.toObject(GiftListProduct.class);
                        mGiftListProducts.add(giftList);
                    }
                }
                adapter.notifyDataSetChanged();


            }
        });

        listenerRegistration2 = db.collection("giftlists").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                mGiftList = new GiftList();
                if(error != null)
                {
                    error.printStackTrace();
                }else{
                    for(QueryDocumentSnapshot doc : value)
                    {

                        GiftList giftList = doc.toObject(GiftList.class);
                        if(giftList.getDocId().equals(mGiftListDocId))
                        {
                            mGiftList =  giftList;
                        }

                    }
                }

                binding.textViewGiftListName.setText(mGiftList.getGiftListName());
                binding.textViewCreatedBy.setText(mGiftList.getUserName());
               binding.progressBar.setMax(100);

                double progress = (mGiftList.getAmountPledged() / mGiftList.getTotalAmount()) * 100;
                int progressInt = (int) Math.round(progress);
                binding.progressBar.setProgress(progressInt);

                binding.textViewProgress.setText(Double.toString(mGiftList.getAmountPledged()) + " Out of " + String.format("%.2f", mGiftList.getTotalAmount()));

                adapter.notifyDataSetChanged();


            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration2 != null)
        {
            listenerRegistration2.remove();
        }

        if(listenerRegistration != null)
        {
            listenerRegistration.remove();
        }
    }

    class GiftlistProductsAdapter extends RecyclerView.Adapter<GiftlistProductsAdapter.GiftListProductViewHolder>{
        @NonNull
        @Override
        public GiftListProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemGiftlistProductBinding itemBinding = ListItemGiftlistProductBinding.inflate(getLayoutInflater(), parent, false);
            return new GiftListProductViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GiftListProductViewHolder holder, int position) {
            holder.setupUI(mGiftListProducts.get(position));
        }

        @Override
        public int getItemCount() {
            return mGiftListProducts.size();
        }

        class GiftListProductViewHolder extends RecyclerView.ViewHolder{
            GiftListProduct mGiftListProduct;
            ListItemGiftlistProductBinding itemBinding;
            public GiftListProductViewHolder(ListItemGiftlistProductBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(GiftListProduct giftListProduct){
                this.mGiftListProduct = giftListProduct;

                itemBinding.textViewName.setText(mGiftListProduct.getName());
                itemBinding.textViewCostPerItem.setText(Double.toString(mGiftListProduct.getPrice()));
                Picasso.get().load(mGiftListProduct.getImg_url()).into(itemBinding.imageViewIcon);


                ArrayList<String> pledgedByList = mGiftListProduct.getPledgedBy();
                String[] pledgedByArray = pledgedByList.toArray(new String[0]);
                String pledgedByString = TextUtils.join(", ", pledgedByArray);
                itemBinding.textViewPledgedBy.setText(pledgedByString);

                if(mGiftListProduct.getPledgedBy().contains(mAuth.getCurrentUser().getDisplayName()))
                {
                    itemBinding.imageViewPlegeOrNot.setImageResource(R.drawable.ic_check_fill);
                }else{
                    itemBinding.imageViewPlegeOrNot.setImageResource(R.drawable.ic_check_not_fill);
                }

                if( !mGiftList.getUserName().equals(mAuth.getCurrentUser().getDisplayName())) {
                    itemBinding.imageViewPlegeOrNot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (mGiftListProduct.getPledgedBy().contains(mAuth.getCurrentUser().getDisplayName())) {
                                HashMap<String, Object> updateData = new HashMap<>();
                                updateData.put("pledgedBy", FieldValue.arrayRemove(mAuth.getCurrentUser().getDisplayName()));
                                db.collection("giftlists").document(mGiftListDocId).collection("giftlist-poducts").document(mGiftListProduct.getDocId()).update(updateData);


                                HashMap<String ,Object> updateData2= new HashMap<>();
                                updateData2.put("amountPledged" , FieldValue.increment(-mGiftListProduct.getPrice()));
                                db.collection("giftlists").document(mGiftListDocId).update(updateData2);

                                itemBinding.imageViewPlegeOrNot.setImageResource(R.drawable.ic_check_not_fill);
                            } else {
                                HashMap<String, Object> updateData = new HashMap<>();
                                updateData.put("pledgedBy", FieldValue.arrayUnion(mAuth.getCurrentUser().getDisplayName()));
                                db.collection("giftlists").document(mGiftListDocId).collection("giftlist-poducts").document(mGiftListProduct.getDocId()).update(updateData);

                                HashMap<String ,Object> updateData2= new HashMap<>();
                                updateData2.put("amountPledged" , FieldValue.increment(mGiftListProduct.getPrice()));
                                db.collection("giftlists").document(mGiftListDocId).update(updateData2);

                                itemBinding.imageViewPlegeOrNot.setImageResource(R.drawable.ic_check_fill);
                            }
                        }
                    });
                }

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
            mListener.cancelGiftListDetail();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    GiftListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GiftListListener){
            mListener = (GiftListListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GiftListListener");
        }
    }

    interface GiftListListener{
        void cancelGiftListDetail();
    }
}