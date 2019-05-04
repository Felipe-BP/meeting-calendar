package br.edu.utfpr.alunos.felipe.meetingcalendar;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class Local implements Parcelable {

    private static Context context = MainActivity.getContext();

    private String name;
    private String description;
    private String address;

    public Local(String nameLocal, String descriptionLocal, String addressLocal) {
        this.setName(nameLocal);
        this.setDescription(descriptionLocal);
        this.setAddress(addressLocal);
    }

    protected Local(Parcel in) {
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
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(address);
    }
}
