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
import com.inatreo.testing.librarysystem.activities.fragments.CreatingMasterWarningDialog;
import com.inatreo.testing.librarysystem.database.CRUDAdmin;
import com.inatreo.testing.librarysystem.database.DBManager;
import com.inatreo.testing.librarysystem.interfaces.CreateNewMasterInterface;
import com.inatreo.testing.librarysystem.models.Admin;
import com.inatreo.testing.librarysystem.services.ScheduledBackup;
import com.inatreo.testing.librarysystem.utils.ExportImportDB;
import com.inatreo.testing.librarysystem.utils.PreferenceManager;
import com.mikepenz.materialdrawer.holder.StringHolder;

import java.util.Calendar;

/**
 * Created by vishal on 1/26/2016.
 */
public class CreateMasterOrAdminActivity extends NavDrawerActivity implements CreateNewMasterInterface{

    private EditText mEtFirstName, mEtLastName, mEtMobile, mEtAdminUsername, mEtAdminPassword;
    private Spinner mSpinner;
    private TextView mTvLogin;
    private Admin admin;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String IS_IT_MASTER = "is_it_master";
    private static final String IS_IT_JUST_INSTALLED = "is_it_just_installed";

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

        //when installed, only master can be created and after master logs in, both master or admin can be created.
        ArrayAdapter<CharSequence> adapter;
        if (PreferenceManager.getInstance(getApplicationContext()).contains(IS_IT_JUST_INSTALLED)){
            adapter = ArrayAdapter.createFromResource(this, R.array.masterOrAdmin, android.R.layout.simple_spinner_item);
        }else{
            PreferenceManager.getInstance(getApplicationContext()).putBoolean(IS_IT_JUST_INSTALLED, true);
            adapter = ArrayAdapter.createFromResource(this, R.array.master, android.R.layout.simple_spinner_item);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        Button btnCreateAdmin = (Button) findViewById(R.id.btnCreateAdmin);
        Button btnReadAdmins = (Button) findViewById(R.id.btnReadAdmins);

        admin = new Admin();
        btnCreateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.setFirstName(mEtFirstName.getText().toString());
                admin.setLastName(mEtLastName.getText().toString());
                admin.setMobile(mEtMobile.getText().toString());
                admin.setUsername(mEtAdminUsername.getText().toString());
                admin.setPassword(mEtAdminPassword.getText().toString());
                admin.setMasterOrAdmin(mSpinner.getSelectedItem().toString());


                //when master is creating an admin profile.
                if (isMasterLoggedIn() && admin.getMasterOrAdmin().equals("Admin")){
                    CRUDAdmin.getInstance(getApplicationContext()).insertAdmin(admin, 0);
                }
                //when master is creating another master. this deletes the current master account
                //as there can be only master at a time.
                if (isMasterLoggedIn() && admin.getMasterOrAdmin().equals("Master")){
                    CreatingMasterWarningDialog dialog = new CreatingMasterWarningDialog();
                    dialog.show(getFragmentManager(), "createNewMaster");
                }
                //when master is getting created when app is installed for the first time.
                else if (admin.getMasterOrAdmin().equals("Master")){
                    CRUDAdmin.getInstance(getApplicationContext()).insertAdmin(admin, 1);
                    PreferenceManager.getInstance(getApplicationContext()).putString(IS_IT_MASTER, "master");
                    updateLoggingDetails(admin.getUsername(), admin.getPassword());
                    Intent intent = new Intent(CreateMasterOrAdminActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                }
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

    //when first master is created, the master directly logs in the details ar stored in shared prefs.
    private void updateLoggingDetails(String username, String password) {
        PreferenceManager.getInstance(getApplicationContext()).putString(USERNAME, username);
        /*PreferenceManager.getInstance(getApplicationContext()).putString(PASSWORD, password);*/
    }

    //to check if master is logged in.
    private boolean isMasterLoggedIn(){
        return PreferenceManager.getInstance(getApplicationContext()).contains(IS_IT_MASTER);
    }

    //to create new master delete the old master.
    @Override
    public void createNewMaster() {

        CRUDAdmin.getInstance(getApplicationContext()).deleteAdmin(PreferenceManager.getInstance(getApplicationContext()).getString(USERNAME));
        PreferenceManager.getInstance(getApplicationContext()).remove(USERNAME);
        /*PreferenceManager.getInstance(getApplicationContext()).remove("password");*/
        PreferenceManager.getInstance(getApplicationContext()).remove("is_it_master");
        CRUDAdmin.getInstance(getApplicationContext()).insertAdmin(admin, 0);
        Intent loginIntent = new Intent(CreateMasterOrAdminActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
