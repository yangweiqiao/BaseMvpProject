package com.example.baselibrary.ext

import com.example.baselibrary.data.protocol.BaseResp
import com.example.baselibrary.presenter.view.BaseView
import com.example.baselibrary.rx.BaseFunBoolean
import com.example.baselibrary.rx.BaseFunc
import com.trello.rxlifecycle.LifecycleProvider
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

fun <T> Observable<T>.execute(
    mView: BaseView,
    lifecycleProvider: LifecycleProvider<*>,
    onSuccess: (data: T) -> Unit
    , onFailed: (e: Throwable) -> Unit
) {
    this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycleProvider.bindToLifecycle())
        .subscribe(object : Subscriber<T>() {
            override fun onNext(t: T) {
                onSuccess(t)
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
                mView.onError(e!!)
            }
        })
}

/**
 * 数据库一次性查询结果扩展方法，目的是处理数据库查询返回的Single结果
 * 有结果调用onSuccess，空结果或查询出错一律调用onFailed
 */
/*fun <T> Single<T>.subscribeDbResult(onSuccess: (data: T) -> Unit, onFailed: (e: Throwable) -> Unit) {
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object :Subscriber<T>() {
            override fun onNext(t: T) {
                onSuccess(t)
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {

            }
        })
}*/

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
