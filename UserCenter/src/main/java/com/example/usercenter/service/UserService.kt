package com.example.usercenter.service

import com.example.baselibrary.data.database.entry.UserEntry
import rx.Observable


interface UserService {
    fun register(username: String, password: String): Observable<Boolean>
    fun addUser(user: UserEntry): List<Long>
}