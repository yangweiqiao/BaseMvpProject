package com.example.baselibrary.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.baselibrary.data.database.entry.UserEntry
import com.example.baselibrary.data.database.entry.User2

@Dao
interface UserDao2 {

    @Query("SELECT * FROM user2 WHERE first_name like :name ")
    fun findUserByName(name: String): List<User2>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users: UserEntry):  List<Long>


}