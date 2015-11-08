package com.kaleido.cesmarttracker.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class Transcript implements Parcelable {
    ArrayList<String> semesters;
    ArrayList<Course> courses;
    ArrayList<Double> grades;

    public Transcript() {
        semesters = new ArrayList<>();
        courses = new ArrayList<>();
        grades = new ArrayList<>();
    }

    protected Transcript(Parcel in) {
        semesters = in.createStringArrayList();
        courses = in.createTypedArrayList(Course.CREATOR);
    }

    public static final Creator<Transcript> CREATOR = new Creator<Transcript>() {
        @Override
        public Transcript createFromParcel(Parcel in) {
            return new Transcript(in);
        }

        @Override
        public Transcript[] newArray(int size) {
            return new Transcript[size];
        }
    };

    public void addCourse(String semester,Course course,double grade){
        semesters.add(semester);
        courses.add(course);
        grades.add(grade);
    }

    public ArrayList<Course> getTakenCoursesBySemester(String semester){
        ArrayList<Course> takenCourse=new ArrayList<>();
        for(int i=0;i<semesters.size();i++){
            if(semesters.get(i).contentEquals(semester)) {
                takenCourse.add(courses.get(i));
            }
        }
        return takenCourse;
    }

    public int getCreditBySemester(String semester){
        int credit=0;
        ArrayList<Course> courses=getTakenCoursesBySemester(semester);
        for (int i =0;i<courses.size();i++){
            credit+=courses.get(i).getCredit();
        }
        return credit;

    }
    public ArrayList<String> getSemesters(){ //purpose?
        ArrayList<String> semesters= new ArrayList<>();
        for(int i=0;i<this.semesters.size();i++){
            if(!semesters.contains(this.semesters.get(i))){
                semesters.add(this.semesters.get(i));
            }
        }
        return semesters;
    }

    public int getTotalCredit(){
        int credit=0;
        for (int i =0;i<courses.size();i++){
            credit+=courses.get(i).getCredit();
        }
        return credit;
    }

    public double getGrade(String semester,Course course){
        for(int i=0;i<semesters.size();i++){
            if(semesters.get(i).contentEquals(semester)) {
                if (course.getName().contentEquals(courses.get(i).getName())) {
                    return grades.get(i);
                }
            }
        }
        return -1;
    }
    public double getGPS(String semester){
        double grade=0;
        double credit=getCreditBySemester(semester);
        ArrayList<Course> CoursesBySemester = getTakenCoursesBySemester(semester);
        for(int i=0;i<CoursesBySemester.size();i++){
            grade+=CoursesBySemester.get(i).getCredit()*getGrade(semester, CoursesBySemester.get(i));
        }
        return grade/credit;
    }

    public ArrayList<Course> getAllTakenCourses() {
        return courses;
    }

    public double getGPA(){
        double grade=0;
        double credit=getTotalCredit();
        for(int i= 0;i<courses.size();i++){
            grade+=grades.get(i)*courses.get(i).getCredit();
        }
        return grade/credit;
    }

    public ArrayList<Course> getTakenCoursesByCategory(String category) {
        ArrayList<Course> takenCourses=new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCategory().contentEquals(category)) {
                if(!takenCourses.contains(courses.get(i))) { //Why needed?
                    takenCourses.add(courses.get(i));
                }
            }
        }
        return  takenCourses;
    }

    public int getAllPassCredits(){
        int credit=0;
        for (int i =0;i<courses.size();i++){
            if(getGrade(semesters.get(i),courses.get(i))!=0) {
                credit += courses.get(i).getCredit();
            }
        }
        return credit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(semesters);
        dest.writeTypedList(courses);
    }
}