package com.kaleido.cesmarttracker.data;

/**
 * Created by Touch on 10/15/2015.
 */
public class Event {
    private String title;
    private String content;
    private String courseName;
    private String dueDate;


    public Event(String title, String content,String courseName,String dueDate) {
        this.title = title;
        this.content = content;
        this.courseName = courseName;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getContent() {
        return content;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }


}