package com.example.fukuyoshi.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Nationalize {

    private String name;
    private ArrayList<Country> countryList;

    public Nationalize (String response, NationalizeInterface nationalizeInterface) {
        countryList = new ArrayList<>();
        try {
            JSONObject responseObject = new JSONObject(response);
            name = responseObject.getString("name");

            JSONArray results = responseObject.getJSONArray("country");
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                Country country = new Country();
                country.setData(obj.getString("country_id"), obj.getDouble("probability"));
                countryList.add(country);
            }
            nationalizeInterface.onSuccess(this);
        } catch (JSONException e) {
            nationalizeInterface.onError(e.toString());
        }
    }
    public interface NationalizeInterface {
        void onSuccess(Nationalize item);
        void onError(String message);
    }

    public String getName(){
        return name;
    }

    public ArrayList<Country> getCountryList(){
        return countryList;
    }
}