package com.inatreo.testing.librarysystem.models;

/**
 * Created by vishal on 2/5/2016.
 */
public class IssueRecord {
    int issueID;
    String bookID;
    String memberID;

    public int getIssueID() {
        return issueID;
    }

    public void setIssueID(int issueID) {
        this.issueID = issueID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }
}
