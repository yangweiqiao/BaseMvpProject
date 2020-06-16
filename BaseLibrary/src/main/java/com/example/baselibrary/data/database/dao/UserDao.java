package com.example.baselibrary.data.database.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Query;

/**
 * 实际操作数据库的类，提供方法
 */
@Dao
public interface UserDao  {

    @Query("SELECT * FROM user WHERE age > :minAge LIMIT 5")
    Cursor loadRawUsersOlderThan(int minAge);

}