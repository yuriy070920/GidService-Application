package com.cio.gidservice.network;

import com.cio.gidservice.models.Service;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchAPIManager {

    @GET("/components/location")
    Call<Map<String, Double>> getLocationForService(@Query("serviceId") Long serviceId);

    @GET("/find/services")
    Call<List<Service>> findServicesByKeywords(@Query("keyWords") List<String> keyWords);
}
