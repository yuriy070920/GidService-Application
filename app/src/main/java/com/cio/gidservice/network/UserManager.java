package com.cio.gidservice.network;

import com.cio.gidservice.models.User;

import retrofit2.Call;

public interface UserManager  {


    Call<User> register();
}
