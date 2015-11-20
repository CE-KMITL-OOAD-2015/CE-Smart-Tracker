package com.kaleido.cesmarttracker.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.bson.types.ObjectId;

/**
 * Created by Touch on 10/15/2015.
 */
public class Event implements Parcelable{
    private String objectId;
    private String title;
    private String content;
    private String courseName;
    private String dueDate;
    private String announceDate;
    private String type;
    private boolean read;


    public Event(String title, String content,String courseName,String dueDate,String announceDate,String type) {
        this.title = title;
        this.content = content;
        this.courseName = courseName;
        this.dueDate = dueDate;
        this.announceDate = announceDate;
        this.type = type;
        read = false;
        objectId = new ObjectId().toString();
    }

    public Event(String title, String content,String courseName,String dueDate,String type) {
        //TODO:
        objectId = new ObjectId().toString();
        this.title = title;
        this.content = content;
        this.courseName = courseName;
        this.dueDate = dueDate;
        this.type = type;
        read = false;
    }

    protected Event(Parcel in) {
        title = in.readString();
        content = in.readString();
        courseName = in.readString();
        dueDate = in.readString();
        announceDate = in.readString();
        type = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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

    public boolean isRead() {
        return read;
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

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setAnnounceDate(String announceDate) {
        this.announceDate = announceDate;
    }

    public String getAnnounceDate() {
        return announceDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(courseName);
        dest.writeString(dueDate);
        dest.writeString(announceDate);
        dest.writeString(type);
    }
}