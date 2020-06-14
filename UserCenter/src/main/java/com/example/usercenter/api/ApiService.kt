package com.example.usercenter.api

import com.example.baselibrary.data.protocol.BaseResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable


open interface ApiService {
    @GET("bgm/list")
    fun hello(): Observable<BaseResp<List<Bean>>>
}