package com.example.baselibrary.rx


import android.util.Log
import com.example.baselibrary.data.protocol.BaseException
import com.example.baselibrary.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

/*
    通用数据类型转换封装
 */
class BaseFunc<T>:Func1<BaseResp<T>,Observable<T>>{
    override fun call(t: BaseResp<T>): Observable<T> {
        //这个地方flatmap转换 ,决定 ,绑定后最终执行哪个error方法还是next方法;
        if (t.status  != 200){
            return Observable.error(BaseException(t.status, t.msg ))
        }

        return Observable.just(t.data)
    }
}
