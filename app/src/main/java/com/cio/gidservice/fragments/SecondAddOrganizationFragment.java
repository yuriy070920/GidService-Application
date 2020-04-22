package com.cio.gidservice.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cio.gidservice.R;
import com.cio.gidservice.activities.MainActivity;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.UserDao;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.User;
import com.cio.gidservice.network.OrganizationAPIManager;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.cio.gidservice.utils.FileUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondAddOrganizationFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "SecondAddOrganization";
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST = 100;
    private View view;

    private Organization organization;
    private MapView mapView;
    private boolean mLocationPermissionGranted;
    private GoogleMap mMap;
    private MapView mMapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_organization_second_fragment, container, false);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(Map -> {
            mMap = Map;

            // For showing a move to my location button
            mMap.setMyLocationEnabled(true);

            // For dropping a marker at a point on the Map
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        organization = new GsonBuilder().create().fromJson(getActivity().getIntent().getStringExtra("organization"), Organization.class);

        System.out.println(organization.getDescription());

        ImageButton createOrganization = view.findViewById(R.id.finish_button);
        createOrganization.setOnClickListener(v -> {
            addOrganization(organization, Uri.parse(organization.getImageUrl()));
        });
    }

    public void requestRead() {
        Uri uri = Uri.parse(organization.getImageUrl());
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        } else {
            addOrganization(organization, uri);
        }
    }

    private void addOrganization(Organization organization, Uri uri) {
        ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Uploading.\nPlease wait...", true);
        UserDao userDao = App.getInstance().getDatabase().userDao();
        User user = userDao.getUser();
        OrganizationAPIManager apiManager = RetrofitClientInstance.getRetrofitInstance().create(OrganizationAPIManager.class);
        File originalFile = FileUtils.getFile(getContext(), uri);
        RequestBody filePart = FormBody.create(
                MediaType.parse(getContext().getContentResolver().getType(uri)),
                originalFile
        );
        MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);
        RequestBody name = RequestBody.create(MultipartBody.FORM, organization.getName());
        RequestBody description = RequestBody.create(MultipartBody.FORM, organization.getDescription());
        apiManager.addOrganization(user.getId(), file, name, description).enqueue(new Callback<List<Organization>>() {
            @Override
            public void onResponse(Call<List<Organization>> call, Response<List<Organization>> response) {
                if (response.isSuccessful()) {
                    endRequest(dialog);
                } else {
                    System.out.println(TAG + response.raw().code() + response.raw().body().toString());
                    Toast.makeText(getContext(), "Some", Toast.LENGTH_LONG).show();
                    endRequest(dialog);
                }
            }

            @Override
            public void onFailure(Call<List<Organization>> call, Throwable t) {
                if(call.isExecuted()) {
                    endRequest(dialog);
                }else {
                    Toast.makeText(getLayoutInflater().getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else{
                getActivity().onBackPressed();
            }
        }
    }

    private void endRequest(AlertDialog dialogFragment) {
        dialogFragment.dismiss();
        Toast.makeText(getContext(), "Organization successfully added!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(0, 0);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
