package com.example.yoant.travelassistant.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yoant.travelassistant.R;
import com.example.yoant.travelassistant.adapters.CountriesAdapter;
import com.example.yoant.travelassistant.models.Country;
import com.example.yoant.travelassistant.service.CountryService;
import com.example.yoant.travelassistant.service.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RepresentationFragment extends Fragment {

    private static final String RESTORE_KEY = "countries";

    private ArrayList<Country> mListCountries = new ArrayList<>();
    private CountriesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_representation, container, false);

        if(savedInstanceState != null)
            savedInstanceState.getParcelableArrayList(RESTORE_KEY);
        else
            getCountriesList();

        setRetainInstance(true);

        mAdapter = new CountriesAdapter(getActivity());
        RecyclerView mRecyclerView; mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_fragment);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addData(mListCountries);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(RESTORE_KEY, mListCountries);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void getCountriesList() {
        CountryService service = ServiceFactory.createRetrofitService(CountryService.class, CountryService.SERVICE_ENDPOINT);
        service.getCountriesByRegion("europe")
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
