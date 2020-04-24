package com.cio.gidservice.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cio.gidservice.models.Organization;
import com.cio.gidservice.models.Service;
import com.cio.gidservice.models.User;

@Database(entities = {Organization.class, Service.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OrganizationDao organizationDao();
    public abstract ServiceDao serviceDao();
    public abstract UserDao userDao();

}
