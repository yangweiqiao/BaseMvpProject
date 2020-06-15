package com.example.usercenter.persenter

import android.util.Log
import com.example.baselibrary.ext.execute
import com.example.baselibrary.presenter.BasePresenter
import com.example.baselibrary.rx.BaseSubscriber
import com.example.usercenter.data.api.User
import com.example.usercenter.persenter.view.UserView
import com.example.usercenter.service.UserService
import com.example.usercenter.service.impl.UserServiceImpl
import com.trello.rxlifecycle.LifecycleProvider
import javax.inject.Inject

class UserPresenter @Inject constructor() : BasePresenter<UserView>() {

    @Inject
    lateinit var serviceImpl: UserService

    fun register() {
        serviceImpl.register("", "")
            .execute(object : BaseSubscriber<Boolean>(mView) {
                override fun onNext(t: Boolean) {
                    mView.onResult(true)
                }
            }, lifecycleProvider)


    }

}