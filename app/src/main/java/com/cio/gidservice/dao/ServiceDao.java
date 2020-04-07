package com.cio.gidservice.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cio.gidservice.models.Service;

import java.util.List;

@Dao
public interface ServiceDao {

    @Query("SELECT * FROM service WHERE organization_id = :org_id")
    List<Service> getAllByOrgId(Long org_id);

    @Insert
    void insert(Service service);

    @Delete
    void delete(Service service);
}
