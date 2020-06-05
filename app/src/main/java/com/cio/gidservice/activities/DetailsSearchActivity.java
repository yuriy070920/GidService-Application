package com.cio.gidservice.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cio.gidservice.R;
import com.cio.gidservice.models.Service;
import com.cio.gidservice.utils.DetailMapLoader;
import com.google.gson.GsonBuilder;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class DetailsSearchActivity extends AppCompatActivity {

    private DetailMapLoader mapLoader;
    private Service service;
    private LatLng latLng;
    private static final String TAG = "DetailsSearchActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);

        setTitle("Service details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.details_search_layout);
        loadDataInto(service, latLng, savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        service = new GsonBuilder().create().fromJson(getIntent().getStringExtra("service"), Service.class);
        latLng = new LatLng();
        latLng.setLatitude(getIntent().getDoubleExtra("lat", 0));
        latLng.setLongitude(getIntent().getDoubleExtra("lng", 0));
//        Log.d(TAG, "init: latlng=" + latLng.toString());
        mapLoader = new DetailMapLoader(this, this, latLng);
    }

    private void loadDataInto(Service service, LatLng location, Bundle savedInstanceState) {
        Glide.with(this)
                .asBitmap()
                .fitCenter()
                .load(service.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .into((ImageView) findViewById(R.id.detailsSearchImage));
        TextView name = findViewById(R.id.detailsSearchName);
        name.setText(service.getName());
        TextView description = findViewById(R.id.detailsSearchDescription);
        description.setText(service.getDescription());
        TextView price = findViewById(R.id.detailsSearchPrice);
        price.setText(String.valueOf(service.getPrice()));
        mapLoader.load(findViewById(R.id.detailsSearchLocation), savedInstanceState, () -> findViewById(R.id.detailsSearch_returnToLocation));
    }
}
