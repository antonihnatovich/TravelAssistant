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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.yoant.travelassistant.R;
import com.example.yoant.travelassistant.adapters.recycler_view.CountriesAdapter;
import com.example.yoant.travelassistant.models.Country;
import com.example.yoant.travelassistant.service.CountryService;
import com.example.yoant.travelassistant.service.ServiceFactory;
import com.example.yoant.travelassistant.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RepresentationFragment extends Fragment {

    private static final String RESTORE_KEY = "countries";
    private static final String EARTH_PART = "part";

    private ArrayList<Country> mListCountries = new ArrayList<>();
    private CountriesAdapter mAdapter;
    private String mEarthPart;
    private RecyclerView mRecyclerView;

    public static final @NonNull RepresentationFragment newInstance(String pEarthPart) {
        RepresentationFragment fragment = new RepresentationFragment();
        Bundle args = new Bundle();
        args.putString(EARTH_PART, pEarthPart);
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
        setRetainInstance(true);

        if (getArguments() != null)
            mEarthPart = getArguments().getString(EARTH_PART);
        else
            mEarthPart = "all";

        if (savedInstanceState != null && !savedInstanceState.getParcelableArrayList(RESTORE_KEY).isEmpty())
            savedInstanceState.getParcelableArrayList(RESTORE_KEY);
        else
            getCountriesListByRegion();

        mAdapter = new CountriesAdapter(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fragment);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState){
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addData(mListCountries);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(RESTORE_KEY, mListCountries);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void getCountriesListByRegion() {
        CountryService service = ServiceFactory.createRetrofitService(CountryService.class, CountryService.SERVICE_ENDPOINT);
        if (!mEarthPart.equals("all")){
            service.getCountriesByRegion(mEarthPart.toLowerCase())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Country>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("Country Demo", e.getMessage());
                        }

                        @Override
                        public void onNext(List<Country> countries) {
                            mListCountries = (ArrayList) countries;
                            mAdapter.addData(countries);
                        }
                    });
    }
        else {
            service.getAllCountries()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Country>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("Country Demo", e.getMessage());
                        }

                        @Override
                        public void onNext(List<Country> countries) {
                            mListCountries = (ArrayList) countries;
                            mAdapter.addData(countries);
                        }
                    });
        }
    }
}
