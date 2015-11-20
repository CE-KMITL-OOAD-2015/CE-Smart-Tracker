package com.kaleido.cesmarttracker.data;

import java.util.ArrayList;

/**
 * Created by pirushprechathavanich on 11/18/15.
 */
public class Department {
    private String id;
    private String name;
    private ArrayList<Course> requiredCoursed;

    public Department(String id, String name) {
        this.id = id;
        this.name = name;
        requiredCoursed = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getRequiredCoursed() {
        return requiredCoursed;
    }

    public void setRequiredCoursed(ArrayList<Course> requiredCoursed) {
        this.requiredCoursed = requiredCoursed;
    }

    public void addRequiredCourse(Course c) {
        requiredCoursed.add(c);
    }
}
