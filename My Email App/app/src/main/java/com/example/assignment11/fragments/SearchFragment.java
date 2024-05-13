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
import com.example.assignment11.databinding.FragmentInboxItemBinding;
import com.example.assignment11.databinding.FragmentSearchBinding;
import com.example.assignment11.models.MessageItem;
import com.example.assignment11.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSearchBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<MessageItem> mMessages = new ArrayList<>();
    SearchAdapter adapter = new SearchAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView4.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView4.setAdapter(adapter);




        binding.editTextText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchQuery = s.toString().trim();
                if (!searchQuery.isEmpty()) {
                    performSearch(searchQuery);
                } else {
                    resetQuery();
                }
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelSearch();
            }
        });






    }

    private void performSearch(String searchQuery) {
         db.collection("UserInbox")
                .document(mAuth.getCurrentUser().getUid())
                .collection("Inbox")
                .whereArrayContains("TitleSubStrings",searchQuery)
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
                        Log.d("TAG", "onEvent: Search "+searchQuery);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void resetQuery() {
         mMessages.clear();
         adapter.notifyDataSetChanged();
    }

    SearchFragmentListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SearchFragmentListener) context;
    }

    public interface SearchFragmentListener {
        void cancelSearch();
    }


    class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            FragmentInboxItemBinding mBinding = FragmentInboxItemBinding.inflate(getLayoutInflater(), parent, false);
            return new SearchViewHolder(mBinding);

        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            MessageItem messageItem = mMessages.get(position);
            holder.setupUI(messageItem);
        }

        @Override
        public int getItemCount() {
            return mMessages.size();
        }

        class SearchViewHolder extends RecyclerView.ViewHolder {
            FragmentInboxItemBinding ItemBinding;
            MessageItem mMessageItem;

            public SearchViewHolder(FragmentInboxItemBinding mBinding) {
                super(mBinding.getRoot());
                ItemBinding = mBinding;
            }

            public void setupUI(MessageItem messageItem) {

                this.mMessageItem = messageItem;
                if (mMessageItem != null) {
                    ItemBinding.textViewSenderName.setText(mMessageItem.getSenderName());
                    ItemBinding.textViewMessageTitle.setText(mMessageItem.getMessageTitle());
                    ItemBinding.textViewSeenIndicator.setText(mMessageItem.getSeen());
                    if (messageItem.getReplyTo().isEmpty()) {
                        ItemBinding.textViewReplyToIndicator.setText("");
                    } else {
                        ItemBinding.textViewReplyToIndicator.setText("Reply To:" + messageItem.getReplyTo());
                    }


                    if (messageItem.getCreatedAt() != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                        ItemBinding.textViewCreatedAt.setText(sdf.format(mMessageItem.getCreatedAt().toDate()));
                    } else {
                        ItemBinding.textViewCreatedAt.setText("");
                    }
                }
            }
        }
    }
}