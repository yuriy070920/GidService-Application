package com.cio.gidservice.requestEntities;

import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;

import java.util.List;

public class OrganizationRequestEntity extends Organization {

    private String ip;

    public OrganizationRequestEntity(Long id, String name, String description, Float rating, List<Service> services, String imageUrl, String ip) {
        super(id, name, description, rating, services, imageUrl);
        this.ip = ip;
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
