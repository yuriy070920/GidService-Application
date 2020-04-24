package com.cio.gidservice.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cio.gidservice.R;
import com.cio.gidservice.adapters.ServiceRecyclerAdapter;
import com.cio.gidservice.models.Organization;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import static com.cio.gidservice.R.id.mapView_description;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

public class OrganizationFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "OrganizationFragment";
    private static final String LAYER_ID = "LAYER_ID";
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";

    View view;
    private Organization organization;
    private ServiceRecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private Layer droppedMarkerLayer;


    public OrganizationFragment(Context context, Organization organization) {
        this.context = context;
        this.organization = organization;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Mapbox.getInstance(getContext(), String.valueOf(R.string.access_token));
        view = inflater.inflate(R.layout.organization_description_fragment, container, false);

        mapView = view.findViewById(mapView_description);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //Setting name of organization
        TextView name = view.findViewById(R.id.name_organization_fragment);
        name.setText(organization.getName());

        //Setting description of organization
        TextView description = view.findViewById(R.id.description_organization_fragment);
        description.setText(organization.getDescription());

        //Setting rating of organization
        TextView rating = view.findViewById(R.id.rating_organization_fragment);
        rating.setText(String.valueOf(organization.getRating()));

        return view;
    }

    private void animateCameraToTarget(LatLng target) {
        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(new CameraPosition.Builder()
                        .target(target)
                        .zoom(15)
                        .build()));
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        animateCameraToTarget(new LatLng(organization.getLat(), organization.getLng()));
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style1 -> {
            initDroppedMarker(style1);
            if (style1.getLayer(DROPPED_MARKER_LAYER_ID) != null) {
                GeoJsonSource source = style1.getSourceAs("dropped-marker-source-id");
                if (source != null) {
                    source.setGeoJson(Point.fromLngLat(organization.getLng(), organization.getLat()));
                }
                droppedMarkerLayer = style1.getLayer(DROPPED_MARKER_LAYER_ID);
                if (droppedMarkerLayer != null) {
                    droppedMarkerLayer.setProperties(visibility(VISIBLE));
                }
            }
        });
        Button button = view.findViewById(R.id.return_to_location);
        button.setOnClickListener(v -> {
            animateCameraToTarget(new LatLng(organization.getLat(), organization.getLng()));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void initDroppedMarker(@NonNull Style loadedMapStyle) {
// Add the marker image to map
        loadedMapStyle.addImage("dropped-icon-image", BitmapFactory.decodeResource(
                getResources(), R.drawable.blue_marker));
        loadedMapStyle.addSource(new GeoJsonSource("dropped-marker-source-id"));
        loadedMapStyle.addLayer(new SymbolLayer(DROPPED_MARKER_LAYER_ID,
                "dropped-marker-source-id").withProperties(
                iconImage("dropped-icon-image"),
                visibility(VISIBLE),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        ));
    }
}
