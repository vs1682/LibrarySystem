package com.inatreo.testing.librarysystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.adapters.MemberListAdapter;
import com.inatreo.testing.librarysystem.database.CRUDMember;
import com.inatreo.testing.librarysystem.models.Member;

import java.util.ArrayList;

/**
 * Created by vishal on 2/13/2016.
 */
public class MemberListActivity extends AppCompatActivity {
    private ListView mListView;
    private MemberListAdapter mMemberListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mListView = (ListView) findViewById(R.id.listBooksOrMembers);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member member = (Member) parent.getItemAtPosition(position);
                Intent intent = new Intent(MemberListActivity.this, MemberDetailsActivity.class);
                intent.putExtra("MEMBER_ID", member.getMobileNo());
                Log.v("-MLA-", member.getFullName());
                startActivity(intent);
            }
        });

        if (mMemberListAdapter == null || mListView.getAdapter() == null){
            ArrayList<Member> members = CRUDMember.getInstance(getApplicationContext()).getAllMember();
            mMemberListAdapter = new MemberListAdapter(getBaseContext(), members);
            mListView.setAdapter(mMemberListAdapter);
        }else mMemberListAdapter.notifyDataSetChanged();
    }
}
