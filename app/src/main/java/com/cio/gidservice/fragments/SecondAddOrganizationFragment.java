package com.cio.gidservice.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.cio.gidservice.dialogs.UploadingDialog;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.User;
import com.cio.gidservice.network.OrganizationAPIManager;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.cio.gidservice.utils.FileUtils;
import com.google.gson.GsonBuilder;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

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
    private MapboxMap mapboxMap;
    private Button selectLocationButton;
    private PermissionsManager permissionsManager;
    private ImageView hoveringMarker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(getContext(), getString(R.string.access_token));
        view = inflater.inflate(R.layout.add_organization_second_fragment, container, false);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        organization = new GsonBuilder().create().fromJson(getActivity().getIntent().getStringExtra("organization"), Organization.class);

        System.out.println(organization.getDescription());

        Button createOrganization = view.findViewById(R.id.finish_button);
        createOrganization.setOnClickListener(v -> {
            LatLng latLng = mapboxMap.getCameraPosition().target;
            addOrganization(organization, Uri.parse(organization.getImageUrl()), latLng);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getParentFragmentManager().beginTransaction().replace(R.id.add_organization_frame, new FirstAddOrganizationFragment(getContext(), organization));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addOrganization(Organization organization, Uri uri, LatLng latLng) {
        UploadingDialog dialog = new UploadingDialog(getActivity());
        dialog.startLoading();
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
        apiManager.addOrganization(user.getId(), file, name, description, latLng.getLatitude(), latLng.getLongitude()).enqueue(new Callback<List<Organization>>() {
            @Override
            public void onResponse(Call<List<Organization>> call, Response<List<Organization>> response) {
                if (response.isSuccessful()) {
                    endRequest(dialog);
                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Organization>> call, Throwable t) {
                if(!call.isCanceled())
                    endRequest(dialog);
                Toast.makeText(getLayoutInflater().getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
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

    private void endRequest(Dialog dialogFragment) {
        dialogFragment.dismiss();
        Toast.makeText(getContext(), "Organization successfully added!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
            // When user is still picking a location, we hover a marker above the mapboxMap in the center.
            // This is done by using an image view with the default marker found in the SDK. You can
            // swap out for your own marker image, just make sure it matches up with the dropped marker.
            hoveringMarker = new ImageView(getContext());
            hoveringMarker.setImageResource(R.drawable.red_marker);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            hoveringMarker.setLayoutParams(params);
            mapView.addView(hoveringMarker);

            // Button for user to drop marker or to pick marker back up.
            selectLocationButton = view.findViewById(R.id.finish_button);
            selectLocationButton.setOnClickListener(view -> {
                final LatLng mapTargetLatLng = mapboxMap.getCameraPosition().target;
                Toast.makeText(getContext(), mapTargetLatLng.toString(), Toast.LENGTH_LONG).show();
                // Use the map camera target's coordinates to make a reverse geocoding search
                addOrganization(organization, Uri.parse(organization.getImageUrl()), mapTargetLatLng);
            });
        });
    }

}
