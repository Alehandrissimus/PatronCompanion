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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EventAddActivity extends AppCompatActivity {
    private EditText mEditText, mTextDate, mTextTime;
    private Button mButtonConfirm, mButtonDate, mButtonTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String date, time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_new);

        mTextDate = findViewById(R.id.event_select_date);
        mTextTime = findViewById(R.id.event_select_time);
        mEditText = findViewById(R.id.event_insert_text);
        mButtonConfirm = findViewById(R.id.event_insert_date);
        mButtonDate = findViewById(R.id.event_select_date_btn);
        mButtonTime = findViewById(R.id.event_select_time_btn);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mDatabaseReference = database.getReferenceFromUrl("https://test-e3678.firebaseio.com/eventsData/" + auth.getUid());

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
                        date = (year + "-" + (month + 1) + "-" + dayOfMonth);
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
                        time = (hourOfDay + ":" + minute + ":00");
                        mTextTime.setText(time);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        EventsElement a = new EventsElement();
        a.setEventDate("chek");
        Log.d("tag", a.getEventDate());

        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventAddActivity.this, DBAddEventData.class);
                Bundle a = new Bundle();

                String mId = UUID.randomUUID().toString();
                String titleData = auth.getUid();
                String textData = mEditText.getText().toString();
                String dateData = (date + " " + time);

                Map<String, EventsElement> obj = new HashMap<>();
                obj.put("nickname", new EventsElement(mId, titleData, dateData, textData));

                DatabaseReference databaseRef = mDatabaseReference.child(mId);
                databaseRef.child("eventTitle").setValue(titleData);
                databaseRef.child("eventText").setValue(textData);
                databaseRef.child("eventDate").setValue(dateData);

                Log.d("TAS", (mId + " " + titleData + " " + textData + " " + dateData));
                finish();
            }
        });
    }
}
