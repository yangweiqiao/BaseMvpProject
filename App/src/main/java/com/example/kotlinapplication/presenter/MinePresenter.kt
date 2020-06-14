package com.example.kotlinapplication.presenter

import com.example.baselibrary.presenter.BasePresenter
import com.example.kotlinapplication.presenter.view.MineView

class MinePresenter : BasePresenter<MineView>() {
    fun test() {

        mView.result(true)
    }
}