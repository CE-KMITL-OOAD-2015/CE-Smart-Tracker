package com.kaleido.cesmarttracker.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class Schedule {
    private HashMap<Course, Section> courseSectionHashMap;
    private int maxCredit;

    public Schedule() {
        courseSectionHashMap = new HashMap<Course, Section>();
        maxCredit = 22; // maximum credit available per semester
    }

    public ArrayList<Course> getCurrentCourses() {
        //return (ArrayList<Course>)courseSectionHashMap.keySet();
        return new ArrayList<Course>(courseSectionHashMap.keySet());
    }

    public ArrayList<Section> getCurrentSections() {
        //return (ArrayList<Section>)courseSectionHashMap.values();
        return new ArrayList<Section>(courseSectionHashMap.values());
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
        courseSectionHashMap.put(c, sec);
    }

    public void removeCourse(Course c) {
        courseSectionHashMap.remove(c);
    }

    public int getAllCredits(){
        int credit=0;
        for (int i =0;i<getCurrentCourses().size();i++){
            credit+=getCurrentCourses().get(i).getCredit();
        }
        return credit;
    }
}