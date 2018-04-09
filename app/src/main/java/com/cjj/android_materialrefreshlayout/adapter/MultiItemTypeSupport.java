package com.cjj.android_materialrefreshlayout.adapter;

/**
 * Created by lvr on 2017/5/24.
 */

public interface MultiItemTypeSupport<T>
{
    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);
}
