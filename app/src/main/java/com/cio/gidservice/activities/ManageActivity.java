package com.cio.gidservice.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cio.gidservice.R;
import com.cio.gidservice.adapters.OrganizationCustomAdapter;
import com.cio.gidservice.adapters.SelectOrganizationRecyclerAdapter;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.AppDatabase;
import com.cio.gidservice.dao.OrganizationDao;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.network.OrganizationAPIManager;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private OrganizationCustomAdapter adapter;
    private RecyclerView recyclerView;
    private DrawerLayout drawLay;
    private ActionBarDrawerToggle drawTog;
    private NavigationView navView;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout refreshLayout;
    private List<Organization> organizations = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_layout);
        generateDataList(organizations);

        //Setting header name
        setTitle("Choose organization...");

        //Setting up button for returning
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void generateDataList(List<Organization> dataList) {

        AppDatabase db = App.getInstance().getDatabase();
        OrganizationDao orgDB = db.organizationDao();

        OrganizationAPIManager apiManager = RetrofitClientInstance.getRetrofitInstance().create(OrganizationAPIManager.class);
        apiManager.getOrganizationList().enqueue(new Callback<List<Organization>>() {
            @Override
            public void onResponse(Call<List<Organization>> call, Response<List<Organization>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.body().size());
                    organizations = response.body();
                    orgDB.clear();
                    for (Organization organization: organizations) {
                        orgDB.insert(organization);
                    }
                    //Получение RecyclerView
                    recyclerView = findViewById(R.id.select_organization_recycler);
                    //создание адаптера для RecyclerView
                    adapter = new SelectOrganizationRecyclerAdapter(getApplicationContext(), organizations, () -> {});
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Organization>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Check your internet connection!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
