package com.example.usercenter.service.impl

import android.util.Log
import com.example.baselibrary.ext.convert
import com.example.baselibrary.ext.convertBoolean
import com.example.usercenter.data.api.User
import com.example.usercenter.data.repository.UserRepository
import com.example.usercenter.service.UserService
import rx.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {
    @Inject
    lateinit var repository: UserRepository

    override fun register(username: String, password: String): Observable<Boolean> {

        return repository.regist().convertBoolean()

    }
}