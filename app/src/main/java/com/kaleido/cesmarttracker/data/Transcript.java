package com.kaleido.cesmarttracker.data;

import java.util.ArrayList;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class Transcript {
    public ArrayList<Course> takenCourses;
    public ArrayList<Double> grades; // enum?
    public ArrayList<Integer> semesters;

    public Transcript() {
        takenCourses = new ArrayList<Course>();
        grades = new ArrayList<Double>();
        semesters = new ArrayList<Integer>();
    }
}
