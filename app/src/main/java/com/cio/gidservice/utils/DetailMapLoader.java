package com.cio.gidservice.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;

public class DetailMapLoader extends MapLoader {
    public DetailMapLoader(Activity activity, Context context, LatLng latLng) {
        super(activity, context, latLng);
    }

    @Override
    public void load(MapView view, Bundle savedInstanceState) {
        super.load(view, savedInstanceState);
    }

    public void load(MapView view, Bundle savedInstanceState, MapAdditionalClick click) {
        super.load(view, savedInstanceState);
        click.click().setOnClickListener(v -> animateCameraToTarget(new LatLng(latLng.getLatitude(), latLng.getLongitude())));
    }

    private void animateCameraToTarget(LatLng target) {
        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(new CameraPosition.Builder()
                        .target(target)
                        .zoom(15)
                        .build()));
    }

}
