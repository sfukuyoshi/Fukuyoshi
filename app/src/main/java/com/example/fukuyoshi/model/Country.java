package com.example.fukuyoshi.model;

import java.io.Serializable;

public class Country implements Serializable {

    private String countryId;
    private double probability;

    public void setData(String countryId, double probability) {
        this.countryId = countryId;
        this.probability = probability;
    }

    public String getCountryId(){
        return countryId;
    }

    public double getProbability(){
        return probability;
    }
}