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

/*
* 1 - Start button should be disabled until valid entries (all numeric) are entered into H/M/S.
*   At least one of the fields should be populated before the start button is enabled.
*
* 2 - Reset button is always enabled and will stop the current timer and clear the numerics in
*   the text fields. This should disable start countdown button
*
* 3 - When the countdown is complete, a toast should appear ‘Countdown is complete’
*/

public class MainActivity extends AppCompatActivity {

    // Edit Texts
    private EditText mEditTextViewHour;
    private EditText mEditTextViewMinute;
    private EditText mEditTextViewSecond;
    private TextView mTextViewCountDown;

    // Buttons
    private Button mButtonStart;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;
    private boolean running;

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
        //mButtonPause = findViewById(R.id.button_pause);
        mButtonReset = findViewById(R.id.button_reset);

        // Append Text change listeners
        mEditTextViewHour.addTextChangedListener(fieldWatcher);
        mEditTextViewMinute.addTextChangedListener(fieldWatcher);
        mEditTextViewSecond.addTextChangedListener(fieldWatcher);


        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    // Hide keyborad after start button is pressed
                    closeKeyboard();
                    mButtonReset.setText("Stop");
                    // Get the total in seconds
                    int totalMilliseconds = getTotalMilliseconds();
                    startTimer(totalMilliseconds);
                    running = true;
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    mCountDownTimer.cancel();
                    mEditTextViewHour.setText("");
                    mEditTextViewMinute.setText("");
                    mEditTextViewSecond.setText("");
                    mButtonReset.setText("Reset");
                    running = false;
                } else {
                    mEditTextViewHour.setText("");
                    mEditTextViewMinute.setText("");
                    mEditTextViewSecond.setText("");
                    mTextViewCountDown.setText("00:00:00");//set text

                    closeKeyboard();
                    //Toast.makeText(MainActivity.this, "Countdown is not running, Please enter a value in the fields above.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // do the maths ro return milliseconds
    private int getTotalMilliseconds() {
        // Get the value from the input
        String hours = mEditTextViewHour.getText().toString();
        String minutes = mEditTextViewMinute.getText().toString();
        String seconds = mEditTextViewSecond.getText().toString();

        // Initialise a int for hours, minute and second
        // If any input is empty, it will assign a value of zero to it
        int h = 0; int m = 0; int s = 0;

        if (!hours.isEmpty()) {
            h = Integer.parseInt(hours) * 3600000;
        }

        if (!minutes.isEmpty()) {
            m = Integer.parseInt(minutes) * 60000;
        }

        if (!seconds.isEmpty()) {
            s = Integer.parseInt(seconds) * 1000;
        }

        // return a sum of seconds
        int total;
        total = h + m + s;
        return total;
    }


    private void startTimer(int TotalOfMinutes) {
        mCountDownTimer = new CountDownTimer(TotalOfMinutes, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour, minute and seconds
                String hms = String.format(Locale.getDefault(),"%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                mTextViewCountDown.setText(hms);//set text
            }
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Countdown is complete", Toast.LENGTH_LONG).show();
                running = false;
            }
        }.start();

    }

    // Method to hide keyboard after the button start is pressed.
    // Got this method from https://codinginflow.com/tutorials/android/countdowntimer/part-4-time-input
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
