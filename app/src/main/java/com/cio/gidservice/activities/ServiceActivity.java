package com.cio.gidservice.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cio.gidservice.R;
import com.cio.gidservice.adapters.ServiceRecyclerAdapter;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class ServiceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ServiceRecyclerAdapter adapter;
    private Organization organization;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        organization = gson.fromJson(getIntent().getStringExtra("organization"), Organization.class);
        recyclerView = findViewById(R.id.recyclerViewServices);
    }
}
