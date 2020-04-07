package com.cio.gidservice.viewController;

import com.cio.gidservice.models.Organization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OrganizationDataWorker implements DataWorker {

    private String path;

    Gson gson;

    public OrganizationDataWorker(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void save(List<?> objects) {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            gson.toJson(objects, new FileWriter(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getData() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try{
            gson.fromJson(new FileReader(path), List.class);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
