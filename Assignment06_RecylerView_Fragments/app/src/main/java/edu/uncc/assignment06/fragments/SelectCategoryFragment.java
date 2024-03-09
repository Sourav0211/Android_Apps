package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentSelectCategoryBinding;
import edu.uncc.assignment06.databinding.SelectionListItemBinding;
import edu.uncc.assignment06.databinding.TaskListItemBinding;
import edu.uncc.assignment06.models.Data;
import edu.uncc.assignment06.models.Task;


public class SelectCategoryFragment extends Fragment {

    String category="N/A";
    String[] categoryList;
    public SelectCategoryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectCategoryBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater,container,false);
        return binding.getRoot();
        // Inflate the layout for this fragment

    }
    CategoryAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryList = Data.categories;

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter= new CategoryAdapter();
        binding.recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelCategory();
            }
        });

    }

    SelectCategoryListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectCategoryListener) context;
    }

    public interface SelectCategoryListener{
        void sendCategory(String category);

        void cancelCategory();
    }




    class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            SelectionListItemBinding mbinding = SelectionListItemBinding.inflate(getLayoutInflater(),parent,false);
            return new CategoryViewHolder(mbinding);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
            category = categoryList[position];
            holder.setupUI(category);
        }

        @Override
        public int getItemCount() {
            return categoryList.length;
        }

        class CategoryViewHolder extends RecyclerView.ViewHolder{
            SelectionListItemBinding mBinding;

            public CategoryViewHolder(SelectionListItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(String category)
            {
                mBinding.textView.setText(category);

                mBinding.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.sendCategory(category);
                    }
                });
            }
        }
    }
}