package com.example.patroncompanion.ui.events;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patroncompanion.R;


public class EventActivity extends AppCompatActivity {
    private TextView mTitle, mDate, mText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mTitle = (TextView) findViewById(R.id.event_activity_title);
        mText = (TextView) findViewById(R.id.event_activity_text);
        mDate = (TextView) findViewById(R.id.event_activity_date);
        mTitle.setText(getIntent().getStringExtra("title"));
        mText.setText(getIntent().getStringExtra("text"));
        mDate.setText(getIntent().getStringExtra("date"));
    }
}
