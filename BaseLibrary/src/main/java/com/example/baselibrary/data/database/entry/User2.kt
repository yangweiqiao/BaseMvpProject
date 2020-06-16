package com.example.baselibrary.data.database.entry

import android.graphics.Bitmap
import androidx.room.*


@Entity(tableName = "user2"   )
class User2 {
    @PrimaryKey
    var id: Int = 0

    @ColumnInfo(name = "first_name")
    var firstName: String? = null

    @ColumnInfo(name = "last_name")
    var lastName: String? = null

    @Ignore
    internal var picture: Bitmap? = null
}