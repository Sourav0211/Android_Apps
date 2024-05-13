package com.example.assignment11.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assignment11.R;
import com.example.assignment11.databinding.FragmentInboxBinding;
import com.example.assignment11.databinding.FragmentInboxItemBinding;
import com.example.assignment11.models.MessageItem;
import com.example.assignment11.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class InboxFragment extends Fragment {



    public InboxFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ArrayList<MessageItem> mMessages = new ArrayList<>();
    InboxMessageAdapter adapter;
    FragmentInboxBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListenerRegistration listenerRegistration;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapter =  new InboxMessageAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        binding.textView.setText(mAuth.getCurrentUser().getDisplayName());




        listenerRegistration = db.collection("UserInbox")
                        .document(mAuth.getCurrentUser().getUid())
                        .collection("Inbox")
                        .orderBy("createdAt", Query.Direction.DESCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        mMessages.clear();
                        if(error != null)
                        {
                            error.printStackTrace();
                        }
                        else{
                            for (QueryDocumentSnapshot doc: value) {

                                MessageItem messageItem = doc.toObject(MessageItem.class);
                                mMessages.add(messageItem);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });







        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                mListener.signOutSuccessful();
            }
        });

        binding.buttonCreateNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createNewMessage();
            }
        });

        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSearch();
            }
        });

    }










    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInboxBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    FragmentInboxListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentInboxListener) context;
    }

    public interface FragmentInboxListener
    {
      void  signOutSuccessful();
      void createNewMessage();

      void onMessageSelected(MessageItem messageItem);
      void gotoSearch();
    }


    class InboxMessageAdapter extends RecyclerView.Adapter<InboxMessageAdapter.InboxMessageViewHolder>{
        @NonNull
        @Override
        public InboxMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            FragmentInboxItemBinding mBinding = FragmentInboxItemBinding.inflate(getLayoutInflater(),parent,false);
            return new InboxMessageViewHolder(mBinding);


        }

        @Override
        public void onBindViewHolder(@NonNull InboxMessageViewHolder holder, int position) {

            MessageItem messageItem = mMessages.get(position);
            holder.setupUI(messageItem);
        }

        @Override
        public int getItemCount() {
            return mMessages.size();
        }

        class InboxMessageViewHolder extends RecyclerView.ViewHolder{
            FragmentInboxItemBinding ItemBinding;
            MessageItem mMessageItem;
            public InboxMessageViewHolder(FragmentInboxItemBinding mBinding) {
                super(mBinding.getRoot());
                ItemBinding = mBinding;
            }

            public void setupUI(MessageItem messageItem)
            {

                this.mMessageItem = messageItem;
                if(mMessageItem != null) {
                    ItemBinding.textViewSenderName.setText(mMessageItem.getSenderName());
                    ItemBinding.textViewMessageTitle.setText(mMessageItem.getMessageTitle());
                    ItemBinding.textViewSeenIndicator.setText(mMessageItem.getSeen());
                    if(messageItem.getReplyTo().isEmpty())
                    {
                        ItemBinding.textViewReplyToIndicator.setText("");
                    }
                    else{
                        ItemBinding.textViewReplyToIndicator.setText("Reply To:"+ messageItem.getReplyTo());
                    }


                    if(messageItem.getCreatedAt()!= null)
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                        ItemBinding.textViewCreatedAt.setText(sdf.format(mMessageItem.getCreatedAt().toDate()));
                    }else {
                        ItemBinding.textViewCreatedAt.setText("");
                    }
                }

                ItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mMessageItem.getSenderId().equals(mAuth.getCurrentUser().getUid())) {
                            HashMap<String, Object> updateData = new HashMap<>();

                            updateData.put("seen", "Opened");

                            db.collection("UserInbox")
                                    .document(mMessageItem.getSenderId())
                                    .collection("Inbox").document(mMessageItem.getSendDocId())
                                    .update(updateData);

                        }
                        mListener.onMessageSelected(mMessageItem);



                    }
                });



                if(mMessageItem.getSenderId().equals(mAuth.getCurrentUser().getUid()))
                {
                    ItemBinding.imageView.setImageResource(R.drawable.img);
                }
                else{
                    ItemBinding.imageView.setImageResource(R.drawable.email);
                }
            }

        }
    }


}