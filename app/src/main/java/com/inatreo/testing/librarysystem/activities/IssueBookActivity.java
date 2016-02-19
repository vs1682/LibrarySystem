package com.inatreo.testing.librarysystem.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.database.CRUDIssueRecord;
import com.inatreo.testing.librarysystem.models.IssueRecord;

/**
 * Created by vishal on 2/5/2016.
 */
public class IssueBookActivity extends NavDrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);
        final IssueRecord issueRecord = new IssueRecord();
        final EditText etIssueBook, etIssueToMember;
        Button btnIssueBook, btnReadIssueRecord;
        btnIssueBook = (Button)findViewById(R.id.btnIssueBook);
        btnReadIssueRecord = (Button)findViewById(R.id.btnReadIssueRecord);

        etIssueBook = (EditText)findViewById(R.id.etIssueBookID);
        etIssueToMember = (EditText)findViewById(R.id.etIssueToMemberID);
        if (savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            if (bundle == null)
                Toast.makeText(this, "some error. Try again.", Toast.LENGTH_SHORT).show();
            else {
                etIssueBook.setText(bundle.getString("BOOK_ID"));
            }
        }else etIssueBook.setText(savedInstanceState.getSerializable("BOOK_ID").toString());

        btnIssueBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issueRecord.setBookID(etIssueBook.getText().toString());
                issueRecord.setMemberID(etIssueToMember.getText().toString());
                CRUDIssueRecord.getInstance(getApplicationContext()).insertIssueRecord(issueRecord);
            }
        });

        btnReadIssueRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDIssueRecord.getInstance(getApplicationContext()).readAllRecords();
            }
        });
    }
}
