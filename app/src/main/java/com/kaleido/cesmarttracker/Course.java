package com.kaleido.cesmarttracker;

/**
 * Created by Touch on 10/15/2015.
 */
import java.util.ArrayList;

/**
 *
 * @author Touch
 */
public class Course {
    private ArrayList<Teacher> teachers;
    private ArrayList<Event> events;
    private String id;
    private String name;

    public Course(String id, String name) {
        this.events = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.id = id;
        this.name = name;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public String getId() {
        return id;
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

    public void addTeacher(Teacher teacher){
        teachers.add(teacher);
    }
    public void addEvent(Event event){
        events.add(event);
    }


}

