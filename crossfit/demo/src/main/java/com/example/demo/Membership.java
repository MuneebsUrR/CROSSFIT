package com.example.demo;

import java.sql.Date;

public class Membership {
    private int memberID;

    private String name;
    private String isActive;
    private Date startDate;
    private Date endDate;

    public Membership(int memberID,String name, String isActive, Date startDate, Date endDate) {
        this.memberID = memberID;
        this.name = name;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getIsActive() {
        return isActive;
    }

    public String getName() {
        return name;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public void setName(String name) {
        this.isActive = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
