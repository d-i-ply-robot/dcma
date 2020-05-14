package com.hackingismakingisengineering.dcma.model;

import net.sf.mpxj.ProjectFile;

public class Program {

    private ProjectFile project;
    private String user;
    private String title;

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
    }

    public Program(ProjectFile project, String user, String title) {
        this.project = project;
        this.user = user;
        this.title = title;
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
}
