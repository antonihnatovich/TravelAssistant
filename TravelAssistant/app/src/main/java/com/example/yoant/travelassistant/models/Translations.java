package com.example.yoant.travelassistant.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Translations implements Parcelable {

    private String de;
    private String es;
    private String fr;
    private String ja;
    private String it;
    private String br;
    private String pt;

    protected Translations(Parcel in) {
        de = in.readString();
        es = in.readString();
        fr = in.readString();
        ja = in.readString();
        it = in.readString();
        br = in.readString();
        pt = in.readString();
    }

    public static final Creator<Translations> CREATOR = new Creator<Translations>() {
        @Override
        public Translations createFromParcel(Parcel in) {
            return new Translations(in);
        }

        @Override
        public Translations[] newArray(int size) {
            return new Translations[size];
        }
    };

    public Translations() {
    }

    public Translations(String de, String es, String fr, String ja, String it, String br, String pt) {

        this.de = de;
        this.es = es;
        this.fr = fr;
        this.ja = ja;
        this.it = it;
        this.br = br;
        this.pt = pt;
    }

    public String getDe() {
        return de;
    }

    public String getEs() {
        return es;
    }

    public String getFr() {
        return fr;
    }

    public String getJa() {
        return ja;
    }

    public String getIt() {
        return it;
    }

    public String getBr() {
        return br;
    }

    public String getPt() {
        return pt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(de);
        parcel.writeString(es);
        parcel.writeString(fr);
        parcel.writeString(ja);
        parcel.writeString(it);
        parcel.writeString(br);
        parcel.writeString(pt);
    }
}