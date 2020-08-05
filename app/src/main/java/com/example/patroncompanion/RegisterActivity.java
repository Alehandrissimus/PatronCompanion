package com.example.patroncompanion;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.patroncompanion.database.DBAddEventData;
import com.example.patroncompanion.database.DBConnectionAlertDialogFragment;
import com.example.patroncompanion.database.DBUserRegistration;
import com.example.patroncompanion.ui.events.EventAddActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RegisterActivity extends AppCompatActivity {
    Button mButtonReg;
    EditText mName, mPass, mMail;

    FirebaseAuth mAuth;

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
                /*
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

                 */

                String name = mName.getText().toString();
                String pass = mPass.getText().toString();
                String mail = mMail.getText().toString();

                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Registration success.", Toast.LENGTH_SHORT).show();

                            /*
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("UID", user);
                            setResult(Activity.RESULT_OK, resultIntent);

                             */
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            DialogFragment dialog = new DBConnectionAlertDialogFragment();
                            dialog.show(getSupportFragmentManager(), "No connection");
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
