package com.example.usercenter.ui.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.elvishew.xlog.XLog
import com.example.baselibrary.data.database.entry.UserEntry
import com.example.baselibrary.log.YLogUtils
import com.example.baselibrary.ui.activity.BaseMvpActivity
import com.example.provider.router.RouterPath
import com.example.usercenter.R
import com.example.usercenter.dagger.commponent.DaggerUserComponent
import com.example.usercenter.dagger.module.UserModule
import com.example.usercenter.data.bean.User
import com.example.usercenter.databinding.ActivityUserBinding
import com.example.usercenter.persenter.UserPresenter
import com.example.usercenter.persenter.view.UserView
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_user.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import kotlin.random.Random

@Route(path = RouterPath.UserCenter.PATH_REGIST)
class UserActivity : BaseMvpActivity<UserPresenter>(), UserView {

    private lateinit var binding: ActivityUserBinding


    override fun initComponent() {
        DaggerUserComponent.builder()
            .activityComponent(activityComponent)
            .userModule(UserModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        //  setContentView(R.layout.activity_user)
        mViewDatabase.onClick {
            val user = UserEntry()
            user.firstName = "yang"
            user.lastName = "rr"
            user.age = 11
            user.id = Random.nextInt()
            mPresenter.addUser(user)

            YLogUtils.json("{\"name\": \"测试\"}")
        }
//        mView.onClick {
//            mPresenter.register()
//
//
//        }

        binding.user = User("ywq", "1111111")
        binding.presenter = this
    }

    fun registClick(name: String, password: String) {


        mPresenter.register()
    }

    override fun onResult(b: Boolean) {
        toast("ok")
    }

    override fun onResultDatabase(it: List<Long>) {
        toast("操作数据库:" + it[0].toString())
        YLogUtils.e(it[0].toString())
    }
}