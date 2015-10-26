package com.kaleido.cesmarttracker.data;

/**
 * Created by Touch on 10/15/2015.
 */

import java.util.ArrayList;

/**
 *
 * @author Touch
 */
public class Course {
    private String name;
    private String id;
    private int credit;
    private String category;
    private ArrayList<Section> sections;
    private ArrayList<Review> reviews;
    private ArrayList<Teacher> teachers;

    public Course(String name, String id,String category, int credit) {
        this.category=category;
        this.name = name;
        this.id = id;
        this.credit = credit;
        sections = new ArrayList<Section>();
        reviews = new ArrayList<Review>();
        teachers = new ArrayList<Teacher>();
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

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void addSection(Section sec) {
        sections.add(sec);
    }

    public void getAllStudent() {
        ArrayList<Student> students = new ArrayList<Student>();
        for(Section sec : sections)
            students.addAll(sec.getAllStudent());
    }

    public void addTeacher(Teacher teacher){
        teachers.add(teacher);
    }

}