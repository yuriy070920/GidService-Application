package com.cio.gidservice.fragments;

import android.Manifest;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cio.gidservice.R;
import com.cio.gidservice.activities.MainActivity;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.UserDao;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.User;
import com.cio.gidservice.network.OrganizationAPIManager;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.cio.gidservice.utils.FileUtils;
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

import static com.cio.gidservice.activities.AddOrganizationActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

public class SecondAddOrganizationFragment extends Fragment {

    private static final String TAG = "SecondAddOrganization";
    private View view;
    private SwipeRefreshLayout refreshLayout;

    private Organization organization;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_organization_second_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        organization = new GsonBuilder().create().fromJson(getActivity().getIntent().getStringExtra("organization"), Organization.class);

        refreshLayout = getActivity().findViewById(R.id.add_organization_swipe);

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
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            addOrganization(organization, uri);
        }
    }

    private void addOrganization(Organization organization, Uri uri) {
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
        refreshLayout.setRefreshing(true);
        apiManager.addOrganization(user.getId(), file, name, description).enqueue(new Callback<List<Organization>>() {
            @Override
            public void onResponse(Call<List<Organization>> call, Response<List<Organization>> response) {
                if (response.isSuccessful()) {
                    endRequest();
                } else {
                    System.out.println(TAG + response.raw().code() + response.raw().body().toString());
                    Toast.makeText(getContext(), "Some", Toast.LENGTH_LONG).show();
                    endRequest();
                }
            }

            @Override
            public void onFailure(Call<List<Organization>> call, Throwable t) {
                if(call.isExecuted()) {
                    endRequest();
                }else {
                    Toast.makeText(getLayoutInflater().getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void endRequest() {
        refreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), "Organization successfully added!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
