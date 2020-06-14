package com.example.kotlinapplication.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baselibrary.ui.activity.BaseMvpActivity
import com.example.kotlinapplication.R
import com.example.kotlinapplication.presenter.MinePresenter
import com.example.kotlinapplication.presenter.view.MineView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class MainActivity : BaseMvpActivity<MinePresenter>() ,MineView {
    override fun result(b: Boolean) {
        toast("toast$b")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MinePresenter()
        mPresenter.mView=this

        mTextView.onClick {

            mPresenter.test()

        }
    }
}
