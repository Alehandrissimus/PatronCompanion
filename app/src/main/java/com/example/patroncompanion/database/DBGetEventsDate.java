package com.example.patroncompanion.database;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.BundleCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBGetEventsDate extends AsyncTask<String, Void, Bundle> {

    @Override
    protected Bundle doInBackground(String... strings) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url      = "jdbc:mysql://192.168.0.103:3306/sys";
        String user     = "aye";
        String password = "12345";

        String buf = strings[0];
        int count = 0;

        String sql_data = "SELECT * FROM sys.eventsdata WHERE username = '" + buf + "'\n" + "ORDER BY eventDate;";
        String sql_rows = "SELECT COUNT(*) FROM sys.eventsdata WHERE username = '" + buf + "';";

        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            try(Statement statement = connection.createStatement()) {
                try(ResultSet result = statement.executeQuery(sql_rows)) {
                    while (result.next()) {
                        count = result.getInt("COUNT(*)");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] eventDates = new String[count];

        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            try(Statement statement = connection.createStatement()) {
                try(ResultSet result = statement.executeQuery(sql_data)) {
                    int i = 0;
                    while (result.next()) {
                        eventDates[i] = result.getString("eventDate");
                        i++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Bundle a = new Bundle();
        a.putStringArray("KEY_STARR", eventDates);
        a.putInt("KEY_INT", count);

        return a;
    }

    @Override
    protected void onPostExecute(Bundle bundle) {
        super.onPostExecute(bundle);
    }
}
