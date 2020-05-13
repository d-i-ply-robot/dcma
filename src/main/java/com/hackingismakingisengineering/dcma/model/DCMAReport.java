package com.hackingismakingisengineering.dcma.model;

import java.util.Date;

public class DCMAReport {

    private String title;
    private Date creationDate;
    private Date updateDate;
    private Date exportDate;
    private String user;
    private String programFormat;

    private Program mProgram;

    

    public DCMAReport(Program mProgram) {
        this.mProgram = mProgram;
    }

    @Override
    public String toString() {
        return "DCMAReport{" +
                "title='" + title + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", exportDate=" + exportDate +
                ", user='" + user + '\'' +
                ", mProgram=" + mProgram +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Program getmProgram() {
        return mProgram;
    }

    public void setmProgram(Program mProgram) {
        this.mProgram = mProgram;
    }
}
