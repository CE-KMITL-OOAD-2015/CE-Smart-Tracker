package com.kaleido.cesmarttracker.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class Section implements Parcelable {
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

    protected Section(Parcel in) {
        id = in.readInt();
        maxSeat = in.readInt();
    }

    public static final Creator<Section> CREATOR = new Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel in) {
            return new Section(in);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(maxSeat);
        dest.writeTypedList(students);
    }
}
