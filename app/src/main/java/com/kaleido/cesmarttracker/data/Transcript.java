package com.kaleido.cesmarttracker.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class Transcript implements Parcelable {
    ArrayList<String> semesters;
    ArrayList<Course> takenCourses;
    ArrayList<Double> grades;

    public Transcript() {
        semesters = new ArrayList<>();
        takenCourses = new ArrayList<>();
        grades = new ArrayList<>();
    }

    protected Transcript(Parcel in) {
        semesters = in.createStringArrayList();
        takenCourses = in.createTypedArrayList(Course.CREATOR);
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
        takenCourses.add(course);
        grades.add(grade);
    }

    public ArrayList<Course> getTakenCoursesBySemester(String semester){
        ArrayList<Course> takenCourse=new ArrayList<>();
        for(int i=0;i<semesters.size();i++){
            if(semesters.get(i).contentEquals(semester)) {
                takenCourse.add(takenCourses.get(i));
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
    public ArrayList<String> getAllSemesters(){ //purpose?
        ArrayList<String> semesters= new ArrayList<>();
        for(int i=0;i<this.semesters.size();i++){
            if(!semesters.contains(this.semesters.get(i))){
                semesters.add(this.semesters.get(i));
            }
        }
        return semesters;
    }

    public ArrayList<String> getSemesters(){
        return semesters;
    }

//    public int getTotalCredit(){
//        int credit=0;
//        for (int i =0;i<courses.size();i++){
//            credit+=courses.get(i).getCredit();
//        }
//        return credit;
//    }

    public double getGrade(String semester,Course course){
        for(int i=0;i<semesters.size();i++){
            if(semesters.get(i).contentEquals(semester)) {
                if (course.getName().contentEquals(takenCourses.get(i).getName())) {
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
        return takenCourses;
    }

//    public double getGPA(){
//        double grade=0;
//        double credit=getTotalCredit();
//        double abc;
//        for(int i= 0;i<courses.size();i++){
//            grade+=grades.get(i)*courses.get(i).getCredit();
//        }
//        abc = grade/credit;
//        DecimalFormat df = new DecimalFormat("###.##");
//        abc = Double.parseDouble(df.format(abc));
//        return abc;
//    }

    public ArrayList<Course> getTakenCoursesByCategory(String category) {
        ArrayList<Course> takenCourses=new ArrayList<>();
        for (int i = 0; i < takenCourses.size(); i++) {
            if (takenCourses.get(i).getCategory().contentEquals(category)) {
                if(!takenCourses.contains(takenCourses.get(i))) { //Why needed?
                    takenCourses.add(takenCourses.get(i));
                }
            }
        }
        return  takenCourses;
    }

    public int getAllPassCredits(){
        int credit=0;
        for (int i =0;i<takenCourses.size();i++){
            if(getGrade(semesters.get(i),takenCourses.get(i))!=0) {
                credit += takenCourses.get(i).getCredit();
            }
        }
        return credit;
    }

    public int getTotalCredit(){
        int credit=0;
        if(takenCourses.size()==0){
            return 0;
        }
        for (int i =0;i<takenCourses.size();i++){
            credit+=takenCourses.get(i).getCredit();
        }
        return credit;
    }

    public double getGPA(){
        double grade=0.00;
        double credit=(double)getTotalCredit();
        double gpa = 0.00;

        if(takenCourses.size()==0){
            return 0.00;
        }
        for(int i= 0;i<takenCourses.size();i++){
            grade+=grades.get(i)*(double)takenCourses.get(i).getCredit();
        }
        gpa = grade/credit;
        DecimalFormat df = new DecimalFormat("###.##");
        gpa = Double.parseDouble(df.format(gpa));
        return gpa;
    }

    public  double gradeBeforeDivide(){
        double grade=0.00;


        if(takenCourses.size()==0){
            return 0.00;
        }
        for(int i= 0;i<takenCourses.size();i++){
            grade+=grades.get(i)*(double)takenCourses.get(i).getCredit();
        }
        return grade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(semesters);
        dest.writeTypedList(takenCourses);
    }
}