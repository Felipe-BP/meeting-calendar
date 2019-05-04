package br.edu.utfpr.alunos.felipe.meetingcalendar;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;


public class Meeting implements Parcelable {

    public static Context context = MainActivity.getContext();

    private String title;
    private String description;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private LocalDate date;
    private Local local;

    //constructor to Parcel (read)
    private Meeting(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.timeStart = LocalTime.parse(in.readString());
        this.timeEnd = LocalTime.parse(in.readString());
        this.date = LocalDate.parse(in.readString());
        this.local = in.readParcelable(Local.class.getClassLoader());
    }

    //method to write parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.timeStart.toString());
        dest.writeString(this.timeEnd.toString());
        dest.writeString(this.date.toString());
        dest.writeParcelable(this.local, flags);
    }

    //default constructor
    public Meeting(String titleMeeting, String descriptionMeeting, LocalTime startMeeting, LocalTime endMeeting, LocalDate dateMeeting, Local localMeeting) {
        this.setTitle(titleMeeting);
        this.setDescription(descriptionMeeting);
        this.setTimeStart(startMeeting);
        this.setTimeEnd(endMeeting);
        this.setDate(dateMeeting);
        this.setLocal(localMeeting);
    }

    //Gets and sets
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getTimeStart() {
        return this.timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return this.timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Local getLocal() {
        return this.local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    //Creator a Parcelable
    public static final Parcelable.Creator<Meeting> CREATOR = new Parcelable.Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    @Override
    public String toString() {
        return context.getResources().getString(R.string.input_title) + ": " + getTitle() + "\n \n"
                + context.getResources().getString(R.string.input_description) + ": " + getDescription() + "\n \n"
                + context.getResources().getString(R.string.title_activity__list_label_init) + getTimeStart().toString() + "\n"
                + context.getResources().getString(R.string.title_activity__list_label_end) + getTimeEnd().toString() + "\n \n"
                + context.getResources().getString(R.string.title_activity__list_label_local)+ "\n" + getLocal().toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
