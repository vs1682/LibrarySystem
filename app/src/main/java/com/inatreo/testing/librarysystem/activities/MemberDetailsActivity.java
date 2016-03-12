package com.inatreo.testing.librarysystem.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.inatreo.testing.librarysystem.R;
import com.inatreo.testing.librarysystem.database.CRUDMember;
import com.inatreo.testing.librarysystem.models.Member;

/**
 * Created by vishal on 2/13/2016.
 */
public class MemberDetailsActivity extends NavDrawerActivity {
    private TextView mTvMemberID, mTvMemberFullName, mTvMemberFatherName, mTvMemberAge, mTvMemberLevel, mTvMemberMobile, mTvMemberAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);
        mTvMemberID = (TextView) findViewById(R.id.tvMemberID);
        mTvMemberFullName = (TextView) findViewById(R.id.tvMemberFullName);
        mTvMemberFatherName = (TextView) findViewById(R.id.tvMemberFatherName);
        mTvMemberAge = (TextView) findViewById(R.id.tvMemberAge);
        mTvMemberLevel = (TextView) findViewById(R.id.tvMemberLevel);
        mTvMemberMobile = (TextView) findViewById(R.id.tvMemberMobile);
        mTvMemberAddress = (TextView) findViewById(R.id.tvMemberAddress);
        String memberID = " ";
        if (savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            if (bundle == null){
                Toast.makeText(this, "some error, please try again", Toast.LENGTH_SHORT).show();
            }else {
                memberID = bundle.getString("MOBILE_NO");
                showMemberDetails(memberID);
            }
        }else {
            memberID = savedInstanceState.getSerializable("MOBILE_NO").toString();
            if (!memberID.isEmpty()){
                showMemberDetails(memberID);
            }
        }
    }

    private void showMemberDetails(String mobileNo) {
        Member member = CRUDMember.getInstance(getApplicationContext()).getMemberDetails(mobileNo);
        mTvMemberID.setText(member.getMemberID());
        mTvMemberFullName.setText(member.getFullName());
        mTvMemberFatherName.setText(member.getFatherName());
        mTvMemberAge.setText(String.valueOf(member.getAge()));
        mTvMemberLevel.setText(member.getLevel());
        mTvMemberMobile.setText(member.getMobileNo());
        mTvMemberAddress.setText(member.getAddress());
        //Log.v("-MDA-",member.getFullName());
    }
}
