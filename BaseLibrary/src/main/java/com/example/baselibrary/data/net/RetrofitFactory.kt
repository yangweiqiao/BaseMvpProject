package com.example.baselibrary.data.net

import com.elvishew.xlog.XLog
import com.example.baselibrary.common.BASE_URL
import com.example.baselibrary.log.YLogUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * 单例模式的Retrofit
 * 1.构造方法私有
 */
class RetrofitFactory private constructor() {

    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)  //主机地址
            .addConverterFactory(GsonConverterFactory.create()) //数据转换
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(initClient())
            .build()
    }

    //构建client
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(initLogIntercept()) //日志拦截器
            .addInterceptor(initHeaderIntercept()) // 拦截器
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    //请求头 拦截器
    private fun initHeaderIntercept(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("charset", "utf-8")
                .build()
            chain.proceed(request)
        }
    }

    //构建日志拦截器
    private fun initLogIntercept(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor;
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

}