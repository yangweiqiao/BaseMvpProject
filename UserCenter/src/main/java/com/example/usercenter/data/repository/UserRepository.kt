package com.example.usercenter.data.repository

import com.example.baselibrary.data.net.RetrofitFactory
import com.example.baselibrary.data.protocol.BaseResp
import com.example.usercenter.data.api.ApiService
import com.example.usercenter.data.bean.User
import rx.Observable
import javax.inject.Inject
import kotlin.random.Random

class UserRepository @Inject constructor() {
    fun regist(): Observable<BaseResp<User>> {
        val nextInt = Random.nextInt()
        val user = User(nextInt.toString(), "1111")
        return  RetrofitFactory.instance.create(ApiService::class.java).regist(user)

    }

}