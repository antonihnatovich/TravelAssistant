package com.example.yoant.travelassistant.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Languages implements Parcelable {

    private String iso639_1;
    private String iso639_2;
    private String name;
    private String nativeName;

    public Languages(Parcel in) {
        iso639_1 = in.readString();
        iso639_2 = in.readString();
        name = in.readString();
        nativeName = in.readString();
    }

    public Languages(String iso639_1, String iso639_2, String name, String nativeName) {
        this.iso639_1 = iso639_1;
        this.iso639_2 = iso639_2;
        this.name = name;
        this.nativeName = nativeName;
    }

    public Languages() {

    }

    public static final Creator<Languages> CREATOR = new Creator<Languages>() {
        @Override
        public Languages createFromParcel(Parcel in) {
            return new Languages(in);
        }

        @Override
        public Languages[] newArray(int size) {
            return new Languages[size];
        }
    };

    public String getIso639_1() {
        return iso639_1;
    }

    public String getIso639_2() {
        return iso639_2;
    }

    public String getName() {
        return name;
    }

    public String getNativeName() {
        return nativeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(iso639_1);
        parcel.writeString(iso639_2);
        parcel.writeString(name);
        parcel.writeString(nativeName);
    }
}
