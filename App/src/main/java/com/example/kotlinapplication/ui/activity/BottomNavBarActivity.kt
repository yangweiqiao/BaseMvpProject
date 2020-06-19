package com.example.kotlinapplication.ui.activity

import android.os.Bundle
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.example.baselibrary.log.YLogUtils
import com.example.baselibrary.ui.activity.BaseActivity
import com.example.kotlinapplication.R
import kotlinx.android.synthetic.main.activity_bar.*

//测试类
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
