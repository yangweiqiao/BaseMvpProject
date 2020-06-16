package com.example.usercenter.persenter

import android.util.Log
import com.example.baselibrary.data.database.entry.UserEntry
import com.example.baselibrary.ext.execute
import com.example.baselibrary.presenter.BasePresenter
import com.example.baselibrary.rx.BaseSubscriber
import com.example.usercenter.persenter.view.UserView
import com.example.usercenter.service.UserService
import javax.inject.Inject

class UserPresenter @Inject constructor() : BasePresenter<UserView>() {

    @Inject
    lateinit var serviceImpl: UserService

    fun register() {
        serviceImpl.register("", "")
            .execute(mView, lifecycleProvider,
                onSuccess = {
                    mView.onResult(it)
                }
                ,
                onFailed = {

                }
            )


    }

    fun addUser(user: UserEntry) {
        serviceImpl.addUser(user)
            .forEach {
                Log.e("数据库", it.toString())
            }


    }
}