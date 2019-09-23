package com.neon.lms.model;

import androidx.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;


public class TeacherSpecialModel extends BaseObservable implements Parcelable {

    private String fName, lName;
    private int image;

    public TeacherSpecialModel() {
    }

    protected TeacherSpecialModel(Parcel in) {
        fName = in.readString();
        lName = in.readString();
        image = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fName);
        dest.writeString(lName);
        dest.writeInt(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TeacherSpecialModel> CREATOR = new Creator<TeacherSpecialModel>() {
        @Override
        public TeacherSpecialModel createFromParcel(Parcel in) {
            return new TeacherSpecialModel(in);
        }

        @Override
        public TeacherSpecialModel[] newArray(int size) {
            return new TeacherSpecialModel[size];
        }
    };

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static Creator<TeacherSpecialModel> getCREATOR() {
        return CREATOR;
    }
}