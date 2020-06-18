package com.example.usercenter.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.ui.activity.BaseActivity
import com.example.provider.router.RouterPath
import com.example.usercenter.R
import com.example.usercenter.adapter.DataBindingAdapter
import com.example.usercenter.databinding.ActivityUserBinding
import com.example.usercenter.entry.Movie
import kotlinx.android.synthetic.main.activity_list.*
import java.util.ArrayList

@Route(path = RouterPath.UserCenter.PATH_LIST)
class ListActivity : BaseActivity() {

    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = DataBindingAdapter()
        mRecyclerView.adapter = adapter
        adapter.setList(genData())
    }

    private fun genData(): List<Movie> {
        val list = ArrayList<Movie>()
        val random = java.util.Random()
        for (i in 0..9) {
            val name = "Chad $i"
            val price = random.nextInt(10) + 10
            val len = random.nextInt(80) + 60
            val movie =
                Movie(
                    name,
                    len,
                    price,
                    "He was one of Australia's most distinguished artistes"
                )
            list.add(movie)
        }
        return list
    }

}