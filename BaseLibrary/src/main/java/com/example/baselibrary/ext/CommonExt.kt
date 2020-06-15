package com.example.baselibrary.ext

import android.util.Log
import com.example.baselibrary.rx.BaseFunc
import com.example.baselibrary.data.protocol.BaseResp
import com.example.baselibrary.rx.BaseFunBoolean
import com.example.baselibrary.rx.BaseSubscriber
import com.trello.rxlifecycle.LifecycleProvider

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

fun <T> Observable<T>.execute(
    subscriber: BaseSubscriber<T>,
    lifecycleProvider: LifecycleProvider<*>
) {
    this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycleProvider.bindToLifecycle())
        .subscribe(subscriber)
}

/*
   扩展数据转换
*/
fun <T> Observable<BaseResp<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunc())
}
/*
    扩展Boolean类型数据转换
 */
fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BaseFunBoolean())
}
