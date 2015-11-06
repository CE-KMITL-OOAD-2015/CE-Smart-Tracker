package com.kaleido.cesmarttracker;


import com.kaleido.cesmarttracker.data.Course;
import com.kaleido.cesmarttracker.data.Period;
import com.kaleido.cesmarttracker.data.Section;
import com.kaleido.cesmarttracker.data.Student;
import com.kaleido.cesmarttracker.data.Teacher;

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
        Course c = new Course("100", "Computer programming","Software",3);
        Course o = new Course("11014", "Object Oriented Analysis and Design","Software",3);
        Course d = new Course("102", "Data Communication","Network",3);
        Course t = new Course("11015", "Theory of Computation","Software",3);
        Course digi = new Course("104", "Digital Circuit","Hardware",3);
        Student s = new Student("001", "s");
        o.addSection(new Section(1, new Period("9:00", 30, "Monday"), 50));
        d.addSection(new Section(1, new Period("9:30", 30, "Tuesday"), 50));
        t.addSection(new Section(1, new Period("10:00", 30, "Sunday"), 50));
        a.addCourse(o);
        a.addCourse(t);
        a.addCourse(d);
        a.addCourse(c);
        b.addCourse(d);
        s.enroll(o, o.getAllSection().get(0));
        s.enroll(t, t.getAllSection().get(0));
        s.enroll(d, d.getAllSection().get(0));
//        a.announce(o, "M2.5: Production-ready use-case", "ooadNaja", "18/10/2015");
//        a.announce(t, "Assignment03: PRNG", "tocNaja", "18/10/2015");
//        a.announce(t, "toc1", "tocNaja", "30/10/2015");
//        s.readEvent(s.getEvents().get(0));
        a.grade(s, "1/2557", c, 0);
        a.grade(s, "2/2557", digi, 3);
        a.grade(s, "1/2558", t, 3);
        a.grade(s, "1/2558", d, 3.5);
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