package com.example.usercenter.service.impl

import com.example.baselibrary.data.net.RetrofitFactory
import com.example.usercenter.data.api.ApiService
import com.example.usercenter.data.api.User
import com.example.usercenter.service.UserService
import rx.Observable

class UserServiceImpl : UserService {
    override fun register(username: String, password: String): Observable<Boolean> {

        val user = User("1111", "1111")

        val hello = RetrofitFactory.instance.create(ApiService::class.java).regist(user)

        return hello.flatMap { t ->
            if (t!!.status == 200) {
                Observable.just(true)
            } else {
                Observable.just(false)
            }
        }

    }
}