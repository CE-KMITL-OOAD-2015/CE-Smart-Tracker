package com.kaleido.cesmarttracker.data;


/**
 * Created by Touch on 10/15/2015.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 *
 * @author Touch
 */
public class Course implements Parcelable {
    private String name;
    private String id;
    private int credit;
    private String category;
    private ArrayList<Section> sections;
    private ArrayList<Review> reviews;
    private ArrayList<Teacher> teachers;

    public Course(String id, String name,String category, int credit) {
        this.category=category;
        this.name = name;
        this.id = id;
        this.credit = credit;
        sections = new ArrayList<Section>();
        reviews = new ArrayList<Review>();
        teachers = new ArrayList<Teacher>();
    }

    public Course(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.credit = in.readInt();
        this.category = in.readString();
        this.sections = new ArrayList<Section>();
        in.readTypedList(sections,Section.CREATOR);
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getCredit() {
        return credit;
    }

    public ArrayList<Section> getAllSection() {
        return sections;
    }

    public ArrayList<Review> getAllReview() {
        return reviews;
    }

    public ArrayList<Teacher> getAllTeacher() {
        return teachers;
    }

    public void addSection(Section sec) {
        sections.add(sec);
    }

    public ArrayList<Student> getAllStudent() {
        ArrayList<Student> students = new ArrayList<Student>();
        for(Section sec : sections)
            students.addAll(sec.getAllStudent());
        return students;
    }

    public void addTeacher(Teacher teacher){
        teachers.add(teacher);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeInt(credit);
        dest.writeString(category);
        dest.writeTypedList(sections);
    }

    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {

        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

}