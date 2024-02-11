package com.example.assignment03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class SelectMaritalStatusActivity extends AppCompatActivity {
    String ms;
    RadioGroup radioGroup;
    public static final String KEY_MS = "MS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_marital_status);


        radioGroup = findViewById(R.id.radioGroup2);

        findViewById(R.id.buttonSubmitSMS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int select = radioGroup.getCheckedRadioButtonId();
                ms = getString(R.string.NotMarriedLael);

                if (select == R.id.radioButtonMarried)
                {
                    ms = getString(R.string.MarriedLabel);
                }else if (select == R.id.radioButtonNTS)
                {
                    ms = getString(R.string.NTSLabel);
                }

                Intent intent = new Intent();
                intent.putExtra(KEY_MS,ms);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        findViewById(R.id.buttonCancelSMS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}