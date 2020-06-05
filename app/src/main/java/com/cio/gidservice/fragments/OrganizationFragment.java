package com.cio.gidservice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cio.gidservice.R;
import com.cio.gidservice.adapters.ServiceRecyclerAdapter;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.utils.DetailMapLoader;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.style.layers.Layer;

public class OrganizationFragment extends Fragment{

    private static final String TAG = "OrganizationFragment";

    View view;
    private Organization organization;
    private ServiceRecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private Layer droppedMarkerLayer;
    private DetailMapLoader mapLoader;


    public OrganizationFragment(Context context, Organization organization) {
        this.context = context;
        this.organization = organization;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mapLoader = new DetailMapLoader(getActivity(), getContext(), new LatLng(organization.getLat(), organization.getLng()));
        view = inflater.inflate(R.layout.organization_description_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapLoader.load(view.findViewById(R.id.mapView_description), savedInstanceState, () -> view.findViewById(R.id.return_to_location));

        //Setting name of organization
        TextView name = view.findViewById(R.id.name_organization_fragment);
        name.setText(organization.getName());

        //Setting description of organization
        TextView description = view.findViewById(R.id.description_organization_fragment);
        description.setText(organization.getDescription());

        //Setting rating of organization
        TextView rating = view.findViewById(R.id.rating_organization_fragment);
        rating.setText(String.valueOf(organization.getRating()));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapLoader.onResume();
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    public void onStart() {
        super.onStart();
        mapLoader.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapLoader.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapLoader.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapLoader.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapLoader.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapLoader.onDestroyView();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapLoader.onLowMemory();
    }
}
