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
import com.example.yoant.travelassistant.helper.constants.ViewConstant;
import com.example.yoant.travelassistant.helper.svg_loader.SvgDecoder;
import com.example.yoant.travelassistant.helper.svg_loader.SvgDrawableTranscoder;
import com.example.yoant.travelassistant.helper.svg_loader.SvgSoftwareLayerSetter;
import com.example.yoant.travelassistant.models.Country;
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
    private Context mContext;
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
    private Country fakeModel = new Country("", "", "", "", null, "", "", "", "", 0, 0, 0, null, "", null, null, null, null, null, null, null);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        ButterKnife.bind(this);
        mCode = getIntent().getStringExtra(ViewConstant.COUNTRY_DETAIL_CODE).toLowerCase();
        mContext = this;

        new GetJsonAsync().execute(mCode);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra(ViewConstant.COUNTRY_DETAIL_COUNTRY));
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
        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(mContext)
                .using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .error(R.drawable.ic_error)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(Uri.parse(mCountry.getFlag()))
                .into((ImageView) findViewById(R.id.country_imageview_flag_source));
        name.setText(mCountry.getName());
        String regionText = mCountry.getRegion() + (mCountry.getSubregion().isEmpty() ? "" : ", " + mCountry.getSubregion());
        region.setText(regionText);
        capital.setText(mCountry.getCapital().isEmpty() ? ViewConstant.DETAILED_ITEM_NO_CAPITAL : mCountry.getCapital());
        String populationText = mCountry.getPopulation() + "";
        population.setText(populationText);
        String areaText = mCountry.getArea() + "";
        area.setText(areaText);

        for (int i = 0; i < mCountry.getLanguages().size(); i++) {
            if (i > 0)
                builder.append(ViewConstant.DETAILED_ITEM_SEPARATOR);
            builder.append(mCountry.getLanguages().get(i).getName());
        }
        languages.setText(builder.toString());

        builder = new StringBuilder();
        for (int i = 0; i < mCountry.getCurrencies().size(); i++) {
            if (i > 0)
                builder.append(ViewConstant.DETAILED_ITEM_SEPARATOR);
            builder.append(mCountry.getCurrencies().get(i).toString());
        }
        currencies.setText(builder.toString());

        builder = new StringBuilder();
        for (int i = 0; i < mCountry.getTopLevelDomain().size(); i++) {
            if (i > 0)
                builder.append(ViewConstant.DETAILED_ITEM_SEPARATOR);
            builder.append(mCountry.getTopLevelDomain().get(i));
        }
        domains.setText(builder.toString());

        builder = new StringBuilder();
        for (int i = 0; i < mCountry.getCallingCodes().size(); i++) {
            if (i > 0)
                builder.append(ViewConstant.DETAILED_ITEM_SEPARATOR);
            builder.append(mCountry.getCallingCodes().get(i));
        }
        callingCodes.setText(builder.toString().isEmpty() ? ViewConstant.DETAILED_ITEM_NO_CODE : builder.toString());
    }

    private class GetJsonAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String endpoint = "https://restcountries.eu/rest/v2/alpha/";
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(endpoint + strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder buffer = new StringBuilder();
                String line;

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
            if (mCountry == null)
                mCountry = getIntent().getExtras().getParcelable(ViewConstant.RESTORE_OBJECT_INTENT);
            setupView();
        }
    }
}
