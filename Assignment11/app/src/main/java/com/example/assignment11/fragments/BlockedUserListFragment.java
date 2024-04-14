package com.example.assignment11.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assignment11.R;
import com.example.assignment11.databinding.BlockedUserlistRowItemBinding;
import com.example.assignment11.databinding.FragmentBlockedUserListBinding;
import com.example.assignment11.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;


public class BlockedUserListFragment extends Fragment {

    public BlockedUserListFragment() {
        // Required empty public constructor
    }

    ArrayList<User> blockedUsers = new ArrayList<>();

    BlockedUserAdapter adapter = new BlockedUserAdapter();
    ListenerRegistration listenerRegistration;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    User currentUser;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView3.setAdapter(adapter);
        binding.textView3.setText("Blocked Users");



        db.collection("UsersList").document(mAuth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            currentUser = documentSnapshot.toObject(User.class);

                            Log.d("TAG", "onSuccess: Current User found" + currentUser);
                            setupUserListListener();
                        } else {
                            Toast.makeText(getActivity(), "User Doesn't Exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancel();
            }
        });

    }

    private void setupUserListListener() {
        listenerRegistration = db.collection("UsersList")
                .whereNotEqualTo("Uid", mAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            error.printStackTrace();
                            return;
                        }
                        blockedUsers.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            User user = doc.toObject(User.class);
                            if (currentUser != null) {
                                if (currentUser.getBlocked().containsKey(user.getUid())) {
                                    blockedUsers.add(user);

                                } else {
                                    continue;
                                }
                                Log.d("TAG", "onEvent: "+ blockedUsers);
                            }
                        }
                       adapter.notifyDataSetChanged();
                    }
                });
    }

    FragmentBlockedUserListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBlockedUserListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


    class BlockedUserAdapter extends RecyclerView.Adapter<BlockedUserAdapter.BlockedUserViewHolder>{
        @NonNull
        @Override
        public BlockedUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            BlockedUserlistRowItemBinding mBinding = BlockedUserlistRowItemBinding.inflate(getLayoutInflater(),parent,false);
            return new BlockedUserViewHolder(mBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull BlockedUserViewHolder holder, int position) {
        User user = blockedUsers.get(position);
        holder.setupUI(user);
        }

        @Override
        public int getItemCount() {
            return blockedUsers.size();
        }

        class BlockedUserViewHolder extends RecyclerView.ViewHolder{
            BlockedUserlistRowItemBinding itemBinding;
            User mUser;
            public BlockedUserViewHolder(BlockedUserlistRowItemBinding mBinding) {
                super(mBinding.getRoot());
                itemBinding = mBinding;

            }

            private void setupUI(User user)
            {

                this.mUser = user;

                itemBinding.textViewUserName.setText(mUser.getName());
                itemBinding.textViewUserEmail.setText(mUser.getEmail());

                itemBinding.buttonUnBlockUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        currentUser.getBlocked().remove(mUser.getUid());
                        db.collection("UsersList").document(mAuth.getCurrentUser().getUid())
                                .update("blocked", currentUser.getBlocked())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        blockedUsers.remove(mUser);
                                        notifyDataSetChanged();

                                        if(blockedUsers.isEmpty())
                                        {
                                            binding.textView3.setText("Blocked Users List is Empty");
                                        }

                                    }
                                });
                    }
                });

            }


        }
    }

    FragmentBlockedUserListener mListener;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentBlockedUserListener) context;

    }

    public interface FragmentBlockedUserListener{
        void onCancel();
    }
}