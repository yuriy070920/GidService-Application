package com.cio.gidservice.requestEntities;

import com.cio.gidservice.models.Organization;

import java.util.List;

public class OrganizationRequestEntity extends Organization {

    private String ip;

    public OrganizationRequestEntity(Long id, String name, String description, Float rating, List<Service> services) {
        super(id, name, description, rating, services);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return super.toString() + ip;
    }
}
