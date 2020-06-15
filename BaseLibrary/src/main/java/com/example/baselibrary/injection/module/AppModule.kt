package com.example.baselibrary.injection.module

import android.app.Application
import android.content.Context
import com.example.baselibrary.common.BaseApplication
import com.example.baselibrary.injection.scope.AppScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private  val context: Context) {
    @Provides
    @Singleton
    fun providesContext( ):Context{
        return context
    }

}