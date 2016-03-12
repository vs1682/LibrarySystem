package com.inatreo.testing.librarysystem.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.database.CRUDBook;
import com.inatreo.testing.librarysystem.models.Book;

/**
 * Created by vishal on 1/28/2016.
 */
public class AddBookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        final EditText etBookName, etBookCategory, etBookAuthor, etBookPrice, etBookQuantity, etBookDescription;
        etBookName = (EditText)findViewById(R.id.etBookName);
        etBookCategory = (EditText)findViewById(R.id.etBookCategory);
        etBookAuthor = (EditText)findViewById(R.id.etBookAuthor);
        etBookPrice = (EditText)findViewById(R.id.etBookPrice);
        etBookQuantity = (EditText)findViewById(R.id.etBookQuantity);
        etBookDescription = (EditText)findViewById(R.id.etBookDescription);

        final Book book = new Book();

        Button btnAddBook = (Button)findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setName(etBookName.getText().toString());
                book.setCategory(etBookCategory.getText().toString());
                book.setAuthor(etBookAuthor.getText().toString());
                book.setPrice(Double.parseDouble(etBookPrice.getText().toString()));
                book.setQuantity(Integer.parseInt(etBookQuantity.getText().toString()));
                book.setDescription(etBookDescription.getText().toString());
                CRUDBook.getInstance(getApplicationContext()).insert(book);
            }
        });
    }
}
