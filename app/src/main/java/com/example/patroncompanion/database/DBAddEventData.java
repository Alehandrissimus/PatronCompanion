package com.example.patroncompanion.database;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAddEventData extends AsyncTask<Bundle, Void, Void> {

    @Override
    protected Void doInBackground(Bundle... bundles) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url      = "jdbc:mysql://192.168.0.103:3306/sys";
        String user     = "aye";
        String password = "12345";

        Bundle b = bundles[0];
        String mText = b.getString("STR");
        String mDate = b.getString("DATE");

        String sql = "INSERT INTO `sys`.`eventsdata` (`userName`, `eventText`, `eventDate`) VALUES ('test', '" + mText + "', '" + mDate + "');";
        Log.d("TAG", "SQL Insert called" + mText);
        Log.d("TAG", "SQL Insert called" + mDate);

        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            try(Statement statement = connection.createStatement()) {
                Log.d("TAG", "SQL Insert called");
                statement.executeUpdate(sql);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
