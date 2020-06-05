package com.cio.gidservice.utils;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.cio.gidservice.R;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Layer;

public class MapLoader implements OnMapReadyCallback {

    private static final String TAG = "OrganizationFragment";
    private static final String LAYER_ID = "LAYER_ID";
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";

    private Activity openActivity;
    private MapView mapView;
    private Context context;
    protected MapboxMap mapboxMap;
    protected LatLng latLng;
    private PermissionsManager permissionsManager;
    private Layer droppedMarkerLayer;


    public MapLoader(Activity activity, Context context, LatLng latLng) {
        Mapbox.getInstance(context, activity.getString(R.string.mapbox_access_token));
        Mapbox.setAccessToken(activity.getString(R.string.mapbox_access_token));
        openActivity = activity;
        this.context = context;
        this.latLng = latLng;
    }

    public void load(MapView view, Bundle savedInstanceState) {
        this.mapView = view;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap){
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS ,style -> {
            IconFactory iconFactory = IconFactory.getInstance(context);
            Icon icon = iconFactory.fromResource(R.drawable.blue_marker);

// Add the marker to the map
            mapboxMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(icon));
        });
    }

    public void onResume() {
        mapView.onResume();
    }

    @SuppressWarnings( {"MissingPermission"})
    public void onStart() {
        mapView.onStart();
    }

    public void onStop() {
        mapView.onStop();
    }

    public void onPause() {
        mapView.onPause();
    }

    public void onLowMemory() {
        mapView.onLowMemory();
    }

    public void onDestroy() {
        mapView.onDestroy();
    }

    public void onSaveInstanceState(Bundle outState) {
        mapView.onSaveInstanceState(outState);
    }

    public void onDestroyView() {
        mapView.onDestroy();
    }
}
