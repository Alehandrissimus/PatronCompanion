package com.example.patroncompanion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.patroncompanion.database.DBConnectionAlertDialogFragment;
import com.example.secondappsprav.database.DBLogin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoginActivity extends AppCompatActivity {
    Button mButtonLogin, mButtonFast, mButtonRegister;
    EditText mUsername, mPassword;
    TextView mErrorText;
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mButtonLogin = (Button) findViewById(R.id.login_button);
        mUsername = (EditText) findViewById(R.id.login_username);
        mPassword = (EditText) findViewById(R.id.login_password);
        mErrorText = (TextView) findViewById(R.id.login_error_text);

        mButtonRegister = (Button) findViewById(R.id.login_button_registration);
        mButtonFast = (Button) findViewById(R.id.login_test_skip);
        mFragmentManager = getSupportFragmentManager();

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = mUsername.getText().toString();
                String upass = mPassword.getText().toString();
                DBLogin test = new DBLogin(LoginActivity.this);
                test.execute(uname, upass);
                try {
                    Boolean trg = test.get(10000, TimeUnit.MILLISECONDS);
                    if(trg) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("USERNAME_KEY", uname);
                        startActivity(intent);
                        finish();
                    } else {
                        mErrorText.setVisibility(View.VISIBLE);
                    }
                } catch (ExecutionException | InterruptedException | TimeoutException e) {
                    e.printStackTrace();
                    test.cancel(true);
                    DialogFragment dialog = new DBConnectionAlertDialogFragment();
                    dialog.show(getSupportFragmentManager(), "No connection");
                }
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mButtonFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("USERNAME_KEY", "test");
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "Enter onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "Enter onDestroy called");
    }
}