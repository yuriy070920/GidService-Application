package com.cio.gidservice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cio.gidservice.R;
import com.cio.gidservice.adapters.SearchServiceAdapter;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.UserDao;
import com.cio.gidservice.models.Service;
import com.cio.gidservice.models.User;
import com.cio.gidservice.models.UserProperties;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.cio.gidservice.network.SearchAPIManager;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Service> services;
    private SearchServiceAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private String searchString;
    private EditText editText;

    private DrawerLayout drawerLay;
    private ActionBarDrawerToggle drawTog;
    private NavigationView navView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_search_layout);

        setTitle("Search");

        init();

        refreshLayout = findViewById(R.id.searchSwipeRefresh);
        refreshLayout.setRefreshing(false);
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            search(searchString);
        });

        editText = findViewById(R.id.searchEditField);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                refreshLayout.setRefreshing(true);
                searchString = editText.getText().toString();
                search(searchString);
                return true;
            }
            return false;
        });
    }

    private void init() {
        drawerLay = findViewById(R.id.mainSearchLayout);
        drawTog = new ActionBarDrawerToggle(this, drawerLay, R.string.Open, R.string.Close);
        drawerLay.addDrawerListener(drawTog);
        drawTog.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navView = findViewById(R.id.searchNavMenu);
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
        switch(item.getItemId()){
            case R.id.action_find:
                Intent intent = new Intent(this, FindActivity.class);
                startActivity(intent);
                break;
        }
        if(drawTog.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(String searchString) {
        String[] strings = searchString.split(" ");
        SearchAPIManager apiManager = RetrofitClientInstance.getRetrofitInstance().create(SearchAPIManager.class);
        apiManager.findServicesByKeywords(Arrays.asList(strings)).enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if(response.isSuccessful()){
                    refreshLayout.setRefreshing(false);
                    recyclerView = findViewById(R.id.searchRecyclerView);
                    adapter = new SearchServiceAdapter(getApplicationContext(), response.body());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FindActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                } else {
                    refreshLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), "Server response is not successful!\n", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(), "There is not connection to server!\nTry again later!", Toast.LENGTH_SHORT).show();
            }
        });
        editText.setActivated(false);
    }
}
