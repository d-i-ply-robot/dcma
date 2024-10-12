package com.hackingismakingisengineering.dcma.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.hackingismakingisengineering.dcma.model.dcma.Report;

import net.sf.mpxj.ProjectFile;

@Entity //JPA annotation on POJOS for ORM
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //sequentially assigned IDs starting w. 1
    private Long id;


    @Transient // This excludes the project from the DB.
    private ProjectFile project;


    @Transient //TODO: undo - excludes from the db
    private Report report;


    private String user;
    private String title;
    private String string;
    private int categoryId;
    private Date statusDate;



    @Lob //Large object
    private byte[] bytes;

    @ManyToOne
    private Category category;

    @Override
    public String toString() {
        return "Program{" +
                "project=" + project +
                ", user='" + user + '\'' +
                ", title='" + title + '\'' +
                '}';
    }


    public Program(ProjectFile project) {
        this.project = project;
        this.string = this.toString();
        report = new Report(project);
        }

    public Program(ProjectFile project, String user, String title, int categoryId) {
        this.project = project;
        this.user = user;
        this.title = title;
        this.string = toString();
        this.categoryId = categoryId;

        if(project!=null) {
            report = new Report(project);
        }
        //this.category = new Category(categoryId);
    }

    public ProjectFile getProject() {
        return project;
    }

    public void setProject(ProjectFile project) {
        this.project = project;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getStatusDate() {
        return statusDate;
    }
    
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }
}
