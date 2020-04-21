package com.cio.gidservice.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cio.gidservice.R;
import com.cio.gidservice.models.Organization;
import com.google.gson.GsonBuilder;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.cio.gidservice.activities.AddOrganizationActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

public class FirstAddOrganizationFragment extends Fragment {

    private static final int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    private View view;
    ImageView imageLoader;

    private Context context;
    private Organization organization;

    public FirstAddOrganizationFragment(Context context, Organization organization) {
        this.context = context;
        this.organization = organization;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_org_first_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageLoader = view.findViewById(R.id.imageLoadButton);
        imageLoader.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
            else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Select picture.."),
                        PICK_IMAGE_FROM_GALLERY_REQUEST
                );
            }
        });
        view.findViewById(R.id.firstRegPageNext).setOnClickListener(v -> {
            if(setOrganization()){
                String organizationString = new GsonBuilder().create().toJson(organization);
                getActivity().getIntent().putExtra("organization", organizationString);
                getParentFragmentManager().beginTransaction().replace(R.id.add_organization_frame, new SecondAddOrganizationFragment()).commit();
            } else {
                Toast.makeText(getContext(), "Not all fields are filled", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean setOrganization() {
        try{
            if(((EditText)view.findViewById(R.id.name_organization_input)).getText() != null &&
               ((EditText)view.findViewById(R.id.name_organization_input)).getText() != null)
            {
                String name = ((EditText)view.findViewById(R.id.name_organization_input)).getText().toString();
                organization.setName(name);
                String description = ((EditText)view.findViewById(R.id.description_organization_input)).getText().toString();
                organization.setDescription(description);
                return true;
            } else {
                return false;
            }
        }catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Glide.with(this)
                    .asBitmap()
                    .fitCenter()
                    .load(data.getData())
                    .placeholder(R.drawable.photo_picker)
                    .into((ImageView) view.findViewById(R.id.imageLoadButton));
            Uri uri = data.getData();
            organization = new Organization();
            organization.setImageUrl(uri.toString());
            Log.d(TAG, "onActivityResult: " + organization.getImageUrl());
            //requestRead(uri);
        }
    }

    public Organization getOrganization() {
        return organization;
    }
}
