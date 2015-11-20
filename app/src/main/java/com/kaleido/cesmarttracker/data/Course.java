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
    private int teacherAmount;

    public Course(String id, String name,String category, int credit) {
        this.category=category;
        this.name = name;
        this.id = id;
        this.credit = credit;
        sections = new ArrayList<Section>();
        reviews = new ArrayList<Review>();
    }

    public Course(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.credit = in.readInt();
        this.category = in.readString();
        this.sections = new ArrayList<Section>();
        in.readTypedList(sections,Section.CREATOR);
        this.teacherAmount = in.readInt();
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

    public ArrayList<Section> getSections() {
        return sections;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void addSection(Section sec) {
        sections.add(sec);
    }

    public void addReview(Review review) { reviews.add(review); }

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
        dest.writeInt(teacherAmount);
    }

    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {

        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public int getStudentAmount() {
        int result = 0;
        for(Section sec : sections)
            result += sec.getTakenSeat();
        return result;
    }

    public String getClassDay() {
        String str = "", result = "";
        for(Section s : sections)
            str += s.getPeriod().getDay().substring(0,3);
        if(str.contains("Mon"))
            result += "Mon,";
        if(str.contains("Tue"))
            result += "Tue,";
        if(str.contains("Wed"))
            result += "Wed,";
        if(str.contains("Thu"))
            result += "Thu,";
        if(str.contains("Fri"))
            result += "Fri,";
        if(str.contains("Sat"))
            result += "Sat,";
        if(str.contains("Sun"))
            result += "Sun,";
        return result.substring(0,result.length()-1);
    }

    public int getTeacherAmount() {
        return teacherAmount;
    }

    public void setTeacherAmount(int teacherAmount) {
        this.teacherAmount = teacherAmount;
    }
}