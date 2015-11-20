package com.kaleido.cesmarttracker.data;

import java.util.ArrayList;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class Schedule {
    //private HashMap<Course, Section> courseSectionHashMap;
    private ArrayList<Course> currentCourses;
    private ArrayList<Section> currentSections;
    private int maxCredit;

    public Schedule() {
        currentCourses = new ArrayList<Course>();
        currentSections = new ArrayList<Section>();
        maxCredit = 22; // maximum credit available per semester
    }

    public ArrayList<Course> getCurrentCourses() {
        return currentCourses;
    }

    public ArrayList<Section> getCurrentSections() {
        return currentSections;
    }

    public int getLeftCredit() {
        int usedCredit = 0;
        for (Course cc : getCurrentCourses())
            usedCredit += cc.getCredit();
        return maxCredit - usedCredit;
    }

    public boolean isScheduleAvailable(Course c, Section sec) {
        Period secPeriod = sec.getPeriod();
        for (Section sect : getCurrentSections()) {
            if (sect.getPeriod().isTimeOverlapped(secPeriod))
                return false;
        }
        return true;
    }

    public void addCourse(Course c, Section sec) {
        currentCourses.add(c);
        currentSections.add(sec);
    }

    public void removeCourse(Course c) {
        int i = -1;
        for(int j=0; j<currentCourses.size(); j++) {
            if(c.getId().equals(currentCourses.get(j).getId())) {
                i = j;
                break;
            }
        }
        if(i>=0) {
            currentCourses.remove(i);
            currentSections.remove(i);
        }
    }

    public int getTotalCredit() {
        int usedCredit = 0;
        for(Course cc : currentCourses)
            usedCredit += cc.getCredit();
        return usedCredit;
    }

    public Course findCourseById(String id) {
        for(Course cc : currentCourses) {
            if(cc.getId().equals(id))
                return cc;
        }
        return null;
    }

    public Section findSectionByCourse(Course c) {
        for(int i=0; i<currentCourses.size(); i++) {
            if(currentCourses.get(i).getId().equals(c.getId()))
                return currentSections.get(i);
        }
        System.out.println("findSectionByCourse : not found");
        return null;
    }
}

