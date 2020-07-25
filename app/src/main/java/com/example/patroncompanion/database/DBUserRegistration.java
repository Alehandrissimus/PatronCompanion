package com.example.patroncompanion.database;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUserRegistration extends AsyncTask<Bundle, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Bundle... bundles) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url      = "jdbc:mysql://192.168.0.10:3306/sys";
        String user     = "aye";
        String password = "12345";

        Bundle b = bundles[0];
        String mName = b.getString("NAME");
        String mPass = b.getString("PASS");
        String mMail = b.getString("MAIL");

        String sql = "INSERT INTO `sys`.`userinfo` (`username`, `password`, `usermail`, `rank`) VALUES ('" + mName + "', '" + mPass + "', '" + mMail + "', 0);";
        Log.d("TAG", "SQL Insert called" + mName + " " + mPass + " " + mMail);

        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            if (!connection.isValid(1)) {
                return false;
            }
            try(Statement statement = connection.createStatement()) {
                Log.d("TAG", "SQL Insert called");
                statement.executeUpdate(sql);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}