package com.example.patroncompanion.ui.events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.patroncompanion.R;
import com.example.patroncompanion.database.DBAddEventData;

import java.util.Calendar;
import java.util.Date;

public class EventAddActivity extends AppCompatActivity {
    private EditText mEditText, mTextDate, mTextTime;
    private Button mButtonConfirm, mButtonDate, mButtonTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String date, time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_new);

        mTextDate = (EditText) findViewById(R.id.event_select_date);
        mTextTime = (EditText) findViewById(R.id.event_select_time);
        mEditText = (EditText) findViewById(R.id.event_insert_text);
        mButtonConfirm = (Button) findViewById(R.id.event_insert_date);
        mButtonDate = (Button) findViewById(R.id.event_select_date_btn);
        mButtonTime = (Button) findViewById(R.id.event_select_time_btn);

        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EventAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = (year + "-" + (month + 1) + "-" + dayOfMonth);
                        mTextDate.setText(date);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        mButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EventAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = (hourOfDay + ":" + minute);
                        mTextTime.setText(time);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventAddActivity.this, DBAddEventData.class);
                Bundle a = new Bundle();

                String text = mEditText.getText().toString();
                a.putString("STR", text);
                a.putString("DATE", (time + " " + date));
                intent.putExtras(a);

                DBAddEventData db = new DBAddEventData();
                db.execute(a);
                finish();
            }
        });
    }
}
