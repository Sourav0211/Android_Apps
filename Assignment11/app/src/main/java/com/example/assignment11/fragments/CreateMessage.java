package com.example.assignment11.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.assignment11.R;
import com.example.assignment11.databinding.FragmentCreateMessageBinding;
import com.example.assignment11.models.MessageItem;
import com.example.assignment11.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


public class CreateMessage extends Fragment {



    public CreateMessage() {
        // Required empty public constructor
    }
    public MessageItem mMessage;

    public User selecterUser;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db =  FirebaseFirestore.getInstance();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(selecterUser != null) {
            binding.textViewSelectedUser.setText(selecterUser.getName());
        }else{
            binding.textViewSelectedUser.setText("");
        }



        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.editTextTitle.getText().toString();
                String message = binding.editTextMessage.getText().toString();
                if(title.equals(""))
                {
                    Toast.makeText(getActivity(),"Enter Title",Toast.LENGTH_SHORT).show();
                }
                else if(message.equals("")){
                    Toast.makeText(getActivity(),"Enter Text Message",Toast.LENGTH_SHORT).show();
                }
                else{
                    ArrayList<String> subTitle = generateSubStrings(title);

                    DocumentReference docRefReceiver = db.collection("UserInbox")
                            .document(selecterUser.getUid())
                            .collection("Inbox")
                            .document();

                    DocumentReference docRefSender = db.collection("UserInbox")
                            .document(mAuth.getCurrentUser().getUid())
                            .collection("Inbox")
                            .document();


                    HashMap<String , Object> receiverData = new HashMap<>();
                    receiverData.put("messageText",message);
                    receiverData.put("messageTitle",title);
                    receiverData.put("senderName", mAuth.getCurrentUser().getDisplayName());
                    receiverData.put("receiverName", selecterUser.getName());
                    receiverData.put("seen", "Not Opened");
                    receiverData.put("replyTo","");
                    receiverData.put("senderId", mAuth.getCurrentUser().getUid());
                    receiverData.put("ReceiverId", selecterUser.getUid());
                    receiverData.put("DocId",docRefReceiver.getId());
                    receiverData.put("TitleSubStrings",subTitle);
                    receiverData.put("createdAt", FieldValue.serverTimestamp());


                    HashMap<String , Object> senderData = new HashMap<>();
                    senderData.put("messageText",message);
                    senderData.put("messageTitle",title);
                    senderData.put("senderName", mAuth.getCurrentUser().getDisplayName());
                    senderData.put("receiverName", selecterUser.getName());
                    senderData.put("seen","");
                    senderData.put("replyTo","");
                    senderData.put("senderId", mAuth.getCurrentUser().getUid());
                    senderData.put("ReceiverId", selecterUser.getUid());
                    senderData.put("DocId",docRefSender.getId());
                    senderData.put("TitleSubStrings",subTitle);
                    senderData.put("createdAt", FieldValue.serverTimestamp());

                    docRefReceiver.set(receiverData);
                    docRefSender.set(senderData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mListener.onMessageCreated();
                        }
                    });




                }



            }
        });

        binding.buttonSelectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selectUser();
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMessageCreated();
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


    FragmentCreateMessageBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateMessageBinding.inflate(inflater,container,false);
        return binding.getRoot();
        // Inflate the layout for this fragment

    }

    FragmentCreateMessageListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentCreateMessageListener) context;
    }

    public interface FragmentCreateMessageListener{
        void createMessage(MessageItem mMessage);

        void selectUser();
        void onMessageCreated();
    }
}