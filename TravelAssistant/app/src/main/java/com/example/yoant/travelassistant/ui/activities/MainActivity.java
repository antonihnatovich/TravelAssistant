package com.example.yoant.travelassistant.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.yoant.travelassistant.R;
import com.example.yoant.travelassistant.adapters.CountriesAdapter;
import com.example.yoant.travelassistant.models.Country;
import com.example.yoant.travelassistant.service.CountryService;
import com.example.yoant.travelassistant.service.ServiceFactory;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        final CountriesAdapter adapter = new CountriesAdapter();
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);

        Button bClear = (Button) findViewById(R.id.button_clear);
        Button bFetch = (Button) findViewById(R.id.button_fetch);
        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
            }
        });

        bFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            public void onNext(List<Country> country) {
                                adapter.addData(country);
                            }
                        });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
