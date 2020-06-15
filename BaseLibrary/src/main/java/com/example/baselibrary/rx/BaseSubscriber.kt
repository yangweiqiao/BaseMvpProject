package com.example.baselibrary.rx

import com.example.baselibrary.presenter.view.BaseView
import rx.Subscriber

  abstract class BaseSubscriber<T>(private val baseView: BaseView) : Subscriber<T>() {

    override fun onCompleted() {
    }
    override fun onError(e: Throwable?) {
    }
}