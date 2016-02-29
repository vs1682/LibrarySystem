package com.inatreo.testing.librarysystem.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.database.CRUDMember;
import com.inatreo.testing.librarysystem.models.Member;

/**
 * Created by vishal on 1/28/2016.
 */
public class AddMemberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        Button btnAddMember, btnReadMembers;
        final EditText etMemberName, etMemberFatherName, etMemberAge, etMemberMobile, etMemberAddress;
        final Spinner spinnerMemberLevel;

        btnAddMember = (Button)findViewById(R.id.btnAddMember);

        etMemberName = (EditText)findViewById(R.id.etMemberName);
        etMemberFatherName = (EditText)findViewById(R.id.etMemberFatherName);
        etMemberAge = (EditText)findViewById(R.id.etMemberAge);
        etMemberMobile = (EditText)findViewById(R.id.etMemberMobile);
        etMemberAddress = (EditText)findViewById(R.id.etMemberAddress);

        spinnerMemberLevel = (Spinner)findViewById(R.id.spinnerMemberLevel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.memberLevels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMemberLevel.setAdapter(adapter);

        final Member member = new Member();

        btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setFullName(etMemberName.getText().toString());
                member.setFatherName(etMemberFatherName.getText().toString());
                member.setAge(Integer.parseInt(etMemberAge.getText().toString()));
                member.setLevel(spinnerMemberLevel.getSelectedItem().toString());
                member.setMobileNo(etMemberMobile.getText().toString());
                member.setAddress(etMemberAddress.getText().toString());
                CRUDMember.getInstance(getApplicationContext()).insertMember(member);
            }
        });

    }
}
