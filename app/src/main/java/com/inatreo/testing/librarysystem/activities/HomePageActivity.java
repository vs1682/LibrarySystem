package com.inatreo.testing.librarysystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.activities.fragments.ImportBackupDialog;
import com.inatreo.testing.librarysystem.database.CRUDBook;
import com.inatreo.testing.librarysystem.database.CRUDMember;
import com.inatreo.testing.librarysystem.models.Book;

/**
 * Created by vishal on 1/26/2016.
 */
public class HomePageActivity extends NavDrawerActivity {
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
                CRUDMember.getInstance(getApplicationContext()).getMemberDetails(Integer.parseInt(etSearch.getText().toString()));
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CRUDBook.getInstance(getApplicationContext()).isBookPresent(etSearch.getText().toString())){
                    Intent intent = new Intent(HomePageActivity.this, BookDetailsActivity.class);
                    intent.putExtra("BOOK_ID", etSearch.getText().toString());
                    startActivity(intent);
                }else Toast.makeText(getBaseContext(), "no book with this ID", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
