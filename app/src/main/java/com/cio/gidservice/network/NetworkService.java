package com.cio.gidservice.network;

public class NetworkService {

    private static NetworkService service;
    private static final String BASE_URL = "http://localhost:8080";

    public static NetworkService getService() {
        if(service == null)
            service = new NetworkService();
        return service;
    }
}
