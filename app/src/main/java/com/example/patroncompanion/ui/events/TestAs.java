package com.example.patroncompanion.ui.events;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class TestAs extends AsyncTask<Bundle, Void, Void> {

    @Override
    protected Void doInBackground(Bundle... bundles) {
        /*
        Bundle bundle = bundles[0];
        int position = bundle.getInt("position");
        Date eventDate = data.get(position-1).getDate();
        Date curDate = Calendar.getInstance().getTime();
        long milliseconds = eventDate.getTime() - curDate.getTime();
        Log.d("TAS", "eventDate = " + eventDate + " curDate = " + curDate);
        Log.d("TAS", "eventDate = " + eventDate.getTime() + " curDate = " + curDate.getTime());
        if(curDate.before(eventDate)) {
            Log.d("TAS", String.valueOf((milliseconds)));
            CountDownTimer timer = new CountDownTimer(milliseconds, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("TAS", "millis = " + millisUntilFinished);
                    long seconds = ((millisUntilFinished / 1000) % 60) ;
                    long minutes = ((millisUntilFinished / (1000*60)) % 60);
                    long hours   = ((millisUntilFinished / (1000*60*60)));
                    a.mDataText.setText(String.format("%02d:%02d:%02d",hours,minutes,seconds));
                }
                public void onFinish() {
                    a.mDataText.setText("Time Up");
                }
            };
            timer.start();
        }

         */
        return null;
    }
}
