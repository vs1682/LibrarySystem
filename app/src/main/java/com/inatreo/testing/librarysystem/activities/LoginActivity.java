package com.inatreo.testing.librarysystem.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.database.CRUDAdmin;
import com.inatreo.testing.librarysystem.utils.PreferenceManager;

public class LoginActivity extends AppCompatActivity {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkIfAlreadyLoggedIn();

        final EditText etUserName, etPassword;
        TextView tvCreateAccount = (TextView) findViewById(R.id.tvCreateAccount);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        etUserName = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                boolean isVerified = CRUDAdmin.getInstance(getApplicationContext()).verifyAdmin(username, password);
                if (isVerified){
                    CRUDAdmin.getInstance(getApplicationContext()).updateLoggingDetails(username, 1);
                    PreferenceManager.getInstance(getApplicationContext()).putString(USERNAME, username);
                    PreferenceManager.getInstance(getApplicationContext()).putString(PASSWORD, password);
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }
            }
        });
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateMasterOrAdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkIfAlreadyLoggedIn() {
        if (PreferenceManager.getInstance(getApplicationContext()).contains(USERNAME)){
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
