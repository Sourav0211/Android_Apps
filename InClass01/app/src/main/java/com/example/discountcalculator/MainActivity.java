package com.example.discountcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    double discount=0.0;
    int percent;
    TextView textViewProgress;
    double price;
    TextView textViewDiscount;
    TextView textViewFinaPrice;
    String userInput;
    RadioGroup radioGroupPercent;
    EditText editTextItemPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupPercent = findViewById(R.id.radioGroupPercent);
        seekBar = findViewById(R.id.seekBar);
        textViewProgress = findViewById(R.id.textViewProgress);



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textViewProgress.setText(String.valueOf(progress + "%"));
                percent = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDiscount = findViewById(R.id.textViewDiscount);
                textViewFinaPrice = findViewById(R.id.textViewFinaPrice);
                editTextItemPrice = findViewById(R.id.editTextItemPrice);
                userInput = editTextItemPrice.getText().toString();
               if(userInput.isEmpty())
                {
                    discount = 0.0;
                    textViewDiscount.setText("0.0");
                    textViewFinaPrice.setText("0.0");
                    Toast.makeText(MainActivity.this,"Enter Item Price",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(userInput)< 0){
                   Toast.makeText(MainActivity.this,"Enter Positive Value",Toast.LENGTH_SHORT).show();
                } else
                {
                    if(!userInput.isEmpty())
                        price = Integer.parseInt(userInput);


                    int checkedId= radioGroupPercent.getCheckedRadioButtonId();

                    if(checkedId == R.id.radioButton10)
                    {
                        discount = price * 0.10;
                        price = price - discount;
                        textViewDiscount.setText(String.valueOf(discount));
                        textViewFinaPrice.setText(String.valueOf(price));

                    }
                    else if(checkedId == R.id.radioButton15)
                    {
                        discount = price * 0.15;
                        price = price - discount;
                        DecimalFormat df = new DecimalFormat("#.##");
                        textViewDiscount.setText(df.format(discount));
                        textViewFinaPrice.setText(String.valueOf(price));
                    }
                    else if(checkedId == R.id.radioButton18)
                    {
                        discount = price * 0.18;
                        price = price - discount;
                        textViewDiscount.setText(String.valueOf(discount));
                        textViewFinaPrice.setText(String.valueOf(price));
                    }
                    else if(checkedId == R.id.radioButtonC)
                    {
                        discount = price * (percent/100.0);
                        price = price - discount;
                        textViewDiscount.setText(String.valueOf(discount));
                        textViewFinaPrice.setText(String.valueOf(price));


                    }
                }
                }



        });



        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroupPercent.check(R.id.radioButton10);
                seekBar.setProgress(25);
                textViewDiscount = findViewById(R.id.textViewDiscount);
                textViewDiscount.setText("0.00");
                textViewFinaPrice = findViewById(R.id.textViewFinaPrice);
                textViewFinaPrice.setText("0.00");
                editTextItemPrice = findViewById(R.id.editTextItemPrice);
                editTextItemPrice.getText().clear();
                textViewProgress = findViewById(R.id.textViewProgress);
                textViewProgress.setText("25%");
                discount = 0.0;
                price = 0.0;




            }
        });

    }


}