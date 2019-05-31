package br.edu.utfpr.alunos.felipe.meetingcalendar.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import br.edu.utfpr.alunos.felipe.meetingcalendar.MainActivity;
import br.edu.utfpr.alunos.felipe.meetingcalendar.R;

@Entity(tableName = "local")
public class Local implements Parcelable {

    private static Context context = MainActivity.getContext();

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "local_id")
    private int id;

    private String name;

    @ColumnInfo(name = "local_description")
    private String description;

    private String address;

    public Local(String name, String description, String address) {
        this.setName(name);
        this.setDescription(description);
        this.setAddress(address);
    }

    protected Local(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        address = in.readString();
    }

    public static final Creator<Local> CREATOR = new Creator<Local>() {
        @Override
        public Local createFromParcel(Parcel in) {
            return new Local(in);
        }

        @Override
        public Local[] newArray(int size) {
            return new Local[size];
        }
    };

    public void setId(int id) { this.id = id; }

    public int getId() { return this.id; }

    public String getName(){
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAddress() {
        return this.address;
    }

    public void setName(String nameLocal) {
        this.name = nameLocal;
    }

    public void setDescription(String descriptionLocal) {
        this.description = descriptionLocal;
    }

    public void setAddress(String addressLocal) {
        this.address = addressLocal;
    }

    @Override
    public String toString() {
        return context.getResources().getString(R.string.input_local_name) + ": " + getName() + "\n \n"
                + context.getResources().getString(R.string.input_local_description) + ": " + getDescription() + "\n \n"
                + context.getResources().getString(R.string.input_local_address) + ": " + getAddress();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(address);
    }
}
