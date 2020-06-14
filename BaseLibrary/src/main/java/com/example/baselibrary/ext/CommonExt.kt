package com.example.baselibrary.ext

import com.example.baselibrary.rx.BaseSubscriber
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

fun <T> Observable<T>.execute(baseSubscriber: BaseSubscriber<T>) {
    this.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(baseSubscriber)
}