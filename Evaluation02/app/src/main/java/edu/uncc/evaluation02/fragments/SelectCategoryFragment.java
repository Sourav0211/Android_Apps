package edu.uncc.evaluation02.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import edu.uncc.evaluation02.R;
import edu.uncc.evaluation02.databinding.FragmentSelectCategoryBinding;
import edu.uncc.evaluation02.models.Data;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link SelectCategoryFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class SelectCategoryFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public SelectCategoryFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SelectCategoryFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static SelectCategoryFragment newInstance(String param1, String param2) {
//        SelectCategoryFragment fragment = new SelectCategoryFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }


    String[] categories;
    ArrayAdapter<String> adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categories = Data.categories;

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,categories);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = categories[position];
                mListener.sendCategory(category);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelSelectCategory();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener= (SelectCategoryListener) context;
    }

    SelectCategoryListener mListener;
    public interface SelectCategoryListener{
        void sendCategory(String category);
        void cancelSelectCategory();
    }


    FragmentSelectCategoryBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater,container,false);
        return binding.getRoot();
        // Inflate the layout for this fragment

    }
}