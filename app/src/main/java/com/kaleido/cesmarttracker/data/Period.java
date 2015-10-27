package com.kaleido.cesmarttracker.data;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class Period {
    private ArrayList<Date> examDates; // consist of 2 dates, mid and final
    private String examStart;
    private int examDuration;
    private String day; // enum?
    private String classStart;
    private int classDuration;

    public Period(String classStart, int classDuration, String day) {
        this.classStart = classStart;
        this.classDuration = classDuration;
        this.day = day;
        examDates = new ArrayList<Date>();
        examDates.add(null);
        examDates.add(null);
    }

    public void setMidExam(Date date) {
        examDates.set(0, date);
    }

    public void setFinalExam(Date date) {
        examDates.set(1, date);
    }

    public Date getMidExam() {
        return examDates.get(0);
    }

    public Date getFinalExam() {
        return examDates.get(1);
    }

    public String getDay() {
        return day;
    }

    public String getClassStart() {
        return classStart;
    }

    public int getClassDuration() {
        return classDuration;
    }

    public String getExamStart() {
        return examStart;
    }

    public int getExamDuration() {
        return examDuration;
    }

    public void setExamStart(String examStart) {
        this.examStart = examStart;
    }

    public void setExamDuration(int examDuration) {
        this.examDuration = examDuration;
    }

    public void setExamTime(String examStart, int examDuration) {
        this.examStart = examStart;
        this.examDuration = examDuration;
    }

    public boolean isTimeOverlapped(Period p) {
        if(examDates.get(0) != null && getMidExam().equals(p.getMidExam())) {
            Double start = Double.parseDouble(examStart);
            Double start2 = Double.parseDouble(p.getExamStart());
            if(Math.abs(start-start2) < examDuration)
                return true;
        }
        if(examDates.get(1) != null && getFinalExam().equals(p.getFinalExam())) {
            double start = Double.parseDouble(examStart);
            double start2 = Double.parseDouble(p.getExamStart());
            if(Math.abs(start-start2) < examDuration)
                return true;
        }
        if(day.equals(p.getDay())) { //same day?
            if(classStart == p.getClassStart())
                return true;
            double start = Double.parseDouble(classStart), end = start+classDuration;
            double start2 = Double.parseDouble(p.getClassStart()), end2 = start2+p.getClassDuration();
            if(start > start2 && start >= end2)
                return false;
            if(start2 > start && start2 >= end)
                return false;
        }
        else //not in the same day, not overlapped
            return false;
        return true;
    }
}
