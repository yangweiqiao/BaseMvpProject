package com.example.baselibrary.injection.component

import android.content.Context
import com.example.baselibrary.injection.module.AppModule
import com.example.baselibrary.injection.scope.AppScope
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
 fun context(): Context
}