package com.jacky.common.widget.recycler;

/**
 * created by jiangshiyu
 * date: 2020/1/14
 */
public interface AdapterCallBack<Data> {
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}