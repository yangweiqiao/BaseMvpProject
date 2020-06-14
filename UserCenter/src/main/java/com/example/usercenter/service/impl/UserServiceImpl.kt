package com.example.usercenter.service.impl

import com.example.baselibrary.data.net.RetrofitFactory
import com.example.usercenter.api.ApiService
import com.example.usercenter.service.UserService
import rx.Observable

class UserServiceImpl : UserService {
    override fun register(username: String, password: String): Observable<Boolean> {

        val hello = RetrofitFactory.instance.create(ApiService::class.java).hello()

        return Observable.just(false)
    }
}