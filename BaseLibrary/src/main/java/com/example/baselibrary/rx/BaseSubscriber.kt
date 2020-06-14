package com.example.baselibrary.rx

import rx.Subscriber

  abstract class BaseSubscriber<T> : Subscriber<T>() {


    override fun onCompleted() {

    }

    override fun onError(e: Throwable?) {
    }
}