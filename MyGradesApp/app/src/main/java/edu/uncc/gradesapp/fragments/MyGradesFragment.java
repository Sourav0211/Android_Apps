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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.uncc.gradesapp.R;
import edu.uncc.gradesapp.databinding.FragmentMyGradesBinding;
import edu.uncc.gradesapp.databinding.GradeRowItemBinding;
import edu.uncc.gradesapp.models.Grade;

public class MyGradesFragment extends Fragment {



    ArrayList<Grade> grades = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    GradesAdapater gardeAdapter;

    Double totalGradePoint=0.0;
    Double totalCreditHours=0.0;

    Double gpa=0.0;





    public MyGradesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_add){
            mListener.gotoAddGrade();
            return true;
        } else if(item.getItemId() == R.id.action_logout) {
            mListener.logout();
            return true;
        } else if(item.getItemId() == R.id.action_reviews){
            mListener.gotoViewReviews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    FragmentMyGradesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Grades");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        gardeAdapter = new GradesAdapater();
        binding.recyclerView.setAdapter(gardeAdapter);

        getGrades();





    }

    public void getGrades()
    {

        listenerRegistration = FirebaseFirestore.getInstance().collection("grades").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null)
                {
                    error.printStackTrace();
                    return;
                }
                else{
                    grades.clear();
                    totalGradePoint=0.0;
                    totalCreditHours=0.0;
                    gpa=0.0;

                    for (QueryDocumentSnapshot doc : value) {
                        Grade grade = doc.toObject(Grade.class);
                        if(grade.getOwnerId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            grades.add(grade);
                            totalCreditHours+=grade.getCourseHours();
                            totalGradePoint+=grade.getGradeValue();
                        }
                    }
                    if(totalCreditHours > 0.0) {
                        gpa = totalGradePoint /totalCreditHours  ;
                    }
                    gardeAdapter.notifyDataSetChanged();
                    binding.textViewHours.setText(Double.toString(totalCreditHours));
                    binding.textViewGPA.setText(String.format("%.2f", gpa));

                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration !=null)
        {
            listenerRegistration.remove();
            listenerRegistration = null;
        }

    }

    MyGradesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //try catch block
        try {
            mListener = (MyGradesListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement MyGradesListener");
        }
    }



    class GradesAdapater extends RecyclerView.Adapter<GradesAdapater.GradesViewHolder>{
        @NonNull
        @Override
        public GradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GradeRowItemBinding gradeRowItemBinding = GradeRowItemBinding.inflate(getLayoutInflater(),parent,false);

            return new GradesViewHolder(gradeRowItemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GradesViewHolder holder, int position) {

            Grade grade = grades.get(position);
            holder.setupUI(grade);
        }

        @Override
        public int getItemCount() {
            return grades.size();
        }

        class GradesViewHolder extends  RecyclerView.ViewHolder{
            GradeRowItemBinding mBindig;
            public GradesViewHolder(GradeRowItemBinding gradeRowItemBinding) {
                super(gradeRowItemBinding.getRoot());
                mBindig = gradeRowItemBinding;
            }

            public void setupUI(Grade grade){

                mBindig.textViewSemester.setText(grade.getSemesterName());
                mBindig.textViewCourseName.setText(grade.getCourseName());
                mBindig.textViewLetterGrade.setText(grade.getGradeLetter());
                mBindig.textViewCourseNumber.setText(grade.getCourseNumber());
                mBindig.textViewCreditHours.setText(Integer.toString(grade.getCourseHours())+" Credit Hourse");


                mBindig.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseFirestore.getInstance().collection("grades").document(grade.getDocId()).delete();
                    }
                });


            }
        }
    }













    public interface MyGradesListener {
        void gotoAddGrade();
        void logout();
        void gotoViewReviews();
    }
}