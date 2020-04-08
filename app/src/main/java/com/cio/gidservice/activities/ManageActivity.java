package com.cio.gidservice.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;

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
import com.cio.gidservice.dao.ServiceDao;
import com.cio.gidservice.models.Organization;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

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
        organizations = orgDB.getAll();
        System.out.println(organizations.size());

        //Получение RecyclerView
        recyclerView = findViewById(R.id.select_organization_recycler);
        //создание адаптера для RecyclerView
        adapter = new SelectOrganizationRecyclerAdapter(this, organizations, () -> {
            AppDatabase database = App.getInstance().getDatabase();
            ServiceDao serviceDao = database.serviceDao();
            for (Organization organization: organizations) {
                organization.setServices(serviceDao.getAllByOrgId(organization.getId()));
                System.out.println("organizationId: " + organization.getId());
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
