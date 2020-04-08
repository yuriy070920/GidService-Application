package com.cio.gidservice.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cio.gidservice.R;
import com.cio.gidservice.adapters.OrganizationCustomAdapter;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.AppDatabase;
import com.cio.gidservice.dao.OrganizationDao;
import com.cio.gidservice.dao.ServiceDao;
import com.cio.gidservice.models.Organization;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

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

        settingUpNavigationMenu();

        settingUpRefreshLayout();

    }

    private void settingUpRefreshLayout() {
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            generateDataList(organizations);
            refreshLayout.setRefreshing(false);
        });

        progressDialog = new ProgressDialog(MainActivity.this);
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
        //getSupport
        navView = findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch(id) {
                case R.id.nav_search:
                    Toast.makeText(this, "Search item selected", Toast.LENGTH_SHORT).show();
                    /*Intent intent = new Intent(this, AddOrganizationActivity.class);
                    startActivity(intent);*/
                    break;
                case R.id.nav_add_organization:
                    Toast.makeText(this, "Add organization item selected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, AddOrganizationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_organization_manage:
                    Toast.makeText(this, "Add service item selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(this, ManageActivity.class);
                    startActivity(intent1);
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
        organizations = orgDB.getAll();
        System.out.println(organizations.size());

        //Получение RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        //создание адаптера для RecyclerView
        adapter = new OrganizationCustomAdapter(this, organizations, () -> {
            AppDatabase database = App.getInstance().getDatabase();
            ServiceDao serviceDao = database.serviceDao();
            for (Organization organization: organizations) {
                organization.setServices(serviceDao.getAllByOrgId(organization.getId()));
                System.out.println("organizationId: " + organization.getId());
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    //Создание/получение данных (model)
        /*List<Organization> list = new ArrayList<>(Arrays.asList(
                new Organization(1L,
                        "Парикмахерская 1",
                        "Лучшая парикмахерская в вашей жизни!",
                        4.5f,
                        new ArrayList<>(Arrays.asList(new Service(1L,
                                        "Стрижка 1",
                                        "Лучшей стрижки вы не делали никогда!",
                                        35,
                                        150F,
                                        "https://images.unsplash.com/photo-1422568374078-27d3842ba676?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
                                new Service(2L,
                                        "Стрижка 2",
                                        "Лучшей стрижки вы не делали никогда!",
                                        35,
                                        150F,
                                        "https://images.unsplash.com/photo-1500336624523-d727130c3328?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"))),
                        "https://images.unsplash.com/photo-1484972759836-b93f9ef2b293?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
                new Organization(2L,
                        "Парикмахерская 2",
                        "Лучшая парикмахерская в вашей жизни!",
                        4.5f,
                        new ArrayList<>(Arrays.asList(new Service(3L,
                                        "Стрижка 1",
                                        "Лучшей стрижки вы не делали никогда!",
                                        35,
                                        150F,
                                        "https://images.unsplash.com/photo-1487222477894-8943e31ef7b2?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
                                new Service(4L,
                                        "Стрижка 2",
                                        "Лучшей стрижки вы не делали никогда!",
                                        35,
                                        150F,
                                        "https://images.unsplash.com/photo-1485893086445-ed75865251e0?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
                                new Service(4L,
                                        "Стрижка 3",
                                        "Лучшей стрижки вы не делали никогда!",
                                        45,
                                        170F,
                                        "https://images.unsplash.com/photo-1473531761844-5a14668fc8f8?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
                                new Service(4L,
                                        "Стрижка 4",
                                        "Лучшей стрижки вы не делали никогда!",
                                        20,
                                        120F,
                                        "https://images.unsplash.com/photo-1484972759836-b93f9ef2b293?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"))),
                        "https://images.unsplash.com/photo-1422568374078-27d3842ba676?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
                new Organization(3L,
                        "Парикмахерская 3",
                        "Лучшая парикмахерская в вашей жизни!",
                        4.5f,
                        new ArrayList<>(Arrays.asList(new Service(5L,
                                        "Стрижка 1",
                                        "Лучшей стрижки вы не делали никогда!",
                                        35,
                                        150F,
                                        "https://images.unsplash.com/photo-1422568374078-27d3842ba676?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"),
                                new Service(6L,
                                        "Стрижка 2",
                                        "Лучшей стрижки вы не делали никогда!",
                                        35,
                                        150F,
                                        "https://images.unsplash.com/photo-1489278353717-f64c6ee8a4d2?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"))),
                        "https://images.unsplash.com/photo-1441786485319-5e0f0c092803?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60")));*/

    /*private void initImageBitmap() {
        Log.d(TAG, "initImageBitmap: preparing bitmaps.");

        mImagesUrl.add("");
        mNames.add("James John");

        mImagesUrl.add("https://images.unsplash.com/photo-1489278353717-f64c6ee8a4d2?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("James Bond");

        mImagesUrl.add("");
        mNames.add("William Woles");

        mImagesUrl.add("https://images.unsplash.com/photo-1514846326710-096e4a8035e0?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("Bill Gates");

        mImagesUrl.add("");
        mNames.add("Brave Heart");

        mImagesUrl.add("");
        mNames.add("Selena Gomez");

        mImagesUrl.add("");
        mNames.add("Igor Voitenko");

        mImagesUrl.add("");
        mNames.add("Yurii Surzhikov");

        mImagesUrl.add("");
        mNames.add("Dmitriy Melnik");

        mImagesUrl.add("");
        mNames.add("Ann Surzhikova");
        
        initRecyclerView();
    }*/
}
