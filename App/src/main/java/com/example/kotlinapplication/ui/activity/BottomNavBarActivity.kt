package com.example.kotlinapplication.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.example.baselibrary.log.YLogUtils
import com.example.baselibrary.ui.activity.BaseActivity
import com.example.baselibrary.ui.activity.BaseMvpActivity
import com.example.kotlinapplication.R
import com.example.kotlinapplication.databinding.ActivityMainBinding
import com.example.kotlinapplication.presenter.MinePresenter
import com.example.kotlinapplication.presenter.view.MineView
import com.example.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_bar.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

//测试类  这里不测试mvp 只测试路由跳转  mvp dagger2 retrofit在usercenter中

class BottomNavBarActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar)
        var iconSelectList = ArrayList<Int>()
        var iconUnSelectList = ArrayList<Int>()
        var titles = ArrayList<String>()

        iconSelectList.add(R.drawable.home)
        iconSelectList.add(R.drawable.home)
        iconSelectList.add(R.drawable.home)
        iconSelectList.add(R.drawable.home)
        iconUnSelectList.add(R.drawable.home_normal)
        iconUnSelectList.add(R.drawable.home_normal)
        iconUnSelectList.add(R.drawable.home_normal)
        iconUnSelectList.add(R.drawable.home_normal)
        titles.add("home")
        titles.add("find")
        titles.add("covert")
        titles.add("mine")


        mBottomNavBar.setIcons(iconSelectList, iconUnSelectList)
            // .setIconUnSelectList(iconUnSelectList)
            .setTitles(titles)
            .build()
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
                YLogUtils.e("onTabReselected:" + position)
            }

            override fun onTabUnselected(position: Int) {
                YLogUtils.e("onTabUnselected:" + position)
            }

            override fun onTabSelected(position: Int) {

                YLogUtils.e("onTabSelected:" + position)
            }

        })
    }


}
