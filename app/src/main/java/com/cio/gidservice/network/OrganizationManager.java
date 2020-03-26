package com.cio.gidservice.network;

import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;

import java.util.List;

import retrofit2.*;
import retrofit2.http.*;

public interface OrganizationManager {

    @GET("/establishment/get-all")
    Call<List<Organization>> listCall(@Path("user_id") Long id);

    @POST("/establishment/add-service")
    Call<okhttp3.Response> addService(@Path("user_id") String establishmentName, Service service);
}
