package com.example.baselibrary.presenter

import com.example.baselibrary.presenter.view.BaseView

open class BasePresenter<T : BaseView> {

    lateinit var mView: T
}