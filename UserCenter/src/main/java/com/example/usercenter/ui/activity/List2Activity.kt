package com.example.usercenter.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.ui.activity.BaseActivity
import com.example.provider.router.RouterPath
import com.example.usercenter.R
import com.example.usercenter.adapter.ProviderMultiAdapter
import com.example.usercenter.databinding.ActivityUserBinding
import com.example.usercenter.entry.ProviderMultiEntity
import kotlinx.android.synthetic.main.activity_list.*
import java.util.ArrayList

@Route(path = RouterPath.UserCenter.PATH_LIST2)
class List2Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ProviderMultiAdapter()
        mRecyclerView.adapter = adapter
        adapter.addData(genData())
    }

    private fun genData(): List<ProviderMultiEntity> {
        val list = ArrayList<ProviderMultiEntity>()
        val random = java.util.Random()
        for (i in 0..22) {
            val name = "Chad $i"
            val price = random.nextInt(10) + 10
            val len = random.nextInt(80) + 60
            val movie = ProviderMultiEntity()
            movie.name = name
            list.add(movie)
        }
        return list
    }

}