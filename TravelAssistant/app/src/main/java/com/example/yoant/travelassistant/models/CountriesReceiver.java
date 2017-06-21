package com.example.yoant.travelassistant.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "countries")
public class CountriesReceiver {

    @PrimaryKey
    public String json;

    public CountriesReceiver() {
    }

    @Ignore
    public CountriesReceiver(String json) {
        this.json = json;
    }
}
