package com.example.usercenter.service

import com.example.usercenter.data.api.User
import rx.Observable


interface UserService {
    fun  register (username:String , password:String ): Observable<Boolean>
}