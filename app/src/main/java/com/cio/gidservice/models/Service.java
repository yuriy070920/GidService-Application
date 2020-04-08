package com.cio.gidservice.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(foreignKeys = @ForeignKey(entity = Organization.class, parentColumns = "id", childColumns = "organization_id"))
public class Service implements Serializable {

    private static Long id_counter = 0L;

    @PrimaryKey(autoGenerate = true)
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
    @SerializedName("imageUrl")
    private String imageUrl;
    @ColumnInfo(name = "organization_id")
    private Long id_organization;

    public Long getId_organization() {
        return id_organization;
    }

    public void setId_organization(Long id_organization) {
        this.id_organization = id_organization;
    }

    public Service() {
    }

    public Service(Long id, String name, String description, Integer leadTime, Float price, String imageUrl, Long id_organization) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.leadTime = leadTime;
        this.price = price;
        this.imageUrl = imageUrl;
        this.id_organization = id_organization;
    }

    @Ignore
    public Service(String name, String description, Integer leadTime, Float price, String imageUrl, Long id_organization) {
        this.name = name;
        this.description = description;
        this.leadTime = leadTime;
        this.price = price;
        this.imageUrl = imageUrl;
        this.id_organization = id_organization;
    }

    @Ignore
    public Service(Long id, String name, String description, Integer leadTime, Float price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.leadTime = leadTime;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @Ignore
    public Service(String name, String description, Integer leadTime, Float price, String imageUrl) {
        this(++id_counter, name, description, leadTime, price, imageUrl, 5L);
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", leadTime=" + leadTime +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
