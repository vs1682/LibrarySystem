package com.inatreo.testing.librarysystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.models.Book;

import java.util.ArrayList;

/**
 * Created by vishal on 2/11/2016.
 */
public class BookListAdapter extends ArrayAdapter<Book> {

    private static class ViewHolder{
        private TextView tvRowBookId, tvRowBookName, tvRowBookAuthur;
    }
    public BookListAdapter(Context context, ArrayList<Book> books) {
        super(context, R.layout.row_books_list, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_books_list, parent, false);
            viewHolder.tvRowBookId = (TextView) convertView.findViewById(R.id.tvRowBookID);
            viewHolder.tvRowBookName = (TextView) convertView.findViewById(R.id.tvRowBookName);
            viewHolder.tvRowBookAuthur = (TextView) convertView.findViewById(R.id.tvRowAuthorName);
            convertView.setTag(viewHolder);
        }else viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tvRowBookId.setText(book.getBookID());
        viewHolder.tvRowBookName.setText(book.getName());
        viewHolder.tvRowBookAuthur.setText(book.getAuthor());
        return convertView;
    }
}
