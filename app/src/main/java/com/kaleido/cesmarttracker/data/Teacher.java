package com.kaleido.cesmarttracker.data;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Touch on 10/15/2015.
 */
public class Teacher implements Parcelable {
    private String id;
    private String name;
    private ArrayList<Course> courses = new ArrayList();

    public Teacher() {
    }
    public Teacher(String id,String name){
        this.id = id;
        this.name = name;
    }

    protected Teacher(Parcel in) {
        id = in.readString();
        name = in.readString();
        courses = in.createTypedArrayList(Course.CREATOR);
    }

    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel in) {
            return new Teacher(in);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };

    public String getId() {
        return id;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public Event announce(Course c, String title, String content, String dueDate, String type) {
        String announcedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        return new Event(title,content,c.getName(),dueDate,announcedDate,type);
    }

    public void grade(Student student,String semester,Course course,double grade) {
        student.getTranscript().addCourse(semester, course, grade);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeTypedList(courses);
    }

    public void gradeAssigment(Assignment assignment,int score){
        assignment.setScore(score);
    }

    //TODO: announceAssignment on Server
//    public void announceAssignment(Course course,String title,String content,int maxScore,String dueDate){
//        for(int i=0;i<course.getSections().size();i++){
//            for(int j=0;j<course.getSections().get(i).getAllStudent().size();j++){
//                Assignment a=new Assignment(title,content,course.getName(),dueDate,maxScore);
//                course.getSections().get(i).getAllStudent().get(j).addEvent(a);
//                course.getSections().get(i).getAllStudent().get(j).addAssignment(a);
//            }
//        }
//    }
}

