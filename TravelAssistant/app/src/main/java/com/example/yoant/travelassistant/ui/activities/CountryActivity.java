package com.example.yoant.travelassistant.ui.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.yoant.travelassistant.R;
import com.example.yoant.travelassistant.models.Country;
import com.example.yoant.travelassistant.models.Currencies;
import com.example.yoant.travelassistant.models.Languages;
import com.example.yoant.travelassistant.svg_loader.SvgDecoder;
import com.example.yoant.travelassistant.svg_loader.SvgDrawableTranscoder;
import com.example.yoant.travelassistant.svg_loader.SvgSoftwareLayerSetter;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryActivity extends AppCompatActivity {

    private String mCode;
    private Country mCountry;
    private Context context;
    @BindView(R.id.country_textview_country_name)
    TextView name;
    @BindView(R.id.country_textview_country_region)
    TextView region;
    @BindView(R.id.country_textview_country_population)
    TextView population;
    @BindView(R.id.country_textview_country_capital)
    TextView capital;
    @BindView(R.id.country_textview_country_area)
    TextView area;
    @BindView(R.id.country_textview_country_languages)
    TextView languages;
    @BindView(R.id.country_textview_country_currencies)
    TextView currencies;
    @BindView(R.id.country_textview_country_domains)
    TextView domains;
    @BindView(R.id.country_textview_country_calling)
    TextView callingCodes;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private Country psseudo = new Country("", "", "", "", null, "", "", "", "", 0, 0, 0, null, "", null, null, null, null, null, null, null);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        ButterKnife.bind(this);
        mCode = getIntent().getStringExtra("code").toLowerCase();
        context = this;

        new GetJsonAsync().execute(mCode);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra("country"));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        Toolbar toolbar = (Toolbar) findViewById(R.id.country_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupView() {
        StringBuilder builder = new StringBuilder();
        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .error(R.drawable.ic_error)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(Uri.parse(mCountry.getFlag()))
                .into((ImageView) findViewById(R.id.country_imageview_flag_source));
        name.setText(mCountry.getName());
        region.setText(mCountry.getRegion() + (mCountry.getSubregion().isEmpty()? "" : ", " + mCountry.getSubregion()));
        capital.setText(mCountry.getCapital().isEmpty()? "No capital for this country" : mCountry.getCapital());
        population.setText("" + mCountry.getPopulation());
        area.setText("" + mCountry.getArea());

        for(int i = 0; i < mCountry.getLanguages().size(); i++){
            if(i > 0)
                builder.append(" | ");
            builder.append(mCountry.getLanguages().get(i).getName());
        }
        languages.setText(builder.toString());

        builder = new StringBuilder();
        for(int i = 0; i < mCountry.getCurrencies().size(); i++){
            if(i > 0)
                builder.append(" | ");
            builder.append(mCountry.getCurrencies().get(i).toString());
        }
        currencies.setText(builder.toString());

        builder = new StringBuilder();
        for(int i = 0; i < mCountry.getTopLevelDomain().size(); i++){
            if(i > 0)
                builder.append(" | ");
            builder.append(mCountry.getTopLevelDomain().get(i));
        }
        domains.setText(builder.toString());

        builder = new StringBuilder();
        for(int i = 0; i < mCountry.getCallingCodes().size(); i++){
            if(i > 0)
                builder.append(" | ");
            builder.append(mCountry.getCallingCodes().get(i));
        }
        callingCodes.setText(builder.toString().isEmpty()? "No codes" : builder.toString());
    }

    private class GetJsonAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String u = "https://restcountries.eu/rest/v2/alpha/";
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(u + strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                    buffer.append(line);
                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Gson gson = new Gson();
            mCountry = gson.fromJson(result, Country.class);
            setupView();
        }
    }
}
