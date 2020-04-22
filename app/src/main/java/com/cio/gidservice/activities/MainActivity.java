package com.cio.gidservice.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cio.gidservice.R;
import com.cio.gidservice.adapters.OrganizationCustomAdapter;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.AppDatabase;
import com.cio.gidservice.dao.OrganizationDao;
import com.cio.gidservice.dao.UserDao;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;
import com.cio.gidservice.models.User;
import com.cio.gidservice.models.UserProperties;
import com.cio.gidservice.network.OrganizationAPIManager;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private OrganizationCustomAdapter adapter;
    private RecyclerView recyclerView;
    private DrawerLayout drawLay;
    private ActionBarDrawerToggle drawTog;
    private NavigationView navView;
    private ProgressDialog progressDialog;

    private SwipeRefreshLayout refreshLayout;


    List<Organization> organizations = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout1);

        checkPermossions();;

        ((SwipeRefreshLayout)findViewById(R.id.refreshLayout)).setRefreshing(true);

        settingUpNavigationMenu();

        settingUpRefreshLayout();

    }

    private void checkPermossions() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }

    private void settingUpRefreshLayout() {
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            generateDataList(organizations);
        });

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        progressDialog.dismiss();
        generateDataList(organizations);
    }

    private void settingUpNavigationMenu() {
        drawLay = findViewById(R.id.main_layout);
        drawTog = new ActionBarDrawerToggle(this, drawLay, R.string.Open, R.string.Close);
        drawLay.addDrawerListener(drawTog);
        drawTog.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.menu_icon);
        //getSupport
        navView = findViewById(R.id.navigation_view);
        System.out.println(UserProperties.isAdmin());
        if(UserProperties.isAdmin()){
            navView.inflateMenu(R.menu.admin_menu);
        } else {
            navView.inflateMenu(R.menu.simple_user_menu);
        }
        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch(id) {
                case R.id.nav_search:
                    Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                    /*Intent intent = new Intent(this, AddOrganizationActivity.class);
                    startActivity(intent);*/
                    break;
                case R.id.nav_management:
                    Toast.makeText(this, "Management", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ManagementActivity.class);
                    startActivity(intent);
                    break;
                case R.id.login_admin:
                    Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(this, LoginActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.logout:
                    Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                    UserProperties.setIsAdmin(false);
                    UserDao userDB = App.getInstance().getDatabase().userDao();
                    User user = userDB.getUser();
                    if(user != null)
                        userDB.deleteUser(user);
                    Intent intent3 = new Intent(this, MainActivity.class);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent3);
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawTog.onOptionsItemSelected(item)){
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
                    recyclerView = findViewById(R.id.recyclerView);
                    //создание адаптера для RecyclerView
                    adapter = new OrganizationCustomAdapter(getApplicationContext(), organizations, () -> {
                        for (Organization organization: organizations) {
                            apiManager.getServices(organization.getId()).enqueue(new Callback<List<Service>>() {
                                @Override
                                public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                                    if(response.isSuccessful())
                                        organization.setServices(response.body());
                                }

                                @Override
                                public void onFailure(Call<List<Service>> call, Throwable t) {

                                }
                            });
                            System.out.println("organizationId: " + organization.getId());
                        }
                    });

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Organization>> call, Throwable t) {
                organizations = orgDB.getAll();
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
