package com.example.usercenter.service.impl

import com.example.usercenter.service.UserService
import rx.Observable

class UserServiceImpl : UserService {
    override fun register(username: String, password: String): Observable<Boolean> {
        return Observable.just(true)
    }
}