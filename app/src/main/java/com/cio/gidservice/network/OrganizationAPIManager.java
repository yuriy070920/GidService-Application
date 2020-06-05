package com.cio.gidservice.network;

import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface OrganizationAPIManager {

    @GET("/organization/get-all")
    Call<List<Organization>> getOrganizationList();

    @GET("/organization/{user_id}/get-all")
    Call<List<Organization>> getOrganizationList(@Path("user_id") Long id);

    @Multipart
    @POST("/organization/{user_id}/add")
    Call<List<Organization>> addOrganization(
            @Path("user_id") Long id,
            @Part MultipartBody.Part photo,
            @Part("name") RequestBody name,
            @Part("description") RequestBody description,
            @Part("lat") Double lat,
            @Part("lng") Double lng
    );

    @Multipart
    @POST("/organization/{id_org}/addService")
    Call<Long> addService(
            @Path("id_org") Long id_org,
            @Part MultipartBody.Part photo,
            @Part("name") RequestBody name,
            @Part("description") RequestBody description,
            @Part("cost") RequestBody cost
    );

    @GET("/organization/getServices")
    Call<List<Service>> getServices(@Query("org_id") Long orgID);

    @GET("/organization/getAllService/{user_id}")
    Call<List<Service>> getServiceForUser(@Path("user_id") Long id);

}
