package com.kaleido.cesmarttracker.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class Transcript {
    HashMap<String,HashMap<Course,Double>> transcript;

    public Transcript() {
        transcript =new HashMap<>();
    }

    public void addCourse(String semester,Course course,double grade){
        if(!transcript.containsKey(semester)){
            transcript.put(semester, new HashMap<Course, Double>());
        }
        transcript.get(semester).put(course, grade);
    }

    public HashMap<Course,Double> getTakenCoursesBySemester(String semester){
        return transcript.get(semester);
    }

    public int getCreditBySemester(String semester){
        int credit=0;
        ArrayList<Course> courses=new ArrayList<>(getTakenCoursesBySemester(semester).keySet());
        for (int i =0;i<courses.size();i++){
            credit+=courses.get(i).getCredit();
        }
        return credit;
    }
    public ArrayList<String> getSemesters(){
        return new ArrayList<>(transcript.keySet());
    }

    public int getAllCredits(){
        int credit=0;
        ArrayList<String> semesters=new ArrayList<>(transcript.keySet());
        for (int i =0;i<semesters.size();i++){
            credit+=getCreditBySemester(semesters.get(i));
        }
        return credit;
    }

    public double getGPS(String semester){
        double grade=0;
        double credit=getCreditBySemester(semester);
        ArrayList<Course> courses = new ArrayList<>(getTakenCoursesBySemester(semester).keySet());
        ArrayList<Double> grades = new ArrayList<>(getTakenCoursesBySemester(semester).values());
        for(int i=0;i<courses.size();i++){
            grade+=grades.get(i)*courses.get(i).getCredit();
        }
        return grade/credit;
    }
    public HashMap<Course,Double> getAllTakenCourses(){
        HashMap<Course,Double> courses=new HashMap<>();
        ArrayList<String> semester=new ArrayList<>(transcript.keySet());
        for(int i=0;i<transcript.size();i++) {
            courses.putAll(transcript.get(semester.get(i)));
        }
        return courses;
    }

    public double getGPA(){
        double grade=0;
        double credit=getAllCredits();
        ArrayList<Course> courses=new ArrayList<>();
        ArrayList<Double> grades = new ArrayList<>();
        for(int i=0;i<getSemesters().size();i++){
            courses.addAll(getTakenCoursesBySemester(getSemesters().get(i)).keySet());
            grades.addAll(getTakenCoursesBySemester(getSemesters().get(i)).values());
        }
        for(int i= 0;i<courses.size();i++){
            grade+=grades.get(i)*courses.get(i).getCredit();
        }
        return grade/credit;
    }
}
