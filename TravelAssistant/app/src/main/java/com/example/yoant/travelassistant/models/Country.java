package com.example.yoant.travelassistant.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Country implements Parcelable {

    private String alpha2Code;
    private String alpha3Code;
    private String name;
    private String nativeName;
    private List<String> altSpellings = new ArrayList<>();
    private String capital = "No capital for this country";
    private String demonym;
    private String region;
    private String subregion;
    private int population;
    private double area;
    private int numericCode;
    private List<String> callingCodes = new ArrayList<>();
    private String flag;

    private List<String> borders = new ArrayList<>();
    private List<String> timezones = new ArrayList<>();
    private List<String> topLevelDomain = new ArrayList<>();
    private List<Currencies> currencies = new ArrayList<>();
    private List<Languages> languages = new ArrayList<>();
    private List<RegionalBlocks> regionalBlocks = new ArrayList<>();
    private Translations translations;

    protected Country(Parcel in) {
        alpha2Code = in.readString();
        alpha3Code = in.readString();
        name = in.readString();
        nativeName = in.readString();
        altSpellings = in.createStringArrayList();
        capital = in.readString();
        demonym = in.readString();
        region = in.readString();
        subregion = in.readString();
        population = in.readInt();
        area = in.readDouble();
        numericCode = in.readInt();
        flag = in.readString();
        borders = in.createStringArrayList();
        timezones = in.createStringArrayList();
        topLevelDomain = in.createStringArrayList();
    }

    public Country() {
    }

    public Country(String alpha2Code, String alpha3Code, String name, String nativeName, List<String> altSpellings, String capital, String demonym, String region, String subregion, int population, double area, int numericCode, List<String> callingCodes, String flag, List<String> borders, List<String> timezones, List<String> topLevelDomain, List<Currencies> currencies, List<Languages> languages, List<RegionalBlocks> regionalBlocks, Translations translations) {
        this.alpha2Code = alpha2Code;
        this.alpha3Code = alpha3Code;
        this.name = name;
        this.nativeName = nativeName;
        this.altSpellings = altSpellings;
        this.capital = capital;
        this.demonym = demonym;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.area = area;
        this.numericCode = numericCode;
        this.callingCodes = callingCodes;
        this.flag = flag;

        this.borders = borders;
        this.timezones = timezones;
        this.topLevelDomain = topLevelDomain;
        this.currencies = currencies;
        this.languages = languages;
        this.regionalBlocks = regionalBlocks;
        this.translations = translations;
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(alpha2Code);
        parcel.writeString(alpha3Code);
        parcel.writeString(name);
        parcel.writeString(nativeName);
        parcel.writeStringList(altSpellings);
        parcel.writeString(capital);
        parcel.writeString(demonym);
        parcel.writeString(region);
        parcel.writeString(subregion);
        parcel.writeInt(population);
        parcel.writeDouble(area);
        parcel.writeInt(numericCode);
        parcel.writeString(flag);
        parcel.writeStringList(borders);
        parcel.writeStringList(timezones);
        parcel.writeStringList(topLevelDomain);
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public String getName() {
        return name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public List<String> getAltSpellings() {
        return altSpellings;
    }

    public String getCapital() {
        return capital;
    }

    public String getDemonym() {
        return demonym;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public int getPopulation() {
        return population;
    }

    public double getArea() {
        return area;
    }

    public int getNumericCode() {
        return numericCode;
    }

    public List<String> getCallingCodes() {
        return callingCodes;
    }

    public String getFlag() {
        return flag;
    }

    public List<String> getBorders() {
        return borders;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public List<String> getTopLevelDomain() {
        return topLevelDomain;
    }

    public List<Currencies> getCurrencies() {
        return currencies;
    }

    public List<Languages> getLanguages() {
        return languages;
    }

    public List<RegionalBlocks> getRegionalBlocks() {
        return regionalBlocks;
    }

    public Translations getTranslations() {
        return translations;
    }

    @Override
    public String toString() {
        return "";
    }
}
