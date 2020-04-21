package com.cio.gidservice.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cio.gidservice.models.User;

@Dao
public interface UserDao {
    @Query(value = "SELECT * FROM user WHERE name = 'Admin'")
    User getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);

    @Delete
    void deleteUser(User user);
}
