package com.cio.gidservice.viewModels;

import com.cio.gidservice.models.Organization;

import java.util.List;

public class OrganizationViewModel {

    private Organization organization;

    public OrganizationViewModel(Organization organization) {
        this.organization = organization;
    }

    public OrganizationViewModel(Long id, String name, String description, Float rating) {
        organization.setDescription(description);
        organization.setId(id);
        organization.setName(name);
        organization.setRating(rating);
        //organization.setServices(services);
    }
}
