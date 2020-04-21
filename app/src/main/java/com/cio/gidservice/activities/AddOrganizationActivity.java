package com.cio.gidservice.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.cio.gidservice.R;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.AppDatabase;
import com.cio.gidservice.dao.OrganizationDao;
import com.cio.gidservice.fragments.FirstAddOrganizationFragment;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.cio.gidservice.network.UserAPIManager;
import com.cio.gidservice.utils.FileUtils;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class AddOrganizationActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 100;
    private static final int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    EditText name_et;
    EditText description_et;
    EditText imageUrl_et;

    FragmentManager fragmentManager;

    private static Organization organization;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_organization_layout);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.add_organization_frame, new FirstAddOrganizationFragment(this, organization)).commit();

        //Setting up the button for returning to the previous layout
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setting header name
        setTitle("Add organization 1/2");

        //Getting fields with corresponding text
        name_et = findViewById(R.id.name_organization_input);
        description_et = findViewById(R.id.description_organization_input);

        /*ImageButton save = findViewById(R.id.save_organization_but);
        save.setOnClickListener(v -> {
            Organization organization = new Organization(name_et.getText().toString(), description_et.getText().toString(), 0F, null, imageUrl_et.getText().toString());
            if(save(organization)) {
                Toast.makeText(this, "Saved successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });*/
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

    private boolean save(Organization organization) {
        try {
            AppDatabase db = App.getInstance().getDatabase();
            OrganizationDao orgDB = db.organizationDao();
            orgDB.insert(organization);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * requestPermissions and do something
     *
     */
    public void requestRead(Uri uri) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            uploadFile(uri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                }
                return;
            }
        }
    }

    private void uploadFile(Uri uri) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        File originalFile = FileUtils.getFile(this, uri);
        RequestBody filePart = FormBody.create(
                MediaType.parse(getContentResolver().getType(uri)),
                originalFile
        );

        //RequestBody body = RequestBody.create(MultipartBody.FORM, );

        MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);


        UserAPIManager userManager = retrofit.create(UserAPIManager.class);

        /*Call<ResponseBody> call = userManager.uploadImage(file);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(AddOrganizationActivity.this, "Ohh, you upload your image to the server!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AddOrganizationActivity.this, "Oops :( Something went wrong.", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    public void loadImageInto(View view) {
        /*Glide.with(view)
                .load(organization.uri)
                .asBitmap()
                .*/
    }

}
