package com.example.baselibrary.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.baselibrary.log.YLogUtils
import com.example.baselibrary.utils.DensityUtil
import org.jetbrains.anko.collections.forEachWithIndex


class BottomNavBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationBar(context, attrs, defStyleAttr) {

    /**
     * 初始化方法
     */
//    init {
//        val homeItem = BottomNavigationItem(R.drawable.home, "首页")
//            .setInactiveIconResource(R.drawable.home_normal)
//            .setActiveColorResource(R.color.colorPrimary)
//            .setInActiveColorResource(R.color.colorPrimaryDark)
//        val homeItem2 = BottomNavigationItem(R.drawable.home, "首页2")
//            .setInactiveIconResource(R.drawable.home_normal)
//            .setActiveColorResource(R.color.colorPrimary)
//            .setInActiveColorResource(R.color.colorPrimaryDark)
//        val homeItem3 = BottomNavigationItem(R.drawable.home, "首页3")
//            .setInactiveIconResource(R.drawable.home_normal)
//            .setActiveColorResource(R.color.colorPrimary)
//            .setInActiveColorResource(R.color.colorPrimaryDark)
//        val homeItem4 = BottomNavigationItem(R.drawable.home, "首页4")
//            .setInactiveIconResource(R.drawable.home_normal)
//            .setActiveColorResource(R.color.colorPrimary)
//            .setInActiveColorResource(R.color.colorPrimaryDark)
//
//        this.setMode(BottomNavigationBar.MODE_FIXED)
//            .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
//            .setBarBackgroundColor(R.color.colorAccent)
//            .addItem(homeItem)
//            .addItem(homeItem2)
//            .addItem(homeItem3)
//            .addItem(homeItem4)
//            .setFirstSelectedPosition(0)
//            .initialise()
//    }

    private var iconSelectList = ArrayList<Int>()
    private var iconUnSelectList = ArrayList<Int>()
    private var titles = ArrayList<String>()
    private var activeColors = com.example.baselibrary.R.color.colorPrimary
    private var inActiveColors = com.example.baselibrary.R.color.colorText
    private var backgroundColors = com.example.baselibrary.R.color.colorWhite

    /**
     * 传入图标 ，选中的和未选中的 ，未选中的可以不传，系统颜色默认
     */
    fun setIcons(
        iconSelectList: ArrayList<Int>,
        iconUnSelectList: ArrayList<Int> = ArrayList<Int>()
    ): BottomNavBar {
        this.iconSelectList = iconSelectList
        this.iconUnSelectList = iconUnSelectList
        return this
    }

    fun setTitles(titles: ArrayList<String>): BottomNavBar {
        this.titles = titles
        return this
    }

    fun setActiveColors(activeColors: Int): BottomNavBar {
        this.activeColors = activeColors
        return this
    }

    fun setInActiveColors(inActiveColors: Int): BottomNavBar {
        this.inActiveColors = inActiveColors
        return this
    }

    fun setBackgroundColors(backgroundColors: Int): BottomNavBar {
        this.backgroundColors = backgroundColors
        return this
    }

    fun build() {
        //判断标题
        if (titles.size == 0) {
            YLogUtils.e("BottomNavBar定义的标题有问题")
            return
        }
        if (iconSelectList.size == 0) {
            YLogUtils.e("BottomNavBar定义的图标有问题")
            return
        }

        if (iconUnSelectList.size == 0) {
            titles.forEachWithIndex { index, title ->
                addItem(
                    BottomNavigationItem(iconSelectList[index], title)
                        .setActiveColorResource(activeColors)
                        .setInActiveColorResource(inActiveColors)
                )
            }
        } else {
            if (iconSelectList.size != iconUnSelectList.size) {
                YLogUtils.e("BottomNavBar自定义了未选中的图标，但是和选中的图标数量不一致")
                return
            } else {
                titles.forEachWithIndex { index, title ->
                    addItem(
                        BottomNavigationItem(iconSelectList[index], title)
                            .setInactiveIconResource(iconUnSelectList[index])
                            .setActiveColorResource(activeColors)
                            .setInActiveColorResource(inActiveColors)

                    )
                }
            }
        }


        this.setMode(BottomNavigationBar.MODE_FIXED)
            .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
            .setBarBackgroundColor(backgroundColors)
            .setFirstSelectedPosition(0)

            .initialise()
        //控制图片和文字大小
        setBottomNavigationItem(this, 5.0f, 24.0f)
    }

    private fun setBottomNavigationItem(bar: BottomNavBar, space: Float, imgLen: Float) {
        val contentLen = 36f
        val barClass = bar.javaClass.superclass
        val fields = barClass!!.getDeclaredFields()
        for (i in fields.indices) {
            val field = fields[i]
            field.setAccessible(true)
            if (field.getName().equals("mTabContainer")) {
                try { //反射得到 mTabContainer
                    val mTabContainer = field.get(bar) as LinearLayout
                    for (j in 0 until mTabContainer.childCount) {
                        //获取到容器内的各个 Tab
                        val view = mTabContainer.getChildAt(j)
                        //获取到Tab内的各个显示控件
                        // 获取到Tab内的文字控件
                        val labelView =
                            view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title) as TextView
                        //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                        labelView.setTextSize(
                            TypedValue.COMPLEX_UNIT_DIP,
                            (Math.sqrt(2.0) * (contentLen - imgLen - space)).toFloat()
                        )
                        //获取到Tab内的图像控件
                        val iconView =
                            view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon) as ImageView
                        //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                        val params = FrameLayout.LayoutParams(
                            DensityUtil.dp2px(this.context, imgLen),
                            DensityUtil.dp2px(this.context, imgLen)
                        )
                        params.gravity = Gravity.CENTER
                        iconView.layoutParams = params
                    }
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

            }
        }
    }
}