package com.example.yoant.travelassistant.ui.activities;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.example.yoant.travelassistant.R;
import com.example.yoant.travelassistant.svg_loader.SvgDecoder;
import com.example.yoant.travelassistant.svg_loader.SvgDrawableTranscoder;
import com.example.yoant.travelassistant.svg_loader.SvgSoftwareLayerSetter;

import java.io.InputStream;

public class CountryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, this), InputStream.class)
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
                .load(Uri.parse("https://restcountries.eu/data/blr.svg"))
                .into((ImageView)findViewById(R.id.country_imageview_flag_source));
    }
}
