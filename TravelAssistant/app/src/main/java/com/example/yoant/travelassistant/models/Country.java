package com.example.yoant.travelassistant.models;

import java.util.ArrayList;
import java.util.List;

public class Country {

    private String alpha2Code;
    private String alpha3Code;
    private String name;
    private String nativeName;
    private List<String> altSpellings = new ArrayList<>();
    private String capital;
    private String demonym;
    private String region;
    private String subregion;
    private int population;
    private double area;
    private int numericCode;
    private List<Integer> callingCodes = new ArrayList<>();
    private String flag;
    private List<String> borders = new ArrayList<>();
    private List<String> timezones = new ArrayList<>();
    private List<String> topLevelDomain = new ArrayList<>();
    private List<Currencies> currencies = new ArrayList<>();
    private List<Languages> languages = new ArrayList<>();
    private List<RegionalBlocks> regionalBlocks = new ArrayList<>();
    private Translations translations;

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

    public List<Integer> getCallingCodes() {
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

}
