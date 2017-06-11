package com.example.yoant.travelassistant.models;

import java.util.ArrayList;
import java.util.List;

class RegionalBlocks {

    private String acronym;
    private String name;
    private List<String> otherAcronyms = new ArrayList<>();
    private List<String> otherNames = new ArrayList<>();

    public String getAcronym() {
        return acronym;
    }

    public String getName() {
        return name;
    }

    public List<String> getOtherAcronyms() {
        return otherAcronyms;
    }

    public List<String> getOtherNames() {
        return otherNames;
    }
}