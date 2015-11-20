package com.kaleido.cesmarttracker.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;

/**
 * Created by pirushprechathavanich on 10/24/15.
 */
public class Section implements Parcelable {
    private String refId;
    private int id;
    private Period period;
    private int takenSeat = 0;
    private int maxSeat;
    public Section(String refId, int id, Period period, int maxSeat) {
        this.refId = refId;
        this.id = id;
        this.period = period;
        this.maxSeat = maxSeat;
        //students = new ArrayList<Student>();
    }

    public Section(int id, int maxSeat) {
        this.id = id;
        this.maxSeat = maxSeat;
    }

    protected Section(Parcel in) {
        id = in.readInt();
        period = in.readParcelable(Period.class.getClassLoader());
        takenSeat = in.readInt();
        maxSeat = in.readInt();
//        this.students = new ArrayList<Student>();
//        in.readTypedList(students,Student.CREATOR);
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

//    public ArrayList<Student> getAllStudent() {
//        return students;
//    }

    public int getMaxSeat() {
        return maxSeat;
    }

    public int getTakenSeat() {
        return takenSeat;
    }

    public void setTakenSeat(int takenSeat) {
        this.takenSeat = takenSeat;
    }

    public int getAvailableSeat() {
        return maxSeat-takenSeat;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public boolean addStudent(Student s) {
        if(maxSeat>takenSeat) {
            takenSeat++;
            return true;
        }
        System.out.println("Error! No available seat to take! : Section " + id);
        return false;
    }

    public boolean removeStudent(Student s) {
        if (takenSeat > 0) {
            takenSeat--;
            return true;
        }
        System.out.println("Error! No seat to remove! : Section " + id);
        return false;
    }


    //TODO gu change here
    @Override
    public String toString() {
        Double end;
        end = Double.parseDouble(period.getClassStart())+period.getClassDuration();
        DecimalFormat df = new DecimalFormat("###.##");
        df.setMinimumFractionDigits(2);

        return id+" "+period.getDay().substring(0,3)+", "+period.getClassStart()+"-"+df.format(end);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(period, flags);
        dest.writeInt(takenSeat);
        dest.writeInt(maxSeat);
    }
}
