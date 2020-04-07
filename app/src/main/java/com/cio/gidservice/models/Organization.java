package com.cio.gidservice.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class Organization implements Serializable {

    private static Long id_counter = 0L;

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("rating")
    private Float rating;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("services")
    @Ignore
    private List<Service> services;

    public Organization() {
        this(null, null, null, null, null);
    }

    public Organization(Long id, String name, String description, Float rating, String imageUrl, List<Service> services) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.services = services;
    }

    public Organization(String name, String description, Float rating, List<Service> services, String imageUrl) {
        this(++id_counter, name, description, rating, imageUrl, services);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", imageUrl='" + imageUrl + '\'' +
                ", services=" + services +
                '}';
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
