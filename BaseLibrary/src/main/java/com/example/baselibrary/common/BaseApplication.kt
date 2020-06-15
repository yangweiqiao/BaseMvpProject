package com.example.baselibrary.common

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselibrary.injection.component.AppComponent
import com.example.baselibrary.injection.component.DaggerAppComponent
import com.example.baselibrary.injection.module.AppModule

class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        //初始化arouter
        //ARouter初始化
      //  ARouter.openLog()    // 打印日志
       // ARouter.openDebug()
        ARouter.init(this)
        initAppComponent()

    }

      lateinit var appComponent: AppComponent
    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }

}