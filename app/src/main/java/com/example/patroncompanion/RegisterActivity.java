package com.example.patroncompanion;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.patroncompanion.database.DBAddEventData;
import com.example.patroncompanion.database.DBConnectionAlertDialogFragment;
import com.example.patroncompanion.database.DBUserRegistration;
import com.example.patroncompanion.ui.events.EventAddActivity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RegisterActivity extends AppCompatActivity {
    Button mButtonReg;
    EditText mName, mPass, mMail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mButtonReg = (Button) findViewById(R.id.registration_confirm_button);
        mName = (EditText) findViewById(R.id.registration_username_text);
        mPass = (EditText) findViewById(R.id.registration_username_pass);
        mMail = (EditText) findViewById(R.id.registration_username_mail);

        mButtonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, DBUserRegistration.class);
                Bundle a = new Bundle();

                String name = mName.getText().toString();
                String pass = mPass.getText().toString();
                String mail = mMail.getText().toString();
                a.putString("NAME", name);
                a.putString("PASS", pass);
                a.putString("MAIL", mail);

                intent.putExtras(a);

                DBUserRegistration db = new DBUserRegistration();
                db.execute(a);

                try {
                    if(!db.get(500, TimeUnit.MILLISECONDS)) {
                        DialogFragment dialog = new DBConnectionAlertDialogFragment();
                        dialog.show(getSupportFragmentManager(), "No connection");
                    }
                } catch (ExecutionException | InterruptedException | TimeoutException e) {
                    e.printStackTrace();
                    DialogFragment dialog = new DBConnectionAlertDialogFragment();
                    dialog.show(getSupportFragmentManager(), "No connection");
                }
            }
        });
    }
}
