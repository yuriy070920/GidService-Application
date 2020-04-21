package com.cio.gidservice.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.cio.gidservice.R;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.AppDatabase;
import com.cio.gidservice.dao.ServiceDao;
import com.cio.gidservice.fragments.FirstAddServiceFragment;
import com.cio.gidservice.models.Service;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.cio.gidservice.network.UserAPIManager;
import com.cio.gidservice.utils.FileUtils;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;


public class AddServiceActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 100;
    private static final int PICK_IMAGE_FROM_GALLERY_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    private Service service;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service_layout);

        getSupportFragmentManager().beginTransaction().replace(R.id.serviceFrame, new FirstAddServiceFragment()).commit();

        //Setting header name
        setTitle("Add service");

        //Setting up button for returning to the previous layout
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            /*if(save(service)) {
                Toast.makeText(this, "Saved successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }*/

        //});
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

    private boolean save(Service service) {
        try{
            AppDatabase db = App.getInstance().getDatabase();
            ServiceDao dao = db.serviceDao();
            dao.insert(service);

            Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

            File originalFile = FileUtils.getFile(this, Uri.parse(service.getImageUrl()));
            RequestBody filePart = FormBody.create(
                    MediaType.parse(getContentResolver().getType(Uri.parse(service.getImageUrl()))),
                    originalFile
            );

            //RequestBody body = RequestBody.create(MultipartBody.FORM, );

            MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalFile.getName(), filePart);


            UserAPIManager userManager = retrofit.create(UserAPIManager.class);

            /*Call<ResponseBody> call = userManager.uploadImage(file);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(AddServiceActivity.this, "Ohh, you upload your image to the server!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(AddServiceActivity.this, "Oops :( Something went wrong.", Toast.LENGTH_LONG).show();
                }
            });*/
            return true;
        } catch (Exception e) {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Glide.with(this)
                    .asBitmap()
                    .fitCenter()
                    .load(Uri.parse(service.getImageUrl()))
                    .placeholder(R.drawable.placeholder_image)
                    .into((ImageView) findViewById(R.id.service_image_input));
            Uri uri = data.getData();
            service.setImageUrl(uri.toString());
            //requestRead(uri);
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

    }
}
