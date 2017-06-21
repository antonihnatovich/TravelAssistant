package com.example.yoant.travelassistant.adapters.recycler_view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.example.yoant.travelassistant.ui.activities.CountryActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class CountriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Country> mCountries;
    private List<Country> mCountriesFiltered;
    private Context mContext;

    private Intent intent;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public CountriesAdapter(Context pContext) {
        super();
        mContext = pContext;
        mCountries = new ArrayList<>();
        intent = new Intent(mContext, CountryActivity.class);
        mCountriesFiltered = new ArrayList<>();
        requestBuilder = Glide.with(mContext)
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
    }

    public void addData(List<Country> country) {
        for (Country c : country) {
            mCountries.add(c);
            mCountriesFiltered.add(c);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_representation, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder pHolder, int position) {
        CountryViewHolder mHolder = (CountryViewHolder) pHolder;
        final Country country = mCountriesFiltered.get(position);
        //Log.d("Loading image : = ", country.getFlag());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(Uri.parse(country.getFlag()))
                .into(mHolder.imageView);
        mHolder.textView.setText(country.getName());
        mHolder.textViewCapital.setText(country.getCapital());
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toPut = country.getAlpha3Code().isEmpty() ? country.getAlpha2Code() : country.getAlpha3Code();
                intent.putExtra(ViewConstant.COUNTRY_DETAIL_CODE, toPut);
                intent.putExtra(ViewConstant.COUNTRY_DETAIL_COUNTRY, country.getName());
                intent.putExtra(ViewConstant.RESTORE_OBJECT_INTENT, country);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCountriesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filter = charSequence.toString().toLowerCase();
                if (filter.isEmpty())
                    mCountriesFiltered = mCountries;
                else {
                    ArrayList<Country> filteredList = new ArrayList<>();
                    for (Country country : mCountries)
                        if (country.getName().toLowerCase().contains(filter) || country.getCapital().toLowerCase().contains(filter))
                            filteredList.add(country);
                    mCountriesFiltered = filteredList;
                }
                FilterResults filteredResults = new FilterResults();
                filteredResults.values = mCountriesFiltered;
                return filteredResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mCountriesFiltered = (ArrayList<Country>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private class CountryViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView textViewCapital;
        ImageView imageView;

        CountryViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.country_name);
            textViewCapital = (TextView) itemView.findViewById(R.id.country_capital);
            imageView = (ImageView) itemView.findViewById(R.id.country_image);
        }
    }
}
