package com.example.assignment03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SelectHouseholdIncomeActivity extends AppCompatActivity {
    String income;
    SeekBar seekBar;
    TextView textViewIncome;
    public static final String KEY_HHI = "HHI";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_household_income);

        seekBar = findViewById(R.id.seekBar);
        textViewIncome = findViewById(R.id.textView14);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress>0 && progress < 25)
                {
                    textViewIncome.setText("< 25K");
                } else if(progress>25 && progress < 50)
                {
                    textViewIncome.setText("$25K to <$50K");
                } else if(progress>50 && progress < 75)
                {
                    textViewIncome.setText("$50K to <$100K");
                } else if(progress>75 && progress < 100)
                {
                    textViewIncome.setText("$100K to <$200K");
                } else if(progress == 100)
                {
                    textViewIncome.setText(">$200K");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        findViewById(R.id.buttonSubmitHHI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                income = textViewIncome.getText().toString();

                Intent intent = new Intent();
                intent.putExtra(KEY_HHI,income);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


        findViewById(R.id.buttonCancelHHI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}