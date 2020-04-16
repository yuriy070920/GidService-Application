package com.cio.gidservice.network;

import com.cio.gidservice.models.Logs;
import com.cio.gidservice.models.User;
import com.cio.gidservice.requestEntities.UserRequestEntity;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserManager  {

    @GET("/auth/login")
    Call<User> login(@Body Logs logs);

    @POST("/auth/register")
    Call<User> register(@Body UserRequestEntity entity);

    @DELETE("/auth/logout")
    Call<?> logout(@Query("usr_id")  Long usrID, @Body String ip);

    @Multipart
    @POST("/organization/image")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image);
}
