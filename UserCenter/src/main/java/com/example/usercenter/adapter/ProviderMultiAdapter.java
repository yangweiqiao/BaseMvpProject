package com.example.usercenter.adapter;

import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.example.usercenter.entry.ProviderMultiEntity;
import com.example.usercenter.entry.provider.ImgItemProvider;
import com.example.usercenter.entry.provider.TextItemProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProviderMultiAdapter extends BaseProviderMultiAdapter<ProviderMultiEntity> {

    public ProviderMultiAdapter() {
        super();
        addItemProvider(new ImgItemProvider());
     //   addItemProvider(new TextImgItemProvider());
        addItemProvider(new TextItemProvider());
    }

    /**
     * 自行根据数据、位置等内容，返回 item 类型
     */
    @Override
    protected int getItemType(@NotNull List<? extends ProviderMultiEntity> data, int position) {
        switch (position % 2) {
            case 0:
                return ProviderMultiEntity.IMG;
            case 1:
                return ProviderMultiEntity.TEXT;
//            case 2:
//                return ProviderMultiEntity.IMG_TEXT;
            default:
                break;
        }
        return 0;
    }
}