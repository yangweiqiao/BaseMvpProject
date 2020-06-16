package com.example.baselibrary.presenter.view

interface BaseView {

    fun  onShowLoading()
    fun  onHideLoading()
    fun  onError(e:Throwable)

}