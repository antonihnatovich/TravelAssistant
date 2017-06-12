package com.example.yoant.travelassistant.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RegionalBlocks implements Parcelable {

    private String acronym;
    private String name;
    private List<String> otherAcronyms = new ArrayList<>();
    private List<String> otherNames = new ArrayList<>();

    protected RegionalBlocks(Parcel in) {
        acronym = in.readString();
        name = in.readString();
        otherAcronyms = in.createStringArrayList();
        otherNames = in.createStringArrayList();
    }

    public static final Creator<RegionalBlocks> CREATOR = new Creator<RegionalBlocks>() {
        @Override
        public RegionalBlocks createFromParcel(Parcel in) {
            return new RegionalBlocks(in);
        }

        @Override
        public RegionalBlocks[] newArray(int size) {
            return new RegionalBlocks[size];
        }
    };

    public RegionalBlocks(String acronym, String name, List<String> otherAcronyms, List<String> otherNames) {
        this.acronym = acronym;
        this.name = name;
        this.otherAcronyms = otherAcronyms;
        this.otherNames = otherNames;
    }

    public RegionalBlocks() {

    }

    public String getAcronym() {
        return acronym;
    }

    public String getName() {
        return name;
    }

    public List<String> getOtherAcronyms() {
        return otherAcronyms;
    }

    public List<String> getOtherNames() {
        return otherNames;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(acronym);
        parcel.writeString(name);
        parcel.writeStringList(otherAcronyms);
        parcel.writeStringList(otherNames);
    }
}