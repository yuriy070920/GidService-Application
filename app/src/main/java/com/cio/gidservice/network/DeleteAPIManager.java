package com.cio.gidservice.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface DeleteAPIManager {

    @DELETE("/delete/service")
    Call<ResponseBody> deleteService(@Query("id") Long id);

    @DELETE("delete/organization")
    Call<ResponseBody> deleteOrganization(@Query("id") Long id);
}
