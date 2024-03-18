package edu.uncc.assignment08;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.uncc.assignment08.databinding.FragmentLoginBinding;
import edu.uncc.assignment08.models.AuthResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginFragment extends Fragment {
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid email!", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()){
                    Toast.makeText(getActivity(), "Enter valid password!", Toast.LENGTH_SHORT).show();
                } else {

                    String loginUrl = "https://www.theappsdr.com/posts/login";

                    // Build the request body
                    FormBody formBody = new FormBody.Builder()
                            .add("email", email)
                            .add("password", password)
                            .build();

                    // Build the request
                    Request request = new Request.Builder()
                            .url(loginUrl)
                            .post(formBody)
                            .build();

                    OkHttpClient client = new OkHttpClient();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                            if (response.isSuccessful()) {
                                Log.d("TAG", "login response sucess");
                                // Response successful, handle data
                                String responseData = response.body().string();

                                try {
                                    JSONObject authResponseObject = new JSONObject(responseData);
                                    String token = authResponseObject.getString("token");
                                    String userId = authResponseObject.getString("user_id");
                                    String userFullName = authResponseObject.getString("user_fullname");

                                    // Save token and user info to SharedPreferences
//                                    saveAuthDataToSharedPreferences(token, userId, userFullName);

//                                    AuthResponse authResponse = new AuthResponse(userId,userFullName,token);


                                    // Communicate data back to activity
                                    mListener.authCompleted(new AuthResponse(userId, userFullName, token));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // Replace current fragment with PostsFragment

                            } else {
                                // Response not successful, handle error
                                String errorMessage = response.message();
                                Log.d("ErrorMessage", "onResponse: "+errorMessage);

                                // Show alert dialog with error message
                                showAlert(errorMessage);
                            }

                        }
                    });


                }
            }
        });

        binding.buttonCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createNewAccount();
            }
        });

        getActivity().setTitle(R.string.login_label);
    }

    private void showAlert(String errorMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Login Error")
                .setMessage(errorMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }

    LoginListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (LoginListener) context;
    }

    interface LoginListener {
        void createNewAccount();
        void authCompleted(AuthResponse authResponse);
    }
}