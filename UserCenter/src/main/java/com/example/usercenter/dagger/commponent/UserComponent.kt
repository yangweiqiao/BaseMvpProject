package com.example.usercenter.dagger.commponent

import com.example.baselibrary.injection.component.ActivityComponent
import com.example.baselibrary.injection.scope.FunScope
import com.example.usercenter.dagger.module.UserModule
import com.example.usercenter.ui.activity.UserActivity
import dagger.Component

@FunScope
@Component(modules = arrayOf(UserModule::class) ,dependencies = arrayOf(ActivityComponent::class))
interface UserComponent {
    fun inject(activity: UserActivity)
}