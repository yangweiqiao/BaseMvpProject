package com.example.usercenter.entry.provider;

import android.database.DatabaseUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.usercenter.R;
import com.example.usercenter.databinding.ItemTextViewBinding;
import com.example.usercenter.entry.ProviderMultiEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * https://github.com/chaychan
 *
 * @author ChayChan
 * @description: Text ItemProvider
 * @date 2018/3/30  11:39
 */

public class TextItemProvider extends BaseItemProvider<ProviderMultiEntity> {
    @Override
    public int getItemViewType() {
        return ProviderMultiEntity.TEXT;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_text_view;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @Nullable ProviderMultiEntity data) {

        ItemTextViewBinding binding = DataBindingUtil.getBinding(helper.itemView);
        binding.setData(data);
        binding.executePendingBindings();
//        helper.setText(R.id.tv, data.getName());
    }

    @Override
    public void onViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        super.onViewHolderCreated(viewHolder, viewType);
        // 绑定View
        DataBindingUtil.bind(viewHolder.itemView);
    }


    @Override
    public void onClick(@NonNull BaseViewHolder helper, @NotNull View view, ProviderMultiEntity data, int position) {

    }

    @Override
    public boolean onLongClick(@NotNull BaseViewHolder helper, @NotNull View view, ProviderMultiEntity data, int position) {

        return true;
    }
}
