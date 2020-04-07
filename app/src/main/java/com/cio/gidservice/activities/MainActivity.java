package com.cio.gidservice.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import com.cio.gidservice.R;
import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;
import com.cio.gidservice.adapters.OrganizationCustomAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private OrganizationCustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    SwipeRefreshLayout refreshLayout;

    List<Organization> organizations = new ArrayList<>();

    /*//vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImagesUrl = new ArrayList<>();*/

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout1);

        //initImageBitmap();

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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progressDialog.dismiss();
        generateDataList(organizations);

        /*OrganizationAPIManager service = RetrofitClientInstance.getRetrofitInstance().create(OrganizationAPIManager.class);
        Call<List<Organization>> listCall = service.getOrganizationList(1L);
        listCall.enqueue(new Callback<List<Organization>>() {
            @Override
            public void onResponse(Call<List<Organization>> call, Response<List<Organization>> response) {
                progressDialog.dismiss();
                if(response.isSuccessful())
                    generateDataList(response.body());
                else {
                    Log.d(TAG, String.valueOf(response.code()));
                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Organization>> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });*/
        /*final EditText phone = findViewById(R.id.phoneTextField);
        final EditText pass = findViewById(R.id.passwordTextField);*/

    }

    private void generateDataList(List<Organization> dataList) {
        //Создание/получение данных (model)
        List<Organization> list = new ArrayList<>(Arrays.asList(
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
                                        ""),
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
                        "https://images.unsplash.com/photo-1441786485319-5e0f0c092803?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60")));
        organizations.addAll(list);
        //Получение RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        //создание адаптера для RecyclerView
        adapter = new OrganizationCustomAdapter(this, organizations);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /*private void initImageBitmap() {
        Log.d(TAG, "initImageBitmap: preparing bitmaps.");

        mImagesUrl.add("https://images.unsplash.com/photo-1441786485319-5e0f0c092803?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("James John");

        mImagesUrl.add("https://images.unsplash.com/photo-1489278353717-f64c6ee8a4d2?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("James Bond");

        mImagesUrl.add("");
        mNames.add("William Woles");

        mImagesUrl.add("https://images.unsplash.com/photo-1514846326710-096e4a8035e0?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
        mNames.add("Bill Gates");

        mImagesUrl.add("https://images.unsplash.com/photo-1487222477894-8943e31ef7b2?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60");
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
