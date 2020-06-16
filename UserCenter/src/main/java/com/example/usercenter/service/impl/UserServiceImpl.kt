package com.example.usercenter.service.impl

import com.example.baselibrary.data.database.entry.UserEntry
import com.example.baselibrary.data.database.repository.UserDaoRepository
import com.example.baselibrary.ext.convertBoolean
import com.example.usercenter.data.repository.UserRepository
import com.example.usercenter.service.UserService
import rx.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {
    override fun addUser(user: UserEntry): List<Long> {

        return userDaoRepositroy.addUser(user)
    }

    @Inject
    lateinit var repository: UserRepository
    @Inject
    lateinit var userDaoRepositroy: UserDaoRepository

    override fun register(username: String, password: String): Observable<Boolean> {

        return repository.regist().convertBoolean()

    }
}