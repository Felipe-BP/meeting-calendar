package br.edu.utfpr.alunos.felipe.meetingcalendar.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import br.edu.utfpr.alunos.felipe.meetingcalendar.MainActivity;
import br.edu.utfpr.alunos.felipe.meetingcalendar.R;

@Entity(tableName = "meeting", indices = {@Index("local_Id")},
        foreignKeys =
        @ForeignKey(entity = Local.class,
                    parentColumns = "local_id",
                    childColumns = "local_Id",
                    onDelete = ForeignKey.CASCADE)
        )
public class Meeting implements Parcelable {

    public static Context context = MainActivity.getContext();

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "meeting_id")
    private int id;

    private String title;

    @ColumnInfo(name = "meeting_description")
    private String description;

    private LocalTime timeStart;
    private LocalTime timeEnd;
    private LocalDate date;

    private @ColumnInfo(name = "local_Id") int localId;

    //default constructor
    public Meeting(String title, String description, LocalTime timeStart, LocalTime timeEnd, LocalDate date, int localId) {
        this.setTitle(title);
        this.setDescription(description);
        this.setTimeStart(timeStart);
        this.setTimeEnd(timeEnd);
        this.setDate(date);
        this.setLocalId(localId);
    }

    //constructor to Parcel (read)
    private Meeting(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.timeStart = LocalTime.parse(in.readString());
        this.timeEnd = LocalTime.parse(in.readString());
        this.date = LocalDate.parse(in.readString());
        this.localId = in.readInt();
    }

    //method to write parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.timeStart.toString());
        dest.writeString(this.timeEnd.toString());
        dest.writeString(this.date.toString());
        dest.writeInt(this.localId);
    }

    //Gets and sets


    public void setId(int id) { this.id = id; }

    public int getId() { return id; }

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

    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
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
                + context.getResources().getString(R.string.title_activity__list_label_local)+ "\n" + getLocalId();
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
