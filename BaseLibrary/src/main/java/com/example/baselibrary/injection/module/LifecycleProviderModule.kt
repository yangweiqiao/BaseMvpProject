package com.example.baselibrary.injection.module

import android.app.Activity
import android.app.Application
import com.example.baselibrary.injection.scope.ActivityScope
import com.trello.rxlifecycle.LifecycleProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class LifecycleProviderModule(private  val lifecycleProvider: LifecycleProvider<*>) {

    @Provides
    @ActivityScope
    fun providesLifecycleProvider( ):LifecycleProvider<*>{
        return lifecycleProvider
    }

}