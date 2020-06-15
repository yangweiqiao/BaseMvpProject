package com.example.baselibrary.injection.component

import android.app.Activity
import android.content.Context
import com.example.baselibrary.injection.module.ActivityModule
import com.example.baselibrary.injection.module.AppModule
import com.example.baselibrary.injection.module.LifecycleProviderModule
import com.example.baselibrary.injection.scope.ActivityScope
import com.trello.rxlifecycle.LifecycleProvider
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScope
@Component(
    modules = arrayOf(ActivityModule::class, LifecycleProviderModule::class),
    dependencies = arrayOf(AppComponent::class)
)
interface ActivityComponent {

    fun activity(): Activity
    fun context(): Context
    fun lifecycleProvider(): LifecycleProvider<*>
}