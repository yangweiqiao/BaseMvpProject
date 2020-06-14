package com.example.usercenter.service

import rx.Observable


interface UserService {
    fun  register (username:String , password:String ): Observable<Boolean>
}