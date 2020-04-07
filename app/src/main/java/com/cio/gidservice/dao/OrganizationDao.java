package com.cio.gidservice.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cio.gidservice.models.Organization;

import java.util.List;

@Dao
public interface OrganizationDao {

    @Query("SELECT * FROM organization")
    List<Organization> getAll();

    @Query("SELECT * FROM organization WHERE id = :id")
    Organization getById(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Organization organization);

    @Delete
    void delete(Organization organization);

}
