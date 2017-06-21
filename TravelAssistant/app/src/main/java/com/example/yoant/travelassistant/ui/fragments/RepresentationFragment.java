package com.example.yoant.travelassistant.ui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.yoant.travelassistant.R;
import com.example.yoant.travelassistant.adapters.recycler_view.CountriesAdapter;
import com.example.yoant.travelassistant.helper.constants.ViewConstant;
import com.example.yoant.travelassistant.models.Country;
import com.example.yoant.travelassistant.ui.activities.MainActivity;

import java.util.ArrayList;

public class RepresentationFragment extends Fragment {

    private ArrayList<Country> mListCountries = new ArrayList<>();
    private CountriesAdapter mAdapter;
    private String mEarthPart;

    public static
    @NonNull
    RepresentationFragment newInstance(String pEarthPart) {
        RepresentationFragment fragment = new RepresentationFragment();
        Bundle args = new Bundle();
        args.putString(ViewConstant.COUNTRY_EARTH_PART, pEarthPart);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_representation, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        setRetainInstance(true);

        if (getArguments() != null)
            mEarthPart = getArguments().getString(ViewConstant.COUNTRY_EARTH_PART);
        else
            mEarthPart = "all";

        setTheData();

        mAdapter = new CountriesAdapter(getActivity());
        mAdapter.addData(mListCountries);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fragment);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main3, menu);

        MenuItem menuItem = menu.findItem(R.id.toolbar_search1);
        SearchView searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(menuItem, searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setTheData() {
        ArrayList<Country> countryList = ((MainActivity) getActivity()).sendData();
        if (mEarthPart.toLowerCase().equals("all")) {
            mListCountries = countryList;
            return;
        }
        for (Country country : countryList)
            if (country.getRegion().toLowerCase().equals(mEarthPart.toLowerCase()))
                mListCountries.add(country);
    }
}
