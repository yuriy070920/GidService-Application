package com.cio.gidservice.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cio.gidservice.R;
import com.cio.gidservice.adapters.ViewPagerAdapter;
import com.cio.gidservice.fragments.OrganizationFragment;
import com.cio.gidservice.fragments.OrganizationServicesFragment;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.viewModels.LogsViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OrganizationActivity extends AppCompatActivity {

    private static final String TAG = "Organization Activity";

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_layout);

        //Finding elements
        tabLayout = findViewById(R.id.tablayout_organization);
        appBarLayout = findViewById(R.id.appbar_organization);
        viewPager = findViewById(R.id.viewpager_organization);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Getting Organization obj from JSON string
        Gson gson = new GsonBuilder()
                            .setPrettyPrinting()
                            .create();
        Organization organization = gson.fromJson(getIntent().getStringExtra("organization"), Organization.class);

        Log.d(TAG, "onCreate: " + organization.toString());

        //Adding fragments
        viewPagerAdapter.addFragment(new OrganizationFragment(this, organization), "About");
        viewPagerAdapter.addFragment(new OrganizationServicesFragment(this, organization), "Services");

        //adapter setup
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
