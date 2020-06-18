package com.example.usercenter.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.example.usercenter.R;
import com.example.usercenter.databinding.ItemMovieBinding;
import com.example.usercenter.entry.Movie;

import org.jetbrains.annotations.NotNull;

/**
 * @author: limuyang
 * @date: 2019-12-05
 * @Description: DataBinding Adapter
 *
 */
public class DataBindingAdapter extends BaseQuickAdapter<Movie, BaseDataBindingHolder<ItemMovieBinding>> {

   // private MoviePresenter mPresenter = new MoviePresenter();

    public DataBindingAdapter() {
        super(R.layout.item_movie);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemMovieBinding> holder, Movie item) {
        // 获取 Binding
        ItemMovieBinding binding = holder.getDataBinding();
        if (binding != null) {
            binding.setMovie(item);
        //    binding.setPresenter(mPresenter);
            binding.executePendingBindings();
        }
    }
}
