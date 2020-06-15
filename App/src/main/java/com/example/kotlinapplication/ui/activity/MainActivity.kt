package com.example.kotlinapplication.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselibrary.ui.activity.BaseActivity
import com.example.baselibrary.ui.activity.BaseMvpActivity
import com.example.kotlinapplication.R
import com.example.kotlinapplication.presenter.MinePresenter
import com.example.kotlinapplication.presenter.view.MineView
import com.example.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

//测试类  这里不测试mvp 只测试路由跳转  mvp dagger2 retrofit在usercenter中

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextView.onClick {
            println("点击事件 1111")
            ARouter.getInstance().build(RouterPath.UserCenter.PATH_REGIST).navigation()

        }
    }
}
