package com.kaleido.cesmarttracker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Touch on 10/15/2015.
 */
public class Student {
    private String id;
    private String name;
    private ArrayList<Course> currentCourses;
    private HashMap<Event,Boolean> inbox;
    private ArrayList<Event> events;
    public Student(String id, String name) {
        this.currentCourses = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.inbox = new HashMap<>();
        this.events = new ArrayList<>();
    }

    public String getId() {return id;}

    public String getName() {return name;}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCourse(Course course){
        currentCourses.add(course);
    }

    public ArrayList<Course> getCurrentCourses() {
        return currentCourses;
    }

    public int updateEvents(){
        for(int i=0;i<getCurrentCourses().size();i++){
            for(int j=0;j<getCurrentCourses().get(i).getEvents().size();j++){
                if(!inbox.containsKey(getCurrentCourses().get(i).getEvents().get(j))){
                    inbox.put(getCurrentCourses().get(i).getEvents().get(j), Boolean.FALSE);
                    events.add(getCurrentCourses().get(i).getEvents().get(j));
                }
            }
        }
        ArrayList<Boolean> read=new ArrayList<>(inbox.values());
        int unread=0;
        for(int i=0;i<read.size();i++){
            if(!read.get(i)){
                unread++;
            }
        }
        return unread;
    }

    public HashMap<Event, Boolean> getInbox() {
        return inbox;
    }

    public ArrayList<Event> getEvents(){
        return events;
    }
    public void readEvent(Event event){
        inbox.put(event, Boolean.TRUE);
        }
        }


