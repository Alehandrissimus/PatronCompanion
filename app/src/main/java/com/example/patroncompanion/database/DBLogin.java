package com.example.secondappsprav.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBLogin extends AsyncTask<String, Void, Boolean> {

    public DBLogin(Context context) {
    }

    @Override
    protected Boolean doInBackground(String... parameter) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url      = "jdbc:mysql://192.168.0.103:3306/sys";
        String user     = "aye";
        String password = "12345";

        String[] par = parameter;

        String par_username = par[0];
        String par_password = par[1];

        String sql_username = "select username from sys.userinfo;";
        String got_username = null;
        String got_password = null;
        boolean trg = false;

        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            try(Statement statement = connection.createStatement()) {
                String sql = "select * from userinfo";
                Log.d("TAG", "SQL string called");
                try(ResultSet result = statement.executeQuery(sql)) {
                    while (result.next()) {
                        Log.d("TAG", "Data from db query called");
                        got_username = result.getString("username");
                        got_password = result.getString("password");
                        if(got_username.equals(par_username) && got_password.equals(par_password)) {
                            trg = true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trg;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }
}
