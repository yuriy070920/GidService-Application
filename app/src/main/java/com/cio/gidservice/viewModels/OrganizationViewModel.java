package com.cio.gidservice.viewModels;

import android.content.Context;
import android.widget.Toast;

import com.cio.gidservice.models.Organization;

import java.util.List;

public class OrganizationViewModel {

    private Organization organization;
    private Context context;

    public OrganizationViewModel(Organization organization) {
        this.organization = organization;
    }

    public OrganizationViewModel(Context context, Organization organization) {
        this.context = context;
        this.organization = organization;
    }

    public OrganizationViewModel(Context context) {
        this.context = context;
    }

    public void loadOrganizations() {

    }

    public static void loadOrganizations(Context context) {
        Toast.makeText(context, "Loading Organizations", Toast.LENGTH_SHORT).show();
    }
}
