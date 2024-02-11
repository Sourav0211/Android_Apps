package edu.uncc.evaluation01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class BillSummaryActivity extends AppCompatActivity {
    int amount;
    int percent;
    double tipAmount;
    double totalBill;
    TextView textViewBillAmount,textViewTipPercentage,textViewTotalBill,textViewTipAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_summary);

        textViewBillAmount = findViewById(R.id.textViewBillAmount);
        textViewTipPercentage = findViewById(R.id.textViewTipPercentage);
        textViewTotalBill =  findViewById(R.id.textViewTotalBill);
        textViewTipAmount = findViewById(R.id.textViewTipAmount);


        if(getIntent() != null && getIntent().getExtras()!=null && getIntent().hasExtra(MainActivity.KEY_BILL))
        {
            Bill bill = (Bill) getIntent().getSerializableExtra(MainActivity.KEY_BILL);
             amount = bill.billAmount;
             percent = bill.tipPercent;
        }

        tipAmount = amount * ((double) percent / 100);
        totalBill = amount + tipAmount;

        textViewTipAmount.setText(String.valueOf(tipAmount));
        textViewBillAmount.setText(String.valueOf(amount));
        textViewTotalBill.setText(String.valueOf(totalBill));
        textViewTipPercentage.setText(String.valueOf(percent));

        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
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
tools:context=".BillSummaryActivity">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/bill_summary"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/textView4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="16dp"
        android:text="@string/bill_amount"
        android:textSize="18sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView" />

    <TextView
        android:id="@+id/textView7"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="@string/total_bill"
        android:textSize="18sp"
        app:layout_constraintStart_toStartOf="@+id/textView6"
        app:layout_constraintTop_toBottomOf="@+id/textView6" />

    <TextView
        android:id="@+id/textViewTipPercentage"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/zero_dollars"
        android:textSize="18sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="@+id/textView5" />

    <TextView
        android:id="@+id/textViewTipAmount"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/zero_dollars"
        android:textSize="18sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="@+id/textView6" />

    <TextView
        android:id="@+id/textViewBillAmount"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/zero_dollars"
        android:textSize="18sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="@+id/textView4" />

    <TextView
        android:id="@+id/textView6"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="@string/tip_amount"
        android:textSize="18sp"
        app:layout_constraintStart_toStartOf="@+id/textView5"
        app:layout_constraintTop_toBottomOf="@+id/textView5" />

    <TextView
        android:id="@+id/textView5"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="@string/tip"
        android:textSize="18sp"
        app:layout_constraintStart_toStartOf="@+id/textView4"
        app:layout_constraintTop_toBottomOf="@+id/textView4" />

    <TextView
        android:id="@+id/textViewTotalBill"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/zero_dollars"
        android:textSize="18sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="@+id/textView7" />

    <Button
        android:id="@+id/buttonBack"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="32dp"
        android:text="@string/back"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView7" />

</androidx.constraintlayout.widget.ConstraintLayout>*/