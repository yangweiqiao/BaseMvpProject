package com.example.baselibrary.common

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.example.baselibrary.BuildConfig
import com.example.baselibrary.data.database.database.DatabaseUtils
import com.example.baselibrary.injection.component.AppComponent
import com.example.baselibrary.injection.component.DaggerAppComponent
import com.example.baselibrary.injection.module.AppModule
import com.example.baselibrary.log.YLogUtils

class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        //初始化arouter
        //ARouter初始化
        //  ARouter.openLog()    // 打印日志
        // ARouter.openDebug()
        ARouter.init(this)
        initAppComponent()
        DatabaseUtils.createDataBase()
        //日志
        YLogUtils.initLog()

    }

    lateinit var appComponent: AppComponent
    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }

}