package com.example.usercenter.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baselibrary.ui.activity.BaseMvpActivity
import com.example.usercenter.R
import com.example.usercenter.persenter.UserPersenter
import com.example.usercenter.persenter.view.UserView
import kotlinx.android.synthetic.main.user.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class UserActivity : BaseMvpActivity<UserPersenter>(), UserView {
    override fun onResult(b: Boolean) {
        println("测试结果:"+b)
        toast("ok")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user)
        mPresenter = UserPersenter()
        mPresenter.mView = this
        mView.onClick {
            mPresenter.register()

        }

    }


}