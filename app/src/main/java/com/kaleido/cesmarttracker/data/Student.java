package com.kaleido.cesmarttracker.data;

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
    private HashMap<Event,Boolean> inbox;
    private ArrayList<Event> events;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        schedule = new Schedule();
        transcript = new Transcript();
        this.inbox = new HashMap<Event,Boolean>();
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

//    public int updateEvents(){
//        for(int i=0;i<getCurrentCourses().size();i++){
//            for(int j=0;j<getCurrentCourses().get(i).getEvents().size();j++){
//                if(!inbox.containsKey(getCurrentCourses().get(i).getEvents().get(j))){
//                    inbox.put(getCurrentCourses().get(i).getEvents().get(j), Boolean.FALSE);
//                    events.add(getCurrentCourses().get(i).getEvents().get(j));
//                }
//            }
//        }
//        ArrayList<Boolean> read=new ArrayList<>(inbox.values());
//        int unread=0;
//        for(int i=0;i<read.size();i++){
//            if(!read.get(i)){
//                unread++;
//            }
//        }
//        return unread;
//    }

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


