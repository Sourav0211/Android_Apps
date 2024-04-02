package edu.uncc.assignment09;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.uncc.assignment09.databinding.FragmentPostsBinding;
import edu.uncc.assignment09.databinding.PostRowItemBinding;
import edu.uncc.assignment09.models.Post;

public class PostsFragment extends Fragment {
    public PostsFragment() {
        // Required empty public constructor
    }

    FragmentPostsBinding binding;
    PostsAdapter postsAdapter;
    ArrayList<Post> mPosts = new ArrayList<>();
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    ListenerRegistration listenerRegistration ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewTitle.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

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

        binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        postsAdapter = new PostsAdapter();
        binding.recyclerViewPosts.setAdapter(postsAdapter);




        getActivity().setTitle(R.string.posts_label);

        getPostsCollection();
    }

    void getPostsCollection(){
        // Get posts from Firestore
        listenerRegistration = db.collection("posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null)
                {
                    error.printStackTrace();
                    return;
                }
                else{
                    mPosts.clear();

                    for (QueryDocumentSnapshot doc : value) {
                        Post post= doc.toObject(Post.class);
                        mPosts.add(post);
                    }
                    postsAdapter.notifyDataSetChanged();

                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration != null)
        {
            listenerRegistration.remove();
            listenerRegistration = null;
        }
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
                mBinding.textViewPost.setText(post.getPostText());
                mBinding.textViewCreatedBy.setText(post.getCreatedByName());

                // Set the created at time
                if(post.getCreatedAt() == null) {
                    mBinding.textViewCreatedAt.setText("N/A");
                } else {
                    Date date = post.getCreatedAt().toDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                    mBinding.textViewCreatedAt.setText(sdf.format(date));
                }

                if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(mPost.getCreatedByUid()))
                {
                    mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                    mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            FirebaseFirestore.getInstance().collection("posts").document(mPost.getDocId()).delete();
                        }
                    });

                }
                else{
                    mBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }

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