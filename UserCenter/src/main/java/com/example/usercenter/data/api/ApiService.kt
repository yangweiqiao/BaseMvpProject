package com.example.usercenter.data.api

import com.example.baselibrary.data.protocol.BaseResp
import com.example.usercenter.data.bean.User
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable


open interface ApiService {
    @POST("regist")
    fun regist(@Body user: User): Observable<BaseResp<User>>
}