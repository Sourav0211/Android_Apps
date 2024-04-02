package edu.uncc.gradesapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.CourseReviewRowItemBinding;
import edu.uncc.gradesapp.databinding.FragmentCourseReviewsBinding;
import edu.uncc.gradesapp.models.Course;
import edu.uncc.gradesapp.models.CourseReview;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CourseReviewsFragment extends Fragment {
    public CourseReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    FragmentCourseReviewsBinding binding;
    ArrayList<CourseReview> mCourseReviews = new ArrayList<>();
    ArrayList<Course> mCourses = new ArrayList<>();
    ArrayList<Course> dCourses = new ArrayList<>();
    CourseReviewsAdapter adapter;

    ListenerRegistration listenerRegistration;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Course Reviews");
        adapter = new CourseReviewsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);



        listenerRegistration = db.collection("course").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

            }
        });

        getCourses();

    }

    private final OkHttpClient client = new OkHttpClient();

    private void getCourses(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/cci-courses")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    try {
                        mCourses.clear();
                        JSONObject json = new JSONObject(body);
                        JSONArray courses = json.getJSONArray("courses");
                        for (int i = 0; i < courses.length(); i++) {
                            Course course = new Course(courses.getJSONObject(i));
                            mCourses.add(course);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                setCourses();
//                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Failed to get courses", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void setCourses()
    {

        db.collection("course").limit(1).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            if(task.getResult().isEmpty())
                            {

                                for(Course course : mCourses) {
                                    DocumentReference docRef = db.collection("course").document();
                                    HashMap<String, Object> data = new HashMap<>();


                                    data.put("name", course.getName());
                                    data.put("number", course.getNumber());
                                    data.put("hours", course.getHours());
                                    data.put("courseId", course.getCourseId());
                                    data.put("reviews",0);
                                    data.put("docId",docRef.getId());
                                    ArrayList<String> likes = new ArrayList<>();
                                    data.put("likes",likes);

                                    docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                displayCourses();
                                            }
                                        }
                                    });



                                }
                            }
                            else{
                                displayCourses();
                            }

                        }
                        else{
                            Log.e("Firestore", "Error getting documents: ", task.getException());
                        }

                    }
                });

    }


    public void  displayCourses()
    {
        listenerRegistration = db.collection("course").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null)
                {
                    error.printStackTrace();
                    return;
                }
                else{
                    dCourses.clear();
                    for(QueryDocumentSnapshot doc : value)
                    {
                        Course course = doc.toObject(Course.class);
                        dCourses.add(course);

                    }
                    adapter.notifyDataSetChanged();
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

    class CourseReviewsAdapter extends RecyclerView.Adapter<CourseReviewsAdapter.CourseReviewsViewHolder> {

        @NonNull
        @Override
        public CourseReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CourseReviewRowItemBinding itemBinding = CourseReviewRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new CourseReviewsViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseReviewsViewHolder holder, int position) {
            holder.setupUI(dCourses.get(position));
        }

        @Override
        public int getItemCount() {
            return dCourses.size();
        }

        class CourseReviewsViewHolder extends RecyclerView.ViewHolder {
            CourseReviewRowItemBinding itemBinding;
            Course mCourse;
            public CourseReviewsViewHolder(CourseReviewRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;

            }

            public void setupUI(Course course){
                this.mCourse = course;
                itemBinding.textViewCourseName.setText(course.getName());
                itemBinding.textViewCreditHours.setText(course.getHours() + " Credit Hours");
                itemBinding.textViewCourseNumber.setText(course.getNumber());
                itemBinding.textViewCourseReviews.setText(Integer.toString(course.getReviews()));

                if(mCourse.getLikes()!= null)
                {


                if(mCourse.getLikes().contains(auth.getCurrentUser().getUid()))
                {
                    itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_full);
                }
                else{
                    itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_empty);
                }
                }
                else{
                    itemBinding.imageViewHeart.setImageResource(R.drawable.ic_heart_empty);
                }



                itemBinding.imageViewHeart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mCourse.getLikes() != null) {
                            if (mCourse.getLikes().contains(auth.getCurrentUser().getUid())) {
                                HashMap<String, Object> updateData = new HashMap<>();
                                updateData.put("likes", FieldValue.arrayRemove(auth.getCurrentUser().getUid()));
                                FirebaseFirestore.getInstance().collection("course").document(mCourse.getDocId()).update(updateData);

                            } else {
                                HashMap<String, Object> updateData = new HashMap<>();
                                updateData.put("likes", FieldValue.arrayUnion(auth.getCurrentUser().getUid()));
                                FirebaseFirestore.getInstance().collection("course").document(mCourse.getDocId()).update(updateData);

                            }
                        }
                        else{
                            HashMap<String, Object> updateData = new HashMap<>();
                            updateData.put("likes", FieldValue.arrayRemove(auth.getCurrentUser().getUid()));
                            FirebaseFirestore.getInstance().collection("course").document(mCourse.getDocId()).update(updateData);
                        }

                    }
                });

                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoReviewCourse(mCourse);
                    }
                });

            }


        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cancel_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_cancel){
            mListener.onSelectionCanceled();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    CourseReviewsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (CourseReviewsListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement CourseReviewsListener");
        }
    }

    public interface CourseReviewsListener{
        void onSelectionCanceled();
        void gotoReviewCourse(Course course);
    }
}