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
    @SerializedName("lat")
    private Double lat;
    @SerializedName("lng")
    private Double lng;
    @SerializedName("services")
    @Ignore
    private List<Service> services;

    public Organization() {
        this(null, null, null, null, null, null, null, null);
    }

    public Organization(Long id, String name, String description, Float rating, String imageUrl, Double lat, Double lng, List<Service> services) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lng = lng;
        this.services = services;
    }

    public static Long getId_counter() {
        return id_counter;
    }

    public static void setId_counter(Long id_counter) {
        Organization.id_counter = id_counter;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
