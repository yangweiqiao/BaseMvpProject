package com.example.baselibrary.rx

import android.util.Log
import com.example.baselibrary.data.protocol.BaseException
import com.example.baselibrary.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

class BaseFunBoolean<T> : Func1<BaseResp<T>, Observable<Boolean>> {

    override fun call(t: BaseResp<T>): Observable<Boolean> {
        if (t.status == 200) {
            return Observable.just(true)
        }
        return Observable.error(BaseException(t.status, t.msg))
    }
}