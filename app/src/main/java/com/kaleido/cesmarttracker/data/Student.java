package com.kaleido.cesmarttracker.data;

import com.kaleido.cesmarttracker.EventActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Touch on 10/15/2015.
 */
public class Student {
    private String id;
    private String name;
    private Schedule schedule;
    private Transcript transcript;
    private ArrayList<Event> events;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        schedule = new Schedule();
        transcript = new Transcript();
        this.events = new ArrayList<Event>();
    }

    public String getId() {return id;}

    public String getName() {return name;}

    public Transcript getTranscript() {
        return transcript;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean enroll(Course c, Section sec) {
        if(sec.getAvailableSeat() <= 0)
            return false;
        if(schedule.getLeftCredit() < c.getCredit())
            return false;
        if(!schedule.isScheduleAvailable(c,sec))
            return false;
        schedule.addCourse(c,sec);
        sec.addStudent(this);
        return true;
    }

    public ArrayList<Course> getCurrentCourses() {
        return schedule.getCurrentCourses();
    }

    public ArrayList<Event> getEvents(){
        return events;
    }

    public void addEvent(Event event){
        events.add(event);
    }

    public void readEvent(Event event){
        event.setRead(true);
    }

    public int getUnread(){
        int unread=0;
        for(int i=0;i<events.size();i++) {
            if(!events.get(i).isRead()){
                unread++;
            }
        }
        return  unread;
    }

}


