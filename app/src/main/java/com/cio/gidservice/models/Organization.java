package com.cio.gidservice.models;

import java.util.List;

public class Organization {

    private Long id;
    private String name;
    private String description;
    private Float rating;
    private List<Service> services;

    public Organization(Long id, String name, String description, Float rating, List<Service> services) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.services = services;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
