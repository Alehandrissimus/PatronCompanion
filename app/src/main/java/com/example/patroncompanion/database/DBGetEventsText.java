package com.example.patroncompanion.database;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.patroncompanion.BuildConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBGetEventsText extends AsyncTask<Bundle, Void, String[]> {
    public DBGetEventsText() {
    }

    @Override
    protected String[] doInBackground(Bundle... bundles) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url      = "jdbc:mysql://192.168.0.103:3306/sys";
        String user     = "aye";
        String password = "12345";

        Bundle b = bundles[0];
        String buf = b.getString("username");
        int rowsCount = b.getInt("rowsCount", 100);
        Log.d("TAS", "rows = " + rowsCount);
        Log.d("TAG", "name = " + buf +" rows = " + rowsCount);

        String[] eventTexts = new String[rowsCount];

        String sql = "SELECT * FROM sys.eventsdata WHERE username = '" + buf + "'\n" + "ORDER BY eventDate;";

        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            try(Statement statement = connection.createStatement()) {
                try(ResultSet result = statement.executeQuery(sql)) {
                    int i = 0;
                    while (result.next()) {
                        eventTexts[i] = result.getString("eventText");
                        i++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventTexts;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
    }
}
