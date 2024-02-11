package com.example.assignment03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class IdentificationActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextTextEmailAddress;
    RadioGroup radioGroup;
    public static final String KEY_PERSON = "PERSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);
        setTitle("Identification info");

        editTextName = findViewById(R.id.editTextName);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        radioGroup = findViewById(R.id.radioGroup);




        findViewById(R.id.buttonNext1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= editTextName.getText().toString();
                String email = editTextTextEmailAddress.getText().toString();

                if(name.isEmpty())
                {

                    Toast.makeText(IdentificationActivity.this,"Enter Name" ,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(email.isEmpty())
                {
                    Toast.makeText(IdentificationActivity.this,"Enter Email" ,Toast.LENGTH_SHORT).show();
                    return;
                }

                String role = getString(R.string.RB_student);
                int selected = radioGroup.getCheckedRadioButtonId();

                if(selected == R.id.radioButtonEmployee)
                {
                    role = getString(R.string.RB_Employee);
                }else if (selected == R.id.radioButtonOther)
                {
                    role = getString(R.string.RB_Other);
                }

                Person person = new Person(name, email, role,"education","livingStatus","maritalStatus","income");


                Intent intent = new Intent(IdentificationActivity.this, DemographicActivity.class);
                intent.putExtra(KEY_PERSON,person);
                startActivity(intent);

            }
        });



    }
}