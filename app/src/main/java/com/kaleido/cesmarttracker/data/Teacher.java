package com.kaleido.cesmarttracker.data;

import java.util.ArrayList;

/**
 * Created by Touch on 10/15/2015.
 */
public class Teacher {
    private String id;
    private String name;
    private ArrayList<Course> courses = new ArrayList();

    public Teacher() {
    }
    public Teacher(String id,String name){
        this.id = id;
        this.name = name;
    }

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
    public void announce(Course course,String title,String content,String dueDate){
        for(int i=0;i<course.getAllSection().size();i++){
            for(int j=0;j<course.getAllSection().get(i).getAllStudent().size();j++){
                course.getAllSection().get(i).getAllStudent().get(j).addEvent(new Event(title,content,course.getName(),dueDate));
            }
        }
    }
    public void grade(Student student,String semester,Course course,double grade) {
        student.getTranscript().addCourse(semester, course, grade);
    }
}

