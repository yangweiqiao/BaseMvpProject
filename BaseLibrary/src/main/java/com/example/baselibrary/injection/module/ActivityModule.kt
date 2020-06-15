package com.example.baselibrary.injection.module

import android.app.Activity
import android.app.Application
import com.example.baselibrary.injection.scope.ActivityScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ActivityModule(private  val activity: Activity) {

    @Provides
    @ActivityScope
    fun providesActivity( ):Activity{
        return activity
    }

}