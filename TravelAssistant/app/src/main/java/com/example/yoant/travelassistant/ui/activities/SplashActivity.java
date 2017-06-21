package com.example.yoant.travelassistant.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.yoant.travelassistant.database.AppDatabase;
import com.example.yoant.travelassistant.database.DatabaseInitializer;
import com.example.yoant.travelassistant.helper.constants.ViewConstant;
import com.example.yoant.travelassistant.models.CountriesReceiver;
import com.example.yoant.travelassistant.models.Country;
import com.example.yoant.travelassistant.service.CountryService;
import com.example.yoant.travelassistant.service.ServiceFactory;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    private ArrayList<Country> mListCountries = new ArrayList<>();
    Intent intent;
    private Context mContext;
    private boolean isLoadedFromDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, MainActivity.class);
        mContext = getApplicationContext();
        AppDatabase db = AppDatabase.getInMemoryDatabase(getApplicationContext());
        if (DatabaseInitializer.getJsonAsync(db) == null) {
            getCountriesListByRegion();
            new GetJsonAsyncFromApi().execute();
        } else {
            Country[] arrayCountries = new Gson().fromJson(DatabaseInitializer.getJsonAsync(db).json, Country[].class);
            for (Country country : arrayCountries)
                mListCountries.add(country);
            isLoadedFromDatabase = true;
            intent.putParcelableArrayListExtra(ViewConstant.RESTORE_COUNTRIES_LIST, mListCountries);
            Toast.makeText(getApplicationContext(), "Info loaded from local database", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }

    }

    private void getCountriesListByRegion() {
        CountryService service = ServiceFactory.createRetrofitService(CountryService.class, CountryService.SERVICE_ENDPOINT);
        service.getAllCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Country>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Country Demo", e.getMessage());
                    }

                    @Override
                    public void onNext(List<Country> countries) {
                        mListCountries = (ArrayList) countries;
                        isLoadedFromDatabase = false;
                        intent.putParcelableArrayListExtra(ViewConstant.RESTORE_COUNTRIES_LIST, mListCountries);
                        Toast.makeText(getApplicationContext(), "Info loaded from the Internet ", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private class GetJsonAsyncFromApi extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String endpoint = "https://restcountries.eu/rest/v2/all";
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(endpoint);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder buffer = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null)
                    buffer.append(line);
                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            DatabaseInitializer.insertJsonAsync(AppDatabase.getInMemoryDatabase(mContext), new CountriesReceiver(result));
        }
    }
}
