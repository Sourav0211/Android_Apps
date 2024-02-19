package edu.uncc.assignment04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import edu.uncc.assignment04.R;
import edu.uncc.assignment04.databinding.FragmentSelectIncomeBinding;


public class SelectIncomeFragment extends Fragment {



    public SelectIncomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentSelectIncomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectIncomeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    String income = "";
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress>=0 && progress <2 )
                {
                    binding.textViewHouseHoldIncome.setText("<$25");
                } else if(progress>=2 && progress < 4)
                {
                    binding.textViewHouseHoldIncome.setText("$25K to <$50K");
                } else if(progress>=4 && progress < 6)
                {
                    binding.textViewHouseHoldIncome.setText("$50K to <$100K");
                } else if(progress>=6 && progress < 8)
                {
                    binding.textViewHouseHoldIncome.setText("$100K to <$200K");
                } else if(progress>=8 && progress <=10)
                {
                    binding.textViewHouseHoldIncome.setText(">$200K");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                income = binding.textViewHouseHoldIncome.getText().toString();
                mListener.setIncome(income);
            }
        });
    }
    FragmentSetIncomeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (FragmentSetIncomeListener) context;
    }

    public interface FragmentSetIncomeListener
    {
        void setIncome(String income);
    }

}