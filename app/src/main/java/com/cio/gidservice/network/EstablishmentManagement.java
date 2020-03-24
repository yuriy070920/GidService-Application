package com.cio.gidservice.network;

import com.cio.gidservice.models.Establishment;

import java.util.List;

import retrofit2.*;
import retrofit2.http.*;

public interface EstablishmentManagement {

    @GET("/establishment/{user_id}/get-all")
    Call<List<Establishment>> listCall(@Path("user_id") Long id);

}
