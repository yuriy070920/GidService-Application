package com.cio.gidservice.requestEntities;

import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;

public class ServiceRequestEntity extends Service {
    private String userIP;
    private Long organizationID;

    public ServiceRequestEntity(Long id, String name, String description, Integer leadTime, Float price, Organization organization) {
        super(id, name, description, leadTime, price, organization);
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public Long getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(Long organizationID) {
        this.organizationID = organizationID;
    }
}
