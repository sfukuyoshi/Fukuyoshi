package com.example.fukuyoshi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fukuyoshi.R;
import com.example.fukuyoshi.model.Country;

import java.util.ArrayList;

public class CountryAdapter extends ArrayAdapter<Country> {
    public CountryAdapter(Context context, ArrayList<Country> item) {
        super(context, 0, item);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Country item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_nationalize, parent, false);
        }
        // Lookup view for data population
        TextView tvCountryId = (TextView) convertView.findViewById(R.id.tv_country_id);
        TextView tvProbability = (TextView) convertView.findViewById(R.id.tv_probability);
        // Populate the data into the template view using the data object
        tvCountryId.setText(item.getCountryId());
        tvProbability.setText(String.valueOf(item.getProbability()));

        // Return the completed view to render on screen
        return convertView;
    }
}
