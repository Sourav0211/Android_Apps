package com.example.assignment03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class SelectLivingStatusActivity extends AppCompatActivity {


    String livingStatus;
    RadioGroup radioGroupLivingStatus ;
    public static final String KEY_LS = "LS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_living_status);
        radioGroupLivingStatus = findViewById(R.id.radioGroupSelectLivingStatus);


        findViewById(R.id.buttonSubmitSLS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int select = radioGroupLivingStatus.getCheckedRadioButtonId();

                livingStatus = getString(R.string.HomeOwnerLabel);
                if(select == R.id.radioButtonRenter)
                {
                    livingStatus = getString(R.string.RenterLabel);
                } else  if(select == R.id.radioButtonLessee)
                {
                    livingStatus = getString(R.string.LesseeLabel);
                } else  if(select == R.id.radioButtonLSOther)
                {
                    livingStatus = getString(R.string.LSOtherLabel);
                } else  if(select == R.id.radioButtonLSNTS)
                {
                    livingStatus = getString(R.string.NTSLabel);
                }
                Intent intent = new Intent();
                intent.putExtra(KEY_LS,livingStatus);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        findViewById(R.id.buttonCancelSLS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}




 /*   <?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".SelectLivingStatusActivity">

<TextView
        android:id="@+id/textView13"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="32dp"
                android:text="@string/SelectLivingStatusLabel"
                android:textSize="24sp"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

<TextView
        android:id="@+id/textView12"
                android:layout_width="349dp"
                android:layout_height="74dp"
                android:layout_marginStart="32dp"
                android:layout_marginTop="32dp"
                android:text="@string/LivingStatusQuetion"
                android:textSize="20sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textView13"
                tools:ignore="TextSizeCheck" />

<RadioGroup
        android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="32dp"
                android:layout_marginTop="32dp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textView12" >

<RadioButton
            android:id="@+id/radioButton"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:checked="true"
                    android:text="@string/HomeOwnerLabel" />

<RadioButton
            android:id="@+id/radioButtonRenter"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="@string/RenterLabel" />

<RadioButton
            android:id="@+id/radioButtonLessee"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="@string/LesseeLabel" />

<RadioButton
            android:id="@+id/radioButtonLSOther"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="@string/LSOtherLabel" />

<RadioButton
            android:id="@+id/radioButtonLSNTS"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="@string/NTSLabel" />
</RadioGroup>

<Button
        android:id="@+id/buttonCancelSLS"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="24dp"
                android:layout_marginEnd="24dp"
                android:text="@string/Button_Cancel"
                app:layout_constraintEnd_toStartOf="@+id/space2"
                app:layout_constraintStart_toStartOf="parent"
                tools:layout_editor_absoluteY="606dp" />

<Button
        android:id="@+id/buttonSubmitSLS"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="24dp"
                android:layout_marginEnd="24dp"
                android:text="@string/Button_Submit"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.0"
                app:layout_constraintStart_toEndOf="@+id/space2"
                tools:layout_editor_absoluteY="606dp" />

<Space
        android:id="@+id/space2"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textView12" />
</androidx.constraintlayout.widget.ConstraintLayout>

  */