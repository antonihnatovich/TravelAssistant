package com.example.yoant.travelassistant.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.yoant.travelassistant.dao.CountriesDao;
import com.example.yoant.travelassistant.models.CountriesReceiver;

@Database(entities = {CountriesReceiver.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CountriesDao countryModel();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "countries-database")
                    .allowMainThreadQueries()
                    .build();
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
