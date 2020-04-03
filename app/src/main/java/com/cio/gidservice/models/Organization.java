package com.cio.gidservice.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Organization {

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("rating")
    private Float rating;
    @SerializedName("services")
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

    public class Service {
        @SerializedName("id")
        private Long id;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("leadTime")
        private Integer leadTime; //minutes
        @SerializedName("price")
        private Float price;

        public Service(Long id, String name, String description, Integer leadTime, Float price) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.leadTime = leadTime;
            this.price = price;
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
    }
}
