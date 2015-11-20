package com.kaleido.cesmarttracker.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Touch on 10/15/2015.
 */
public class Student implements Parcelable {
    private String id;
    private String name;
    private Schedule schedule;
    private Transcript transcript;
    private ArrayList<Event> events;
    private ArrayList<Assignment> assignments;
    private Department department;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        schedule = new Schedule();
        transcript = new Transcript();
        this.events = new ArrayList<Event>();
        this.assignments = new ArrayList<Assignment>();
    }

    protected Student(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

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

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
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
    public int getTotalCredit(){
        return schedule.getTotalCredit()+transcript.getTotalCredit();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }


    //TODO: from here on
    public ArrayList<String> getAllCategory() {
        ArrayList<String> categories = new ArrayList<String>();
        for(Course c : transcript.getAllTakenCourses()) {
            boolean repeated = false;
            for(String str : categories) {
                if(str.equals(c.getCategory())) {
                    repeated = true;
                    break;
                }
            }
            if(!repeated)
                categories.add(c.getCategory());
        }
        return categories;
    }


    public int getTotalGradeAssignment(Course course){
        int total=0;
        if(assignments!=null)
        for (Assignment assignment:assignments){
            if(assignment.getCourseName().contentEquals(course.getName()))
                total+=assignment.getScore();
        }
        return total;
    }
    public int getMaxGradeAssignment(Course course){
        int total=0;
        if(assignments!=null)
        for (Assignment assignment:assignments){
            if(assignment.getCourseName().contentEquals(course.getName()))
                total+=assignment.getMaxScore();
        }
        return total;
    }

    public int getGradeByAssignment(Assignment assignment){
        return assignment.getScore();
    }

    public void addAssignment(Assignment assignment){
        assignments.add(assignment);
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}


