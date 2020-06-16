package com.example.baselibrary.data.database.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.baselibrary.common.AppGlobals

/**
 * 数据库操作类 ，初始化AppDataBase，并提供一个方法获取AppDataBase
 */
@SuppressWarnings("all")
object DatabaseUtils {
    //AppDataBase
    lateinit var appDataBase: AppDataBase

    fun createDataBase() {
        //创建数据库对象
        appDataBase = Room.databaseBuilder(
            AppGlobals.getApplication(),
            AppDataBase::class.java,
            "android_room_dev.db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration() //数据库升级之后的异常回滚
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE) //日志打印模式
            // .addMigrations(MIGRATION_1_2, MIGRATION_2_3) //数据库升级时候使用
            .build();
    }

    fun getDatabase(): AppDataBase {
        if (appDataBase == null) {
            createDataBase()
        }
        return appDataBase
    }

}



