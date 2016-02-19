package com.inatreo.testing.librarysystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.adapters.BookListAdapter;
import com.inatreo.testing.librarysystem.database.CRUDBook;
import com.inatreo.testing.librarysystem.models.Book;

import java.util.ArrayList;

/**
 * Created by vishal on 2/9/2016.
 */
public class BookListActivity extends AppCompatActivity {
    private BookListAdapter mBookListAdapter;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mListView = (ListView) findViewById(R.id.listBooksOrMembers);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = (Book) parent.getItemAtPosition(position);
                Log.v("-BLA-", book.getBookID());
                Intent intent = new Intent(BookListActivity.this, BookDetailsActivity.class);
                intent.putExtra("BOOK_ID", book.getBookID());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBookListAdapter == null || mListView.getAdapter() == null){
            ArrayList<Book> books = CRUDBook.getInstance(getApplicationContext()).getAllBook();
            mBookListAdapter = new BookListAdapter(getBaseContext(), books);
            mListView.setAdapter(mBookListAdapter);
        }else mBookListAdapter.notifyDataSetChanged();
    }
}
