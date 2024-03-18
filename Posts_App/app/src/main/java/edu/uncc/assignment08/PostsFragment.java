package edu.uncc.assignment08;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.assignment08.databinding.FragmentPostsBinding;
import edu.uncc.assignment08.databinding.PostRowItemBinding;
import edu.uncc.assignment08.models.AuthResponse;
import edu.uncc.assignment08.models.Post;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostsFragment extends Fragment {
    public PostsFragment() {
        // Required empty public constructor
    }

    AuthResponse authResponse;
    FragmentPostsBinding binding;
    PostsAdapter postsAdapter;
    ArrayList<Post> mPosts = new ArrayList<>();
    private int currentPage = 1; // Variable to track the current page
    private int totalPageCount = Integer.MAX_VALUE;

    SharedPreferences sharedPreferences;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("auth_data", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("user_fullname","Sir/Madam");
        binding.textViewTitle.setText("Hello " +name);

        binding.buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createPost();
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.logout();
            }
        });



        binding.textViewPaging.setText("Loading ...");

        binding.imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPage == 1) {
                    binding.imageViewPrevious.setEnabled(false);
                } else {
                    currentPage = currentPage - 1;
                    fetchPosts(currentPage);
                }
            }
        });

        binding.imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPage == totalPageCount) {
                    binding.imageViewNext.setEnabled(false);
                } else {
                    currentPage = currentPage + 1;
                    fetchPosts(currentPage);
                }
            }
        });

        getActivity().setTitle(R.string.posts_label);
        fetchPosts(currentPage);
    }


    private void fetchPosts(int page) {

        OkHttpClient client = new OkHttpClient();
        String postsUrl = "https://www.theappsdr.com/posts?page=" + page; // Requesting specific page


        Request request = new Request.Builder()
                .url(postsUrl)
                .header("Authorization", "BEARER " + sharedPreferences.getString("token","null"))
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // Handle failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject root = new JSONObject(responseData);
                        JSONArray posts = root.getJSONArray("posts");
                        totalPageCount = root.getInt("totalCount")/10;

                        // Clear previous posts
                        mPosts.clear();

                        for(int i = 0; i < posts.length(); i++) {
                            JSONObject post = posts.getJSONObject(i);
                            mPosts.add(new Post(post.getString("created_by_name"),
                                    post.getString("post_id"),
                                    post.getString("created_by_uid"),
                                    post.getString("post_text"),
                                    post.getString("created_at")));
                        }

                        // Update UI with retrieved posts and pagination info
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                binding.textViewPaging.setText("Showing Page " + currentPage + " out of " + totalPageCount);
                                binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
                                postsAdapter = new PostsAdapter();
                                binding.recyclerViewPosts.setAdapter(postsAdapter);

                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Handle unsuccessful response
                }
            }
        });
    }






    class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
        @NonNull
        @Override
        public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PostRowItemBinding binding = PostRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new PostsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
            Post post = mPosts.get(position);
            holder.setupUI(post);
            if(post.getCreated_by_uid().equals(sharedPreferences.getString("user_id","null"))) {
                holder.mBinding.imageViewDelete.setVisibility(View.VISIBLE);
            } else {
                holder.mBinding.imageViewDelete.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }

        class PostsViewHolder extends RecyclerView.ViewHolder {
            PostRowItemBinding mBinding;
            Post mPost;
            public PostsViewHolder(PostRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Post post){
                mPost = post;
                mBinding.textViewPost.setText(post.getPost_text());
                mBinding.textViewCreatedBy.setText(post.getCreated_by_name());
                mBinding.textViewCreatedAt.setText(post.getCreated_at());

                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeleteConfirmationDialog();
                    }
                });
            }

            private void showDeleteConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                builder.setMessage("Are you sure you want to delete this post?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                deletePost();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        });
                // Create the AlertDialog object and show it
                builder.create().show();
            }


            private void deletePost() {
                OkHttpClient client = new OkHttpClient();
                String deleteUrl = "https://www.theappsdr.com/posts/delete"; // Endpoint for deleting a post


                FormBody formBody = new FormBody.Builder()
                        .add("post_id",mPost.getPost_id())
                        .build();


                Request request = new Request.Builder()
                        .url(deleteUrl)
                        .header("Authorization", "BEARER " + sharedPreferences.getString("token","null"))
                        .post(formBody)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        // Handle failure
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            // Post deleted successfully, refresh the post list
                            fetchPosts(currentPage);
                        } else {
                            // Handle unsuccessful response
                        }
                    }
                });
            }

        }

    }

    PostsListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (PostsListener) context;
    }

    interface PostsListener{
        void logout();
        void createPost();
    }
}