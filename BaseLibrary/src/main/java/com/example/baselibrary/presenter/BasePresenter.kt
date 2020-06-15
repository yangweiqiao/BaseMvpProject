package com.example.baselibrary.presenter

import com.example.baselibrary.presenter.view.BaseView
import com.trello.rxlifecycle.LifecycleProvider
import javax.inject.Inject

open class BasePresenter<T : BaseView> {

    lateinit var mView: T
    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>
}