package com.example.yoant.travelassistant.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.yoant.travelassistant.models.CountriesReceiver;

import java.util.List;

@Dao
public interface CountriesDao {

    @Query("SELECT * FROM countries")
    List<CountriesReceiver> getAll();

    @Insert
    void insertData(CountriesReceiver... json);

    @Delete
    void delete(CountriesReceiver json);

}
