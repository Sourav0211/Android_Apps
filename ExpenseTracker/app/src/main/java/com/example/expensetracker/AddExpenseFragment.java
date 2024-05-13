package com.example.expensetracker;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.expensetracker.databinding.FragmentAddExpenseBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExpenseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddExpenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddExpenseFragment newInstance(String param1, String param2) {
        AddExpenseFragment fragment = new AddExpenseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentAddExpenseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddExpenseBinding.inflate(inflater,container,false);
        return binding.getRoot();
        // Inflate the layout for this fragment

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =binding.editTextTextName.getText().toString();
                String amount = binding.editTextTextAmount.getText().toString();
                String category = "";
                int select =  binding.RadioGroup.getCheckedRadioButtonId();
                if(select == binding.radioButton.getId())
                {
                    category = "Housing";
                }
                else if(select == binding.radioButton2.getId())
                {
                    category = "Transportation";
                }else if(select == binding.radioButton3.getId())
                {
                    category = "Food";
                }else if(select == binding.radioButton4.getId())
                {
                    category = "Health";
                }else if(select == binding.radioButton5.getId())
                {
                    category = "Other";
                }

                if(name.isEmpty() || amount.isEmpty() || category.isEmpty())
                {
                    Toast.makeText(getContext(),"Enter all values",Toast.LENGTH_SHORT).show();

                }
                else{
                    AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "expense-db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();

                    Expense expense = new Expense(name,category, amount);
                    db.dao().insertAll(expense);
                    mListener.doneAddingExpense();


                }
            }
        });
    }


    AddExpenseListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddExpenseListener) context;
    }

    public interface AddExpenseListener{
        void doneAddingExpense();
    }
}