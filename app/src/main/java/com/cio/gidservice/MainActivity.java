package com.cio.gidservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImagesUrl = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout1);

        initImageBitmap();
        /*final EditText phone = findViewById(R.id.phoneTextField);
        final EditText pass = findViewById(R.id.passwordTextField);*/

    }

    /*public void handleClickMain(View view) {
        final EditText phone = findViewById(R.id.phoneTextField);
        final EditText pass = findViewById(R.id.passwordTextField);

        if(phone.getText().toString().equals("+380931262912") && pass.getText().toString().equals("123456")){
            Intent intent = new Intent(this, VerificationActivity.class);
            startActivity(intent);
        }
    }*/

    private void initImageBitmap() {
        Log.d(TAG, "initImageBitmap: preparing bitmaps.");

        mImagesUrl.add("https://images.unsplash.com/photo-1441786485319-5e0f0c092803?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("James John");

        mImagesUrl.add("https://images.unsplash.com/photo-1489278353717-f64c6ee8a4d2?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("James Bond");

        mImagesUrl.add("https://images.unsplash.com/photo-1422568374078-27d3842ba676?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("William Woles");

        mImagesUrl.add("https://images.unsplash.com/photo-1514846326710-096e4a8035e0?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("Bill Gates");

        mImagesUrl.add("https://images.unsplash.com/photo-1487222477894-8943e31ef7b2?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("Brave Heart");

        mImagesUrl.add("https://images.unsplash.com/photo-1502323777036-f29e3972d82f?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("Selena Gomez");

        mImagesUrl.add("https://images.unsplash.com/photo-1484972759836-b93f9ef2b293?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("Igor Voitenko");

        mImagesUrl.add("https://images.unsplash.com/photo-1500336624523-d727130c3328?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("Yurii Surzhikov");

        mImagesUrl.add("https://images.unsplash.com/photo-1485893086445-ed75865251e0?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("Dmitriy Melnik");

        mImagesUrl.add("https://images.unsplash.com/photo-1473531761844-5a14668fc8f8?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("Ann Surzhikova");
        
        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler.");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter viewAdapter = new RecyclerViewAdapter(this, mNames, mImagesUrl);
        recyclerView.setAdapter(viewAdapter);
    }
}
