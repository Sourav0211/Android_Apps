package edu.uncc.evaluation01;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class SelectTipActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    SeekBar seekBar;
    TextView textViewProgress;
    int tipPercentCustom = 25;
    int tipPercent;
    public static final String KEY_TIP = "TIP_PERCENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tip);

        textViewProgress = findViewById(R.id.textViewProgress);
        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewProgress.setText(progress+"%");
                tipPercentCustom = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup = findViewById(R.id.radioGroup);
                int select = radioGroup.getCheckedRadioButtonId();

                if(select == R.id.radioButton10)
                {
                    tipPercent = 10;
                } else if (select == R.id.radioButton15) {
                    tipPercent = 15;

                } else if (select == R.id.radioButton18) {
                    tipPercent = 18;

                }else if (select == R.id.radioButtonCustom) {
                    tipPercent = tipPercentCustom;
                }

                Intent intent = new Intent();
                intent.putExtra(KEY_TIP,tipPercent);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

/*
* <?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp"
    tools:context=".SelectTipActivity">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/selected_tip"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/textView2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/tip"
        android:textSize="18sp"
        app:layout_constraintBottom_toBottomOf="@+id/radioGroup"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@+id/radioGroup" />

    <TextView
        android:id="@+id/textView3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="@string/custom_percentage"
        android:textSize="18sp"
        app:layout_constraintStart_toStartOf="@+id/textView2"
        app:layout_constraintTop_toBottomOf="@+id/radioGroup" />



    <RadioGroup
        android:id="@+id/radioGroup"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:orientation="horizontal"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/textView2"
        app:layout_constraintTop_toBottomOf="@+id/textView">

        <RadioButton
            android:checked="true"
            android:id="@+id/radioButton10"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@string/ten_percent" />

        <RadioButton
            android:id="@+id/radioButton15"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@string/fifteen_percent" />

        <RadioButton
            android:id="@+id/radioButton18"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@string/eighteen_percent" />

        <RadioButton
            android:id="@+id/radioButtonCustom"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@string/custom" />
    </RadioGroup>

    <SeekBar
        android:id="@+id/seekBar"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginEnd="8dp"
        android:max="50"
        android:progress="25"
        app:layout_constraintBottom_toBottomOf="@+id/textView3"
        app:layout_constraintEnd_toStartOf="@+id/textViewProgress"
        app:layout_constraintStart_toEndOf="@+id/textView3"
        app:layout_constraintTop_toTopOf="@+id/textView3" />

    <TextView
        android:id="@+id/textViewProgress"
        android:layout_width="36dp"
        android:layout_height="wrap_content"
        android:text="25%"
        android:textAlignment="center"
        app:layout_constraintBottom_toBottomOf="@+id/textView3"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="@+id/textView3" />

    <Button
        android:id="@+id/buttonSubmit"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="24dp"
        android:text="@string/submit"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/space"
        app:layout_constraintTop_toBottomOf="@+id/textView3" />

    <Button
        android:id="@+id/buttonCancel"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginEnd="16dp"
        android:text="@string/cancel"
        app:layout_constraintEnd_toStartOf="@+id/space"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@+id/buttonSubmit" />

    <Space
        android:id="@+id/space"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/seekBar" />

</androidx.constraintlayout.widget.ConstraintLayout>*/