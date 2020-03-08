package com.example.mytimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextViewHour;
    private EditText editTextViewMinute;
    private EditText editTextViewSecond;

    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextViewHour = findViewById(R.id.edit_text_hour);
        editTextViewMinute = findViewById(R.id.edit_text_minute);
        editTextViewSecond = findViewById(R.id.edit_text_seconds);

        buttonStart = findViewById(R.id.button_start);

        editTextViewHour.addTextChangedListener(fieldWatcher);
        editTextViewMinute.addTextChangedListener(fieldWatcher);
        editTextViewSecond.addTextChangedListener(fieldWatcher);

    }

    private TextWatcher fieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String hourInput = (String) editTextViewHour.getText().toString().trim();
            String minuteInput = (String) editTextViewMinute.getText().toString().trim();
            String secondInput = (String) editTextViewSecond.getText().toString().trim();

            buttonStart.setEnabled(!hourInput.isEmpty()
                    || !minuteInput.isEmpty()
                    || !secondInput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}
