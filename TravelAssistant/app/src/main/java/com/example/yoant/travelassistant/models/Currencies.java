package com.example.yoant.travelassistant.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Currencies implements Parcelable {

    private String code;
    private String name;
    private String symbol;

    protected Currencies(Parcel in) {
        code = in.readString();
        name = in.readString();
        symbol = in.readString();
    }

    public Currencies() {
    }

    public Currencies(String code, String name, String symbol) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }

    public static final Creator<Currencies> CREATOR = new Creator<Currencies>() {
        @Override
        public Currencies createFromParcel(Parcel in) {
            return new Currencies(in);
        }

        @Override
        public Currencies[] newArray(int size) {
            return new Currencies[size];
        }
    };

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(name);
        parcel.writeString(symbol);
    }

    @Override
    public String toString() {
        return code + (symbol == null ? "" : " - " + symbol);
    }
}
