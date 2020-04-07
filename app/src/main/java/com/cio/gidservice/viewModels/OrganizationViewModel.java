package com.cio.gidservice.viewModels;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.Editable;

import androidx.room.Room;

import com.cio.gidservice.dao.AppDatabase;
import com.cio.gidservice.models.Organization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OrganizationViewModel {

    private Organization organization;
    private Activity activity;

    public OrganizationViewModel(Activity activity, Organization organization) {
        this.activity = activity;
        this.organization = organization;
    }

    public OrganizationViewModel(Editable text, Editable text1, Editable text2, Editable text3) {
    }

    public boolean save() {
        //AppDatabase database = Room.databaseBuilder()
        return false;
    }

    /*public static boolean save(Organization organization) {
        OrganizationViewModel viewModel = new OrganizationViewModel(organization);
        return viewModel.save();
    }*/

    /*public static boolean save(List<Organization> organizations) {
        boolean isSuccessfulWriting = true;
        for (Organization organization: organizations) {
            isSuccessfulWriting = save(organization);
        }
        return isSuccessfulWriting;
    }*/

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "OrganizationViewModel{" +
                "organization=" + organization +
                '}';
    }
}
