package com.example.baselibrary.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.baselibrary.data.database.entry.UserEntry
import com.example.baselibrary.data.database.entry.User2
import com.example.baselibrary.data.database.dao.UserDao
import com.example.baselibrary.data.database.dao.UserDao2

/**
 * 是扩展 RoomDatabase 的抽象类 在注释entities中添加与数据库关联的实体列表。
 */
@Database(entities = arrayOf(UserEntry::class , User2::class) , version = 1)
abstract class  AppDataBase  : RoomDatabase(){
    //包含具有若干个  没有参数且返回使用 @Dao 注释的类的抽象方法。
    abstract fun userDao(): UserDao
    abstract fun userDao2(): UserDao2
}