package com.example.assignment03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class SelectEducationActivity extends AppCompatActivity {
    String education;
    RadioGroup RadioGroupEducation;
    public static final String KEY_EDU = "EDU_LEVEL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_education);
        setTitle("Select Education level");

        RadioGroupEducation = findViewById(R.id.RadioGroupEducation);




        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                education = "Below High School";
                int select = RadioGroupEducation.getCheckedRadioButtonId();

                if(select == R.id.radioButtonHighSchool)
                {
                    education = "High School";

                }else if(select == R.id.radioButtonBEDegree)
                {
                    education = "Bachelor's Degree";

                }else if(select == R.id.radioButtonMSDegree)
                {
                    education = "Master's Degree";

                }else if(select == R.id.radioButtonPHD)
                {
                    education = "Ph.D or Higher";

                }else if(select == R.id.radioButtonTradeSchool)
                {
                    education = "Trade School";

                }else if(select == R.id.radioButtonOtherSE)
                {
                    education = "other";
                }

                Intent intent = new Intent();
                intent.putExtra(KEY_EDU, education);
                setResult(RESULT_OK,intent);
                finish();

            }
        });




    }
}
