package com.example.baselibrary.data.database.entry

import android.graphics.Bitmap
import androidx.room.*

/**
 * 标注的实体类
 */
@Entity(tableName = "activity_user"   )
class UserEntry {
    @PrimaryKey
    var id: Int = 0

    @ColumnInfo(name = "first_name")
    var firstName: String? = null

    @ColumnInfo(name = "last_name")
    var lastName: String? = null
    @ColumnInfo(name = "age")
    var age: Int? = 0
    @Ignore
    internal var picture: Bitmap? = null
}