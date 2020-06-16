package com.example.baselibrary.data.database.repository

import com.example.baselibrary.data.database.database.DatabaseUtils
import com.example.baselibrary.data.database.entry.UserEntry
import javax.inject.Inject

class UserDaoRepository @Inject constructor() {

    private val dao by lazy { DatabaseUtils.appDataBase.userDao2() }
    fun addUser(user: UserEntry): List<Long> {
        return dao.insertUsers(user)

    }


}