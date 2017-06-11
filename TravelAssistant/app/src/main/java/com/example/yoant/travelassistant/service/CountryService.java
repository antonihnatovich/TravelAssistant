package com.example.yoant.travelassistant.service;

import com.example.yoant.travelassistant.models.Country;

import java.util.List;

import retrofit.http.Path;
import retrofit.http.GET;
import rx.Observable;

public interface CountryService {
    String SERVICE_ENDPOINT = "https://restcountries.eu/rest/v2";

    @GET("/name/{name}?fullText=true")
    Observable<List<Country>> getCountryByName(@Path("name") String name);

    @GET("/region/{region}")
    Observable<List<Country>> getCountriesByRegion(@Path("region") String region);

    @GET("/lang/{et}")
    Observable<List<Country>> getCountriesByLanguage(@Path("et") String et);

    @GET("/capital/{capital}")
    Observable<List<Country>> getCountryByCapital(@Path("capital") String capital);

    @GET("/alpha/{code}")
    Observable<List<Country>> getCountryBy2or3LetterCode(@Path("code") String code);

}
