package com.example.usercenter.persenter

import com.example.baselibrary.ext.execute
import com.example.baselibrary.presenter.BasePresenter
import com.example.baselibrary.rx.BaseSubscriber
import com.example.usercenter.persenter.view.UserView
import com.example.usercenter.service.impl.UserServiceImpl
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class UserPersenter : BasePresenter<UserView>() {


    fun register() {

        val userServiceImpl = UserServiceImpl()
        userServiceImpl.register("", "")
            .execute(object :BaseSubscriber<Boolean>(){
                override fun onNext(t: Boolean?) {
                    mView.onResult(t!!)
                }
            })


    }

}