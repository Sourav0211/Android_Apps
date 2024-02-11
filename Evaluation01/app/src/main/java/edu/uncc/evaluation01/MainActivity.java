package edu.uncc.evaluation01;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextBillAmount;

    TextView textViewSelectedTip;
    int tip;
    public static final String KEY_BILL = "BILL";

    private ActivityResultLauncher<Intent> StartSelectTipActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK)
                    {
                            Intent data = result.getData();
                            tip = data.getIntExtra(SelectTipActivity.KEY_TIP,0);
                            textViewSelectedTip.setText(String.valueOf(tip)+"%");

                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewSelectedTip = findViewById(R.id.textViewSelectedTip);
        editTextBillAmount = findViewById(R.id.editTextBillAmount);

        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = editTextBillAmount.getText().toString();

                if(amount.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Enter Amount",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(textViewSelectedTip.getText().toString().contains("N/A"))
                {
                    Toast.makeText(MainActivity.this,"Select Tip Percent",Toast.LENGTH_SHORT).show();
                    return;
                }
                int intamount = Integer.valueOf(amount);
                int tipAmount =  intamount * (tip/100);
                int totalbill =  intamount + tipAmount;

                Bill bill = new Bill( intamount, tip);

                Intent intent = new Intent(MainActivity.this, BillSummaryActivity.class);
                intent.putExtra(KEY_BILL,bill);
                setResult(RESULT_OK);
                startActivity(intent);
            }
        });



        findViewById(R.id.buttonSelectTip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SelectTipActivity.class);
                StartSelectTipActivityForResult.launch(intent);
            }
        });

        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextBillAmount.getText().clear();
                textViewSelectedTip.setText("N/A");
            }
        });
    }
}