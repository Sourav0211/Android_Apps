package edu.uncc.assignment08;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.uncc.assignment08.databinding.FragmentCreatePostBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreatePostFragment extends Fragment {
    public CreatePostFragment() {
        // Required empty public constructor
    }


    FragmentCreatePostBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBackToPosts();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postText = binding.editTextPostText.getText().toString();
                if (postText.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter valid post !!", Toast.LENGTH_SHORT).show();
                } else {
                    // Assuming you have the authentication token stored in a variable called authToken
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("auth_data",Context.MODE_PRIVATE);

                    String authToken = sharedPreferences.getString("token","null");
                    Log.d("AuthToken", authToken);

                    String createUrl = "https://www.theappsdr.com/posts/create";

                    FormBody formBody = new FormBody.Builder()
                            .add("post_text", postText)
                            .build();


                    // Create an OkHttpClient instance
                    OkHttpClient client = new OkHttpClient();

                    // Create a request object with the authorization header and request body
                    Request request = new Request.Builder()
                            .url(createUrl)
                            .addHeader("Authorization", "BEARER " + authToken)
                            .post(formBody)
                            .build();

                    // Execute the request asynchronously
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                            // Handle failure, show error message if necessary
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String body = response.body().string();
                            if (response.isSuccessful()) {
                                // API call successful
                                // Pop the back stack to display the Post List Fragment
                                Log.d("TAG", "onResponse: Create post success");
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mListener.goBackToPosts();
                                    }
                                });
                            } else {
                                try {
                                    JSONObject rootJson = new JSONObject(body);

                                    String message = rootJson.getString("message");

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                }
            }
        });


        getActivity().setTitle(R.string.create_post_label);
    }

    CreatePostListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreatePostListener) context;
    }

    interface CreatePostListener {
        void goBackToPosts();
    }
}