package com.inatreo.testing.librarysystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.database.CRUDBook;
import com.inatreo.testing.librarysystem.database.CRUDIssueRecord;
import com.inatreo.testing.librarysystem.models.Book;

import java.util.ArrayList;

/**
 * Created by vishal on 2/5/2016.
 */
public class BookDetailsActivity extends NavDrawerActivity {
    String bookID;
    TextView mtvBookId, mtvBookName, mtvBookAuthor, mtvBookCategory, mtvBookPrice, mtvBookQuantity, mtvBookAvailable, mtvBookIssuedTo, mtvBookDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        mtvBookId = (TextView) findViewById(R.id.tvBookId);
        mtvBookName = (TextView) findViewById(R.id.tvBookName);
        mtvBookAuthor = (TextView) findViewById(R.id.tvBookAuthor);
        mtvBookCategory = (TextView) findViewById(R.id.tvBookCatrgory);
        mtvBookPrice = (TextView) findViewById(R.id.tvBookPrice);
        mtvBookQuantity = (TextView) findViewById(R.id.tvBookQuantity);
        mtvBookAvailable = (TextView) findViewById(R.id.tvBookAvailable);
        mtvBookIssuedTo = (TextView) findViewById(R.id.tvBookIssuedTo);
        mtvBookDescription = (TextView) findViewById(R.id.tvBookDescription);
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                Toast.makeText(this, "some error", Toast.LENGTH_SHORT).show();
            } else {
                bookID = bundle.getString("BOOK_ID");
                bookDetails();
            }
        }else {
            bookID = (String) savedInstanceState.getSerializable("BOOK_ID");
            bookDetails();
        }

        mtvBookAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailsActivity.this, IssueBookActivity.class);
                intent.putExtra("BOOK_ID", bookID);
                startActivity(intent);
            }
        });

    }

    private void bookDetails() {
        Book book = CRUDBook.getInstance(getApplicationContext()).getBookDetails(bookID);
        ArrayList<String> memberIDs = CRUDIssueRecord.getInstance(getApplicationContext()).getIssueDetails(bookID);
        String allMemberIDs = "";
        for (int i=0; i<memberIDs.size(); i++){
            allMemberIDs+=memberIDs.get(i) + ", ";
        }
        int availableBooks = book.getQuantity() - memberIDs.size();
        mtvBookId.setText(book.getBookID());
        mtvBookName.setText(book.getName());
        mtvBookAuthor.setText(book.getAuthor());
        mtvBookCategory.setText(book.getCategory());
        mtvBookPrice.setText(String.valueOf(book.getPrice()));
        mtvBookQuantity.setText(String.valueOf(book.getQuantity()));
        mtvBookAvailable.setText(String.valueOf(availableBooks));
        mtvBookIssuedTo.setText(allMemberIDs);
        mtvBookDescription.setText(book.getDescription());
    }
}
