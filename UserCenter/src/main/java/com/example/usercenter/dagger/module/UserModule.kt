package com.example.usercenter.dagger.module

import com.example.baselibrary.injection.scope.FunScope
import com.example.usercenter.service.UserService
import com.example.usercenter.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UserModule {

    @Provides
    @FunScope
    fun providesuserService(service: UserServiceImpl): UserService {
        return service
    }
}