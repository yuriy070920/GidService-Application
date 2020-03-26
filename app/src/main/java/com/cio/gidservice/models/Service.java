package com.cio.gidservice.models;

public class Service {

    private Long id;
    private String name;
    private String description;
    private Integer leadTime; //minutes
    private Float price;
    private Organization organization;

    public Service(Long id, String name, String description, Integer leadTime, Float price, Organization organization) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.leadTime = leadTime;
        this.price = price;
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}

