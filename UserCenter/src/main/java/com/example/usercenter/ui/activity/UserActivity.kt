package com.example.usercenter.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.data.database.entry.UserEntry
import com.example.baselibrary.ui.activity.BaseMvpActivity
import com.example.provider.router.RouterPath
import com.example.usercenter.R
import com.example.usercenter.dagger.commponent.DaggerUserComponent
import com.example.usercenter.dagger.module.UserModule
import com.example.usercenter.persenter.UserPresenter
import com.example.usercenter.persenter.view.UserView
import kotlinx.android.synthetic.main.user.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import kotlin.random.Random

@Route(path = RouterPath.UserCenter.PATH_REGIST)
class UserActivity : BaseMvpActivity<UserPresenter>(), UserView {
    override fun onResultDatabase(it: List<Long>) {
        toast("操作数据库:" + it[0].toString())

    }

    override fun initComponent() {
        DaggerUserComponent.builder()
            .activityComponent(activityComponent)
            .userModule(UserModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onResult(b: Boolean) {
        toast("ok")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user)
        mViewDatabase.onClick {
            val user = UserEntry()
            user.firstName = "yang"
            user.lastName = "rr"
            user.age = 11
            user.id = Random.nextInt()
            mPresenter.addUser(user)

        }
        mView.onClick {
            mPresenter.register()



        }

    }


}