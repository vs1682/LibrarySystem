package com.inatreo.testing.librarysystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.models.Member;

import java.util.ArrayList;

/**
 * Created by vishal on 2/13/2016.
 */
public class MemberListAdapter extends ArrayAdapter<Member> {

    public MemberListAdapter(Context context, ArrayList<Member> objects) {
        super(context, R.layout.row_member_list, objects);
    }

    private static class ViewHolder{
        TextView tvRowMemberFullName, tvRowMemberFatherName, tvRowMemberID, tvRowMemberAge;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Member member = getItem(position);
        ViewHolder viewHolder;

        if (convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_member_list, parent, false);
            viewHolder.tvRowMemberID = (TextView) convertView.findViewById(R.id.tvRowMemberID);
            viewHolder.tvRowMemberFatherName = (TextView) convertView.findViewById(R.id.tvRowMemberFatherName);
            viewHolder.tvRowMemberFullName = (TextView) convertView.findViewById(R.id.tvRowMemberFullName);
            viewHolder.tvRowMemberAge = (TextView) convertView.findViewById(R.id.tvRowMemberAge);
            convertView.setTag(viewHolder);
        }else viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tvRowMemberID.setText(member.getMemberID());
        viewHolder.tvRowMemberFullName.setText(member.getFullName());
        viewHolder.tvRowMemberFatherName.setText(member.getFatherName());
        viewHolder.tvRowMemberAge.setText(String.valueOf(member.getAge()));

        return convertView;
    }
}
