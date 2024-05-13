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
