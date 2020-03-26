package com.cio.gidservice.viewModels;

import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;

public class ServiceViewModel {

    private Service service;

    public ServiceViewModel(Service service) {
        this.service = service;
    }

    public ServiceViewModel(Long id, String name, String description, Integer leadTime, Float price, Organization organization) {
        service = new Service(id, name, description, leadTime, price, organization);
    }
}
