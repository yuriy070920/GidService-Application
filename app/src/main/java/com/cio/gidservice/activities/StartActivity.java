package com.cio.gidservice.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cio.gidservice.R;
import com.cio.gidservice.dao.App;
import com.cio.gidservice.dao.AppDatabase;
import com.cio.gidservice.dao.UserDao;
import com.cio.gidservice.models.User;
import com.cio.gidservice.models.UserProperties;
import com.cio.gidservice.network.RetrofitClientInstance;
import com.cio.gidservice.network.UserAPIManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler h = new Handler();
        h.post(() -> getSupportActionBar().hide());
        setContentView(R.layout.splash_screen_layout);
        ((SwipeRefreshLayout)findViewById(R.id.start_refreshing_swipe)).setRefreshing(true);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.main_animation);
        findViewById(R.id.splash_logo).startAnimation(animation);


        Intent intent = new Intent(this, MainActivity.class);

        Thread timer = new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                try {
                    //Try get user instance from local DB
                    AppDatabase db = App.getInstance().getDatabase();
                    UserDao userDao = db.userDao();
                    User user = userDao.getUser();
                    //If user not exists break all and start Main activity  as simple user
                    if (user == null) {
                        UserProperties.setIsAdmin(false);
                    } else {
                        //If user exists try confirm he on server part
                        UserAPIManager apiManager = RetrofitClientInstance.getRetrofitInstance().create(UserAPIManager.class);
                        apiManager.login(user).enqueue(new Callback<ResponseBody>() {
                            //If user exists start activity as businessman user
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    UserProperties.setIsAdmin(true);
                                    try {
                                        Toast.makeText(getApplicationContext(), "Query is successful" + (Long.valueOf(response.body().string()) == user.getId()), Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    UserProperties.setIsAdmin(false);
                                    Toast.makeText(getApplicationContext(), "Query is unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                                ((SwipeRefreshLayout)findViewById(R.id.start_refreshing_swipe)).setRefreshing(false);
                            }

                            //If not exists start activity as simple user
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                UserProperties.setIsAdmin(false);
                                ((SwipeRefreshLayout)findViewById(R.id.start_refreshing_swipe)).setRefreshing(false);
                                Toast.makeText(getApplicationContext(), "Cannot resolve request to server!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}