package com.cio.gidservice.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;

@Database(entities = {Organization.class, Service.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OrganizationDao organizationDao();
    public abstract ServiceDao serviceDao();

}
