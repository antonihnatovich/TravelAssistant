package com.example.yoant.travelassistant.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

class Translations {

    private String de;
    private String es;
    private String fr;
    private String ja;
    private String it;
    private String br;
    private String pt;

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
}