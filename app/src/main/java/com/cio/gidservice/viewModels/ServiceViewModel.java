package com.cio.gidservice.viewModels;

import android.content.pm.ApplicationInfo;

import com.cio.gidservice.models.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ServiceViewModel {

    private Service service;

    public ServiceViewModel(Service service) {
        this.service = service;
    }

    public boolean save() {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String forSave = gson.toJson(service);

        try{

            File file = new File(new ApplicationInfo().dataDir + "OrganizationList.txt");

            /* This logic is to create the file if the
             * file is not already present
             */
            if(!file.exists()){
                file.createNewFile();
            }

            //Here true is to append the content to file
            FileWriter fw = new FileWriter(file,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(forSave);
            //Closing BufferedWriter Stream
            bw.close();
            return true;

        }catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }
        return false;
    }

    public static void save(Service service) {

    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "ServiceViewModel{" +
                "service=" + service +
                '}';
    }
}
