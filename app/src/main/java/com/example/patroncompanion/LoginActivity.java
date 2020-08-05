package com.example.patroncompanion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    Button mButtonLogin, mButtonFast, mButtonRegister;
    EditText mUsermail, mPassword;
    TextView mErrorText;
    FragmentManager mFragmentManager;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mButtonLogin = (Button) findViewById(R.id.login_button);
        mUsermail = (EditText) findViewById(R.id.login_username);
        mPassword = (EditText) findViewById(R.id.login_password);
        mErrorText = (TextView) findViewById(R.id.login_error_text);

        mButtonRegister = (Button) findViewById(R.id.login_button_registration);
        mButtonFast = (Button) findViewById(R.id.login_test_skip);
        mFragmentManager = getSupportFragmentManager();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String umail = mUsermail.getText().toString();
                String upass = mPassword.getText().toString();

                /*
                DBLogin test = new DBLogin(LoginActivity.this);
                test.execute(umail, upass);
                try {
                    Boolean trg = test.get(10000, TimeUnit.MILLISECONDS);
                    if(trg) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("USERNAME_KEY", umail);
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

                 */

                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(umail, upass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            /*
                            Intent intent = new Intent();
                            intent.putExtra("UID", user);

                             */
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1001);

            }
        });

        mButtonFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("USERNAME_KEY", "test");
                startActivity(intent);
                finish();

                 */

                FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");

                //FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), mUsername, null);
                mMessagesDatabaseReference.setValue("test1");
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