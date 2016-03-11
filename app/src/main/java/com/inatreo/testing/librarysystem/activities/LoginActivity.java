package com.inatreo.testing.librarysystem.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.database.CRUDAdmin;
import com.inatreo.testing.librarysystem.utils.PreferenceManager;

public class LoginActivity extends AppCompatActivity {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String IS_IT_MASTER = "is_it_master";
    private EditText etUserName, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //if already logged in then skip login page and land directly into home page.
        checkIfAlreadyLoggedIn();

        TextView tvCreateAccount = (TextView) findViewById(R.id.tvCreateAccount);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        etUserName = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);

        //when one master is created, hide the link to create another at login at login page
        if (CRUDAdmin.getInstance(getApplicationContext()).isMasterPresent()){
            tvCreateAccount.setVisibility(View.INVISIBLE);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                int isVerified = CRUDAdmin.getInstance(getApplicationContext()).verifyAdmin(username, password);

                if (isVerified == 1 || isVerified == 2){
                    if (isVerified == 1){
                        PreferenceManager.getInstance(getApplicationContext()).putString(IS_IT_MASTER, "master");
                    }
                    updateLoggingDetails(username);
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateMasterOrAdminActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //put admin details in shared prefs to check if already logged in.
    private void updateLoggingDetails(String username) {
        CRUDAdmin.getInstance(getApplicationContext()).updateLoggingDetails(username, 1);
        PreferenceManager.getInstance(getApplicationContext()).putString(USERNAME, username);
        /*PreferenceManager.getInstance(getApplicationContext()).putString(PASSWORD, password);*/
    }

    private void checkIfAlreadyLoggedIn() {
        if (PreferenceManager.getInstance(getApplicationContext()).contains(USERNAME)){
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
