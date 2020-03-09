package com.example.mytimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


//Start button should be disabled until valid entries (all numeric) are entered into H/M/S. At least one of the fields should be populated before the start button is enabled.

//- Reset button is always enabled and will stop the current timer and clear the numerics in the text fields. This should disable start countdown button

//- When the countdown is complete, a toast should appear ‘Countdown is complete’









public class MainActivity extends AppCompatActivity {

    // Edit Texts
    private EditText mEditTextViewHour;
    private EditText mEditTextViewMinute;
    private EditText mEditTextViewSecond;
    private TextView mTextViewCountDown;

    // Buttons
    private Button mButtonStart;
    private Button mButtonPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = 0;
    private TextWatcher fieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String hourInput = (String) mEditTextViewHour.getText().toString().trim();
            String minuteInput = (String) mEditTextViewMinute.getText().toString().trim();
            String secondInput = (String) mEditTextViewSecond.getText().toString().trim();

            mButtonStart.setEnabled(!hourInput.isEmpty()
                    || !minuteInput.isEmpty()
                    || !secondInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Views
        mEditTextViewHour = findViewById(R.id.edit_text_hour);
        mEditTextViewMinute = findViewById(R.id.edit_text_minute);
        mEditTextViewSecond = findViewById(R.id.edit_text_seconds);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        // Set Buttons
        mButtonStart = findViewById(R.id.button_start);
        mButtonPause = findViewById(R.id.button_pause);
        mButtonReset = findViewById(R.id.button_reset);

        // Append Text change listeners
        mEditTextViewHour.addTextChangedListener(fieldWatcher);
        mEditTextViewMinute.addTextChangedListener(fieldWatcher);
        mEditTextViewSecond.addTextChangedListener(fieldWatcher);


        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                int totalMilliseconds = getTotalMilliseconds();
                startTimer(totalMilliseconds);
                //mButtonPause.setEnabled(true);
            }
        });


        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resetTimer();

                mCountDownTimer.cancel();
                mEditTextViewHour.setText("");
                mEditTextViewMinute.setText("");
                mEditTextViewSecond.setText("");
            }
        });
    }


    // do the maths ro return milliseconds
    private int getTotalMilliseconds() {
        String hours = mEditTextViewHour.getText().toString();
        String minutes = mEditTextViewMinute.getText().toString();
        String seconds = mEditTextViewSecond.getText().toString();

        int h, m, s;
        h = m = s = 0;
        if (!hours.isEmpty()) {
            h = Integer.parseInt(hours) * 3600000;
        }
        if (!minutes.isEmpty()) {
            m = Integer.parseInt(minutes) * 60000;
        }
        if (!seconds.isEmpty()) {
            s = Integer.parseInt(seconds) * 1000;
        }

        int total;
        total = h + m + s;
        return total;
    }


    private void startTimer(int noOfMinutes) {
        mCountDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour, minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                mTextViewCountDown.setText(hms);//set text
            }
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Countdown is complete", Toast.LENGTH_SHORT).show();

            }
        }.start();

    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }


    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
