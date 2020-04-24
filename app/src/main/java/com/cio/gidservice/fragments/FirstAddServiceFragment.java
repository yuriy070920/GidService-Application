package com.cio.gidservice.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.cio.gidservice.R;
import com.cio.gidservice.activities.MainActivity;
import com.cio.gidservice.dialogs.UploadingDialog;
import com.cio.gidservice.models.Service;
import com.cio.gidservice.network.OrganizationAPIManager;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.cio.gidservice.utils.FileUtils;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.cio.gidservice.activities.AddOrganizationActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

public class FirstAddServiceFragment extends Fragment {

    private static final int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    private View view;
    private EditText name_et;
    private EditText description_et;
    private EditText cost_et;
    private Service service;
    private SwipeRefreshLayout refreshLayout;

    public FirstAddServiceFragment() {
        service = new Service();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_service_first_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name_et = view.findViewById(R.id.service_name_input);
        description_et = view.findViewById(R.id.service_description_input);
        cost_et = view.findViewById(R.id.service_cost_input);

        ImageView imageLoader = view.findViewById(R.id.service_image_input);
        imageLoader.setOnClickListener(v -> {
            //If not have access to external storage
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Select picture.."),
                        PICK_IMAGE_FROM_GALLERY_REQUEST
                );
            }
        });

        ImageButton save = view.findViewById(R.id.save_service_but);
        save.setOnClickListener(v -> {
            startUpload();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            service.setImageUrl(data.getData().toString());
            Glide.with(this)
                    .asBitmap()
                    .fitCenter()
                    .load(Uri.parse(service.getImageUrl()))
                    .placeholder(R.drawable.placeholder_image)
                    .into((ImageView) view.findViewById(R.id.service_image_input));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void checkPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(
                            Intent.createChooser(intent, "Select picture.."),
                            PICK_IMAGE_FROM_GALLERY_REQUEST
                    );
                }
                break;
            }
        }
    }

    private void startUpload() {

        UploadingDialog dialog = new UploadingDialog(getActivity());
        dialog.startLoading();
        Long orgId = getActivity().getIntent().getLongExtra("organization_id", 1);

        if(name_et.getText() == null ||
           description_et.getText() == null ||
           cost_et.getText() == null) {
            Toast.makeText(getContext(), "Not all fields are fill!", Toast.LENGTH_LONG).show();
            return;
        } else {
            System.out.println(name_et.getText().toString());
            System.out.println(description_et.getText().toString());
            System.out.println(cost_et.getText().toString());
        }

        service.setName(name_et.getText().toString());
        service.setDescription(description_et.getText().toString());
        service.setPrice(Float.valueOf(cost_et.getText().toString()));
        service.setId_organization(orgId);


        OrganizationAPIManager apiManager = RetrofitClientInstance.getRetrofitInstance().create(OrganizationAPIManager.class);

        RequestBody name = RequestBody.create(MultipartBody.FORM, service.getName());
        RequestBody description = RequestBody.create(MultipartBody.FORM, service.getDescription());
        RequestBody cost = RequestBody.create(MultipartBody.FORM, String.valueOf(service.getPrice()));
        File originalFile = FileUtils.getFile(getContext(), Uri.parse(service.getImageUrl()));
        RequestBody filePart = FormBody.create(
                MediaType.parse(getContext().getContentResolver().getType(Uri.parse(service.getImageUrl()))),
                originalFile
        );
        MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);
        apiManager.addService(orgId, file, name, description, cost).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Service successfully added!", Toast.LENGTH_LONG).show();
                    finishUpload(dialog);
                }
                else{
                    Toast.makeText(getContext(), "Service cannot be added!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Toast.makeText(getContext(), "Check your internet connection!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    private void finishUpload(Dialog dialog) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        dialog.dismiss();
        startActivity(intent);
    }
}
