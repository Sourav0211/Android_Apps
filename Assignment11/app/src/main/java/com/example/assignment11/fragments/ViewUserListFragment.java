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
import com.example.assignment11.databinding.FragmentViewMessageBinding;
import com.example.assignment11.databinding.FragmentViewUserListBinding;
import com.example.assignment11.databinding.UserlistRowItemBinding;
import com.example.assignment11.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class ViewUserListFragment extends Fragment {


    public ViewUserListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentViewUserListBinding binding;

    ArrayList<User> users = new ArrayList<User>();

    UserAdapater adapater = new UserAdapater();
    ListenerRegistration listenerRegistration;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewUserListBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView2.setAdapter(adapater);

        db.collection("UsersList").document(mAuth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            currentUser = documentSnapshot.toObject(User.class);
                            setupUserListListener();
                        } else {
                            Toast.makeText(getActivity(), "User Doesn't Exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        binding.buttonViewBlockedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoBlockedUserList();
            }
        });


        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelSelectUser();
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
                        users.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            User user = doc.toObject(User.class);
                            if (currentUser != null) {
                                if (currentUser.getBlocked().containsKey(user.getUid())) {
                                    continue;
                                } else if(user.getBlocked().containsKey(currentUser.getUid())) {
                                    continue;
                                }
                                else {
                                    users.add(user);
                                }
                            }
                        }
                        adapater.notifyDataSetChanged();
                    }
                });
    }

    class UserAdapater extends RecyclerView.Adapter<UserAdapater.ViewUserHolder>{
        @NonNull
        @Override
        public ViewUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            UserlistRowItemBinding mBinding =  UserlistRowItemBinding.inflate(getLayoutInflater(),parent,false);
            return new ViewUserHolder(mBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewUserHolder holder, int position) {

            User user = users.get(position);
            holder.setupUI(user);
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        class ViewUserHolder extends  RecyclerView.ViewHolder{
            UserlistRowItemBinding itemBinding ;
            User mUser;

            public ViewUserHolder( UserlistRowItemBinding mBinding) {
                super(mBinding.getRoot());
                itemBinding = mBinding;
            }

            void setupUI(User user)
            {
                this.mUser = user;

                itemBinding.textViewUserName.setText(mUser.getName());
                itemBinding.textViewUserEmail.setText(mUser.getEmail());



                itemBinding.buttonBlockUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            if (mUser != null) {

                                currentUser.getBlocked().put(mUser.getUid(), true);

                                db.collection("UsersList").document(mAuth.getCurrentUser().getUid())
                                        .update("blocked",currentUser.getBlocked())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                users.remove(mUser);
                                                notifyDataSetChanged();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
                            } else {
                                Log.e("TAG", "User object is null");
                            }
                    }
                });



                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onUserSelected(mUser);
                    }
                });
            }
        }
    }
    FragmentUserListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentUserListListener) context;
    }

    public interface FragmentUserListListener {
        void onUserSelected(User user);
        void gotoBlockedUserList();
        void cancelSelectUser();
    }
}