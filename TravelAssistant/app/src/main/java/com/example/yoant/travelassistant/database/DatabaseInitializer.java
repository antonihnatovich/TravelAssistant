package com.example.yoant.travelassistant.database;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.yoant.travelassistant.models.CountriesReceiver;

public class DatabaseInitializer {

    public static CountriesReceiver jsonData;

    public static void insertJsonAsync(final AppDatabase appDatabase, @NonNull CountriesReceiver json) {
        new InsertDataAsync(appDatabase).execute(json);
    }

    public static CountriesReceiver getJsonAsync(final AppDatabase db) {
        if (!db.countryModel().getAll().isEmpty())
            jsonData = db.countryModel().getAll().get(0);
        return jsonData;
    }

    public static void deleteDataFromDatabase(final AppDatabase appDatabase, CountriesReceiver countriesReceiver) {
        appDatabase.countryModel().delete(countriesReceiver);
    }

    private static class InsertDataAsync extends AsyncTask<CountriesReceiver, Void, Void> {

        private final AppDatabase mAppDatabase;

        InsertDataAsync(AppDatabase db) {
            mAppDatabase = db;
        }

        @Override
        protected Void doInBackground(CountriesReceiver... jsons) {
            mAppDatabase.countryModel().insertData(jsons);
            return null;
        }
    }

    private static class GetDataAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase appDatabase;

        GetDataAsync(AppDatabase db) {
            appDatabase = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            jsonData = appDatabase.countryModel().getAll().get(0);
            return null;
        }
    }

}
