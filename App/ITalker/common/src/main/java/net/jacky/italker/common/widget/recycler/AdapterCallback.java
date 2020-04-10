package net.jacky.italker.common.widget.recycler;

/**
 * @author jacky
 * @version 1.0.0
 */
public interface AdapterCallback<Data> {
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
