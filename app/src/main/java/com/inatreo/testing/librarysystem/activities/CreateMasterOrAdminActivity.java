package com.inatreo.testing.librarysystem.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.database.CRUDAdmin;
import com.inatreo.testing.librarysystem.database.DBManager;
import com.inatreo.testing.librarysystem.models.Admin;
import com.inatreo.testing.librarysystem.services.ScheduledBackup;
import com.inatreo.testing.librarysystem.utils.ExportImportDB;
import com.inatreo.testing.librarysystem.utils.PreferenceManager;
import com.mikepenz.materialdrawer.holder.StringHolder;

import java.util.Calendar;

/**
 * Created by vishal on 1/26/2016.
 */
public class CreateMasterOrAdminActivity extends NavDrawerActivity {

    private EditText mEtFirstName, mEtLastName, mEtMobile, mEtAdminUsername, mEtAdminPassword;
    private Spinner mSpinner;
    private TextView mTvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_master_or_admin);

        mEtFirstName = (EditText)findViewById(R.id.etFirstName);
        mEtLastName = (EditText)findViewById(R.id.etLastName);
        mEtMobile = (EditText)findViewById(R.id.etMobile);
        mEtAdminUsername = (EditText)findViewById(R.id.etAdminUsername);
        mEtAdminPassword = (EditText)findViewById(R.id.etAdminPassword);

        mTvLogin = (TextView)findViewById(R.id.tvLogin);

        mSpinner = (Spinner)findViewById(R.id.spinnerMasterOrAdmin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.masterOrAdmin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        Button btnCreateAdmin = (Button) findViewById(R.id.btnCreateAdmin);
        Button btnReadAdmins = (Button) findViewById(R.id.btnReadAdmins);

        final Admin admin = new Admin();
        btnCreateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.setFirstName(mEtFirstName.getText().toString());
                admin.setLastName(mEtLastName.getText().toString());
                admin.setMobile(mEtMobile.getText().toString());
                admin.setUsername(mEtAdminUsername.getText().toString());
                admin.setPassword(mEtAdminPassword.getText().toString());
                admin.setMasterOrAdmin(mSpinner.getSelectedItem().toString());
                CRUDAdmin.getInstance(getApplicationContext()).insertAdmin(admin);
                Intent intent = new Intent(CreateMasterOrAdminActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnReadAdmins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDAdmin.getInstance(getApplicationContext()).getAllAdminDetails();
            }
        });

        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateMasterOrAdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}
