package com.kaleido.cesmarttracker;

import java.util.ArrayList;

/**
 * Created by Touch on 10/15/2015.
 */
public class Test {
    public ArrayList<Teacher> teachers;
    public ArrayList<Student> students;
    public Test(){
        this.teachers = new ArrayList<>();
        this.students = new ArrayList<>();
        Teacher a = new Teacher("001", "a");
        Teacher b = new Teacher("002", "b");
        Course o = new Course("101", "ooad");
        Course d = new Course("102", "data");
        Course t = new Course("102", "toc");
        Student s = new Student("001", "s");
        a.addCourse(o);
        a.addCourse(t);
        b.addCourse(d);
        s.addCourse(o);
        s.addCourse(t);
        s.addCourse(d);
        a.announce(o, "M2.5: Production-ready use-case", "ooadNaja","16/10/2015");
        a.announce(t, "Assignment03: PRNG","tocNaja","16/10/2015");
        a.announce(t, "toc1", "tocNaja","30/10/2015");
        s.updateEvents();
        s.readEvent(s.getEvents().get(0));
        teachers.add(a);
        teachers.add(b);
        students.add(s);
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}
