package com.inatreo.testing.librarysystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.activities.fragments.ImportBackupDialog;
import com.inatreo.testing.librarysystem.activities.fragments.SelectBackupDialog;
import com.inatreo.testing.librarysystem.database.CRUDBook;
import com.inatreo.testing.librarysystem.database.CRUDMember;
import com.inatreo.testing.librarysystem.interfaces.SelectBackupInterface;
import com.inatreo.testing.librarysystem.models.Book;
import com.inatreo.testing.librarysystem.utils.ExportImportDB;

import java.io.IOException;

/**
 * Created by vishal on 1/26/2016.
 */
public class HomePageActivity extends NavDrawerActivity implements SelectBackupInterface{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final EditText etSearch = (EditText)findViewById(R.id.etSearch);
        Button btnUser = (Button) findViewById(R.id.btnUser);
        Button btnBook = (Button) findViewById(R.id.btnBook);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRUDMember.getInstance(getApplicationContext()).getMemberDetails((etSearch.getText().toString()));
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CRUDBook.getInstance(getApplicationContext()).isBookPresent(etSearch.getText().toString())) {
                    Log.v("-HPA-","it's working");
                    Intent intent = new Intent(getBaseContext(), BookDetailsActivity.class);
                    intent.putExtra("BOOK_ID", etSearch.getText().toString());
                    startActivity(intent);
                } else
                    Toast.makeText(getBaseContext(), "no book with this ID", Toast.LENGTH_SHORT).show();
            }
        });

        new ImportBackupDialog().show(getFragmentManager(), "importDialog");
    }

    @Override
    public void selectedFile(String file) {
        Log.v("-HPA-", String.valueOf(file));
        Toast.makeText(this, "should we import", Toast.LENGTH_SHORT).show();
        try{
            ExportImportDB.importDB(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
