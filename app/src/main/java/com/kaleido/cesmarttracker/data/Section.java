package com.kaleido.cesmarttracker.data;

import java.util.ArrayList;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class Section {
    private int id;
    private Period period;
    private ArrayList<Student> students;
    private int maxSeat;
    public Section(int id, Period period, int maxSeat) {
        this.id = id;
        this.period = period;
        this.maxSeat = maxSeat;
        students = new ArrayList<Student>();
    }

    public Section(int id, int maxSeat) {
        this.id = id;
        this.period = period;
        this.maxSeat = maxSeat;
    }

    public int getId() {
        return id;
    }

    public Period getPeriod() {
        return period;
    }

    public ArrayList<Student> getAllStudent() {
        return students;
    }

    public int getMaxSeat() {
        return maxSeat;
    }

    public int getAvailableSeat() {
        return maxSeat-students.size();
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public void removeStudent(Student s) {
        students.remove(s);
    }

    public void printAllStudent() {
        for(Student s : students)
            System.out.println(">> "+id+" : "+s.getName());
    }
}
