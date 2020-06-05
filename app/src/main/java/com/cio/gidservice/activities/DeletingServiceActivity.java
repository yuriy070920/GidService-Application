package com.cio.gidservice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cio.gidservice.R;
import com.cio.gidservice.adapters.DeletingServiceAdapter;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.UserDao;
import com.cio.gidservice.dialogs.ErrorDialog;
import com.cio.gidservice.models.Service;
import com.cio.gidservice.models.User;
import com.cio.gidservice.models.UserProperties;
import com.cio.gidservice.network.OrganizationAPIManager;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeletingServiceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Service> services;
    private DeletingServiceAdapter adapter;

    private DrawerLayout drawerLay;
    private ActionBarDrawerToggle drawTog;
    private NavigationView navView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_service_layout);

        setTitle("Deleting service");

        init();

        loadList();
    }

    private void init() {
        drawerLay = findViewById(R.id.deletingServiceLayout);
        drawTog = new ActionBarDrawerToggle(this, drawerLay, R.string.Open, R.string.Close);
        drawerLay.addDrawerListener(drawTog);
        drawTog.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navView = findViewById(R.id.deletingNavMenu);
        if(UserProperties.isAdmin()){
            navView.inflateMenu(R.menu.admin_menu);
        } else {
            navView.inflateMenu(R.menu.simple_user_menu);
        }
        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch(id) {
                case R.id.nav_search:
                case R.id.simple_search_icon:
                    Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, FindActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME|Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    break;
                case R.id.nav_management:
                    Toast.makeText(this, "Management", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(this, ManagementActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_TASK_ON_HOME|Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent1);
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
                case R.id.home_page:
                    Intent intent4 = new Intent(this, MainActivity.class);
                    intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent4);
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

    private void loadList() {
        OrganizationAPIManager apiManager = RetrofitClientInstance.getRetrofitInstance().create(OrganizationAPIManager.class);
        apiManager.getServiceForUser(UserProperties.getUser().getId()).enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                recyclerView = findViewById(R.id.deleteServRecyclerView);
                adapter = new DeletingServiceAdapter(getApplicationContext(), response.body(), DeletingServiceActivity.this, () -> loadList());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                ErrorDialog dialog = new ErrorDialog(DeletingServiceActivity.this);
                dialog.start("An error occurred while loading data!\n Check your internet connection or try again later", 3000);
            }
        });
    }
}
