package com.example.yoant.travelassistant.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yoant.travelassistant.R;
import com.example.yoant.travelassistant.models.Country;

import java.util.ArrayList;
import java.util.List;


public class CountriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Country> mCountries;

    public CountriesAdapter(ArrayList<Country> pCountries) {
        mCountries = pCountries;
    }

    public CountriesAdapter() {
        super();
        mCountries = new ArrayList<>();
    }

    public void addData(List<Country> country) {
        for (Country c : country)
            mCountries.add(c);
        notifyDataSetChanged();
    }

    public void clear() {
        mCountries.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_representation, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder pHolder, int position) {
        CountryViewHolder hholder = (CountryViewHolder) pHolder;
        Country country = mCountries.get(position);

        hholder.textView.setText(country.getName());
        hholder.textViewCapital.setText(country.getCapital());
    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public TextView textViewCapital;

        public CountryViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.country_name);
            textViewCapital = (TextView) itemView.findViewById(R.id.country_capital);
        }
    }
}
