package com.example.expensetracker;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expensetracker.databinding.ExpenseItemBinding;
import com.example.expensetracker.databinding.FragmentExpenseBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExpenseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenseFragment newInstance(String param1, String param2) {
        ExpenseFragment fragment = new ExpenseFragment();
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

    FragmentExpenseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentExpenseBinding.inflate(inflater,container,false);
        return binding.getRoot();


    }
    AppDatabase db;
    List<Expense> expenses = new ArrayList<>();

    ExpenseAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExpenseAdapter();
        binding.recyclerView.setAdapter(adapter);

        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "expense-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();



//        //String date, String sleepHrs, String sleepQuality, String exerciseTime, int weight
//        DetailsModel detailsModel = new DetailsModel(date, sleepHrs, sleepQuality, exerciseTime, weight);
//        db.dao().insertAll(detailsModel);





        getData();

        binding.buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddExpenseFragment();
            }
        });

        binding.buttonGotoSetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSetUpFragment();
            }
        });


    }

    void getData()
    {
        expenses = db.dao().getAll();
        calculateSum(expenses);
        adapter.notifyDataSetChanged();
    }

    void calculateSum(List<Expense> expenses) {
        int totalExpense = 0;
        int housingExp = 0;
        int transportationExp = 0;
        int foodExp = 0;
        int healthExp = 0;
        int otherExp = 0;

        for (Expense expense : expenses) {
            String amountStr = expense.getAmount();
            int amount = Integer.parseInt(amountStr);
            totalExpense += amount;

            switch (expense.getCategory()) {
                case "Housing":
                    housingExp += amount;
                    break;
                case "Transportation":
                    transportationExp += amount;
                    break;
                case "Food":
                    foodExp += amount;
                    break;
                case "Health":
                    healthExp += amount;
                    break;
                default:
                    otherExp += amount;
                    break;
            }
        }


        binding.textViewHousingExp.setText("Housing Expense: " + String.valueOf(housingExp));
        binding.textViewFoodExp.setText("Food Expense: " + String.valueOf(foodExp));
        binding.textViewHealthExp.setText("Health Expense: " + String.valueOf(healthExp));
        binding.textViewTransportaionExp.setText("Transportation Expense: " + String.valueOf(transportationExp));
        binding.textViewOtherExp.setText("Other Expense: " + String.valueOf(otherExp));
        binding.textViewTotalExpenses.setText(String.valueOf(totalExpense));


    }


    class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>{
        @NonNull
        @Override
        public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ExpenseItemBinding mBinidng = ExpenseItemBinding.inflate(getLayoutInflater(),parent,false);
            return new ExpenseViewHolder(mBinidng);


        }

        @Override
        public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {

            Expense expense = expenses.get(position);
            holder.setupUI(expense);
        }

        @Override
        public int getItemCount() {
            return expenses.size();
        }

        class ExpenseViewHolder extends RecyclerView.ViewHolder{
            ExpenseItemBinding itemBinding;
            public ExpenseViewHolder( ExpenseItemBinding mBinidng) {
                super(mBinidng.getRoot());
                this.itemBinding = mBinidng;
            }

            public void setupUI(Expense expense)
            {
                itemBinding.textViewExpenseName.setText(expense.getName());
                itemBinding.textViewExpenseCategory.setText(expense.getCategory());
                itemBinding.textViewExpenseAmount.setText(expense.getAmount());

                itemBinding.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.dao().delete(expense);
                        getData();
                    }
                });
            }
        }
    }

    ExpenseFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener =  (ExpenseFragmentListener) context;
    }

    public interface ExpenseFragmentListener{
        void gotoSetUpFragment();
        void gotoAddExpenseFragment();
    }
}