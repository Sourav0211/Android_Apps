package com.example.assignment03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    TextView textViewName, textViewEmail,textViewRole, textViewEducation, textViewLivingStatus, textViewMaritalstatus, textViewIncome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewName = findViewById(R.id.textViewProfileName);
        textViewEmail = findViewById(R.id.textViewProfileEmailFinal);
        textViewRole = findViewById(R.id.textViewProfileRoleFinal);
        textViewEducation = findViewById(R.id.textViewProfileEducationFinal);
        textViewLivingStatus = findViewById(R.id.textViewProfileLSFinal);
        textViewMaritalstatus = findViewById(R.id.textViewProfileMSFinal);
        textViewIncome = findViewById(R.id.textViewProfileIncomeFinal);

        if(getIntent()!=null && getIntent().getExtras()!= null && getIntent().hasExtra(DemographicActivity.KEY_PERSON_PROFILE))
        {
            Person person2 = (Person) getIntent().getSerializableExtra(DemographicActivity.KEY_PERSON_PROFILE);

            textViewName.setText(person2.name);
            textViewEmail.setText(person2.email);
            textViewRole.setText(person2.role);
            textViewEducation.setText(person2.education);
            textViewMaritalstatus.setText(person2.maritalStatus);
            textViewLivingStatus.setText(person2.livingStatus);
            textViewIncome.setText(person2.income);

        }
    }
}

