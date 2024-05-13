package com.example.assignment11.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assignment11.R;
import com.example.assignment11.databinding.FragmentViewMessageBinding;
import com.example.assignment11.models.MessageItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewMessageFragment extends Fragment {


    private static final String ARG_PARAM_MESSAGE = "VIEW_MESSAGE";
    private MessageItem mMessage;

    public ViewMessageFragment() {
        // Required empty public constructor
    }


    public static ViewMessageFragment newInstance(MessageItem mMessage) {
        ViewMessageFragment fragment = new ViewMessageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_MESSAGE, mMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMessage = (MessageItem) getArguments().getSerializable(ARG_PARAM_MESSAGE);
        }
    }

    FragmentViewMessageBinding binding ;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewMessageText.setText(mMessage.getMessageText());
        binding.textViewTitle.setText(mMessage.getMessageTitle());
        binding.textViewSender.setText("From: " + mMessage.getSenderName());
        binding.textViewReciever.setText("To: "+mMessage.getReceiverName());
        if(mMessage.getCreatedAt()!= null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            binding.textViewDate.setText("Date: "+ sdf.format(mMessage.getCreatedAt().toDate()));
        }else {
            binding.textViewDate.setText("");
        }



        binding.buttonSendReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String replytext = binding.editTextReply.getText().toString();

                if(replytext.equals(""))
                {
                    Toast.makeText(getActivity(),"Enter Text ",Toast.LENGTH_SHORT).show();
                }else{

                    DocumentReference docRefReceiver = db.collection("UserInbox")
                            .document(mMessage.getSenderId())
                            .collection("Inbox")
                            .document();

                    DocumentReference docRefSender = db.collection("UserInbox")
                            .document(mAuth.getCurrentUser().getUid())
                            .collection("Inbox")
                            .document();

                    ArrayList<String> subTitle = generateSubStrings(mMessage.getMessageTitle());

                    HashMap<String , Object> receiverData = new HashMap<>();
                    receiverData.put("messageText",replytext);
                    receiverData.put("messageTitle",mMessage.getMessageTitle());
                    receiverData.put("senderName", mAuth.getCurrentUser().getDisplayName());
                    receiverData.put("receiverName", mMessage.getSenderName());
                    receiverData.put("seen", "Not Opened");
                    receiverData.put("replyTo",mMessage.getMessageTitle());
                    receiverData.put("senderId", mAuth.getCurrentUser().getUid());
                    receiverData.put("ReceiverId", mMessage.getSenderId());
                    receiverData.put("DocId",docRefReceiver.getId());
                    receiverData.put("TitleSubStrings",subTitle);
                    receiverData.put("createdAt", FieldValue.serverTimestamp());


                    HashMap<String , Object> senderData = new HashMap<>();
                    senderData.put("messageText",replytext);
                    senderData.put("messageTitle",mMessage.getMessageTitle());
                    senderData.put("senderName", mAuth.getCurrentUser().getDisplayName());
                    senderData.put("receiverName", mMessage.getSenderName());
                    senderData.put("seen","");
                    senderData.put("replyTo",mMessage.getMessageTitle());
                    senderData.put("senderId", mAuth.getCurrentUser().getUid());
                    senderData.put("ReceiverId", mMessage.getSenderId());
                    senderData.put("DocId",docRefSender.getId());
                    senderData.put("TitleSubStrings",subTitle);
                    senderData.put("createdAt", FieldValue.serverTimestamp());

                    docRefReceiver.set(receiverData);
                    docRefSender.set(senderData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mListener.onReplyCreated();
                        }
                    });



                }
            }
        });

        binding.buttonDeleteMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("UserInbox").document(mAuth.getCurrentUser().getUid()).collection("Inbox")
                        .document(mMessage.getDocId()).delete();
                mListener.onReplyCreated();
            }
        });

        binding.buttonCancelViewReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onReplyCreated();
            }
        });

    }


    private ArrayList<String> generateSubStrings(String title)
    {

        ArrayList<String> subTitles =new ArrayList<>();

        for(int i=0;i<title.length();i++)
        {
            int j =0;
            String temp = title.substring(j,i+1);
            subTitles.add(temp);

        }
        return subTitles;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentViewMessageBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    FragmentViewMessageListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentViewMessageListener) context;
    }

    public interface FragmentViewMessageListener{
        void onReplyCreated();
    }
}