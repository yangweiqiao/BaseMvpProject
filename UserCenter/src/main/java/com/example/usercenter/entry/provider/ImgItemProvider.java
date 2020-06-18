package com.example.usercenter.entry.provider;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.usercenter.R;
import com.example.usercenter.databinding.ItemImageViewBinding;
import com.example.usercenter.entry.ProviderMultiEntity;

import org.jetbrains.annotations.NotNull;


/**
 * https://github.com/chaychan
 *
 * @author ChayChan
 * @description: Img ItemProvider
 * @date 2018/3/30  11:39
 */

public class ImgItemProvider extends BaseItemProvider<ProviderMultiEntity> {

    @Override
    public int getItemViewType() {
        return ProviderMultiEntity.IMG;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_image_view;
    }

    @Override
    public void onViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        super.onViewHolderCreated(viewHolder, viewType);
        // 绑定View
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, @Nullable ProviderMultiEntity data) {
        data.setSrc(R.drawable.brvah_sample_footer_loading_progress);
//        if (helper.getAdapterPosition() % 2 == 0) {
//            helper.setImageResource(R.id.iv, R.drawable.brvah_sample_footer_loading);
//        } else {
//            helper.setImageResource(R.id.iv, R.drawable.brvah_sample_footer_loading_progress);
//        }
        ItemImageViewBinding binding = DataBindingUtil.getBinding(helper.itemView);
//        ItemImageViewBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setData(data);
            binding.executePendingBindings();
        }


    }

    @Override
    public void onClick(@NonNull BaseViewHolder helper, @NotNull View view, ProviderMultiEntity data, int position) {

    }

    @Override
    public boolean onLongClick(@NotNull BaseViewHolder helper, @NotNull View view, ProviderMultiEntity data, int position) {

        return true;
    }
}
