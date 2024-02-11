package com.example.assignment03;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DemographicActivity extends AppCompatActivity {


    String edulevel, maritalStatus ,livingStatus ,HHIncome;

    TextView textViewEducation;
    TextView textViewMaritalStatus;
    TextView textViewLivingStatus;
    TextView textViewIncome;
    Person person1;
    public static final String KEY_PERSON_PROFILE = "PROFILE_PERSON";
    private ActivityResultLauncher<Intent> StartSelectEducationForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK)
                    {
                        Intent data = result.getData();
                        edulevel = data.getStringExtra(SelectEducationActivity.KEY_EDU);
                        textViewEducation.setText(edulevel);
                        person1.setEducation(edulevel);
                    }else{

                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> StartSelectMaritalStatusForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK)
                    {
                        Intent data = result.getData();
                        maritalStatus = data.getStringExtra(SelectMaritalStatusActivity.KEY_MS);
                        textViewMaritalStatus.setText(maritalStatus);
                        person1.setMaritalStatus(maritalStatus);

                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> StartLivingStatusForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK)
                    {
                        Intent data = result.getData();
                        livingStatus = data.getStringExtra(SelectLivingStatusActivity.KEY_LS);
                        textViewLivingStatus.setText(livingStatus);
                        person1.setLivingStatus(livingStatus);
                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> StartIncomeActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK)
                    {
                        Intent data = result.getData();
                        HHIncome =  data.getStringExtra(SelectHouseholdIncomeActivity.KEY_HHI);
                        textViewIncome.setText(HHIncome);
                        person1.setIncome(HHIncome);
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demographic);

        textViewEducation = findViewById(R.id.textViewEducation);
        textViewMaritalStatus = findViewById(R.id.textViewMaritalStatus);
        textViewLivingStatus = findViewById(R.id.textViewLivingStatus);
        textViewIncome = findViewById(R.id.textViewIncome);
        setTitle("Demographic Info");

        if(getIntent()!=null && getIntent().getExtras()!= null && getIntent().hasExtra(IdentificationActivity.KEY_PERSON))
        {
            person1 = (Person) getIntent().getSerializableExtra(IdentificationActivity.KEY_PERSON);
        }





                findViewById(R.id.buttonEducation).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DemographicActivity.this, SelectEducationActivity.class);
                        StartSelectEducationForResult.launch(intent);
                    }
                });

                findViewById(R.id.buttonMaritalStatus).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DemographicActivity.this, SelectMaritalStatusActivity.class);
                        StartSelectMaritalStatusForResult.launch(intent);
                    }
                });

                findViewById(R.id.buttonLivingStatus).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DemographicActivity.this , SelectLivingStatusActivity.class);
                        StartLivingStatusForResult.launch(intent);
                    }
                });

                findViewById(R.id.buttonIncome).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DemographicActivity.this , SelectHouseholdIncomeActivity.class);
                        StartIncomeActivityForResult.launch(intent);
                    }
                });





        findViewById(R.id.buttonNext3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DemographicActivity.this, ProfileActivity.class);
                        intent.putExtra(KEY_PERSON_PROFILE,person1);
                        startActivity(intent);
                    }
                });
    }
}




