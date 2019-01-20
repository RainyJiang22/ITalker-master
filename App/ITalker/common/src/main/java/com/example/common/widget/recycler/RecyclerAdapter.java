package com.example.common.widget.recycler;


        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.common.R;

        import java.util.ArrayList;
        import java.util.Collection;
        import java.util.Collections;
        import java.util.List;

        import butterknife.ButterKnife;
        import butterknife.Unbinder;

public abstract class RecyclerAdapter<Data> extends
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>> implements View.OnClickListener,
        View.OnLongClickListener,AdapterCallback<Data>{
    private final List<Data> mDataList;
    private AdapterListener<Data> mListener;
    

    /**
     * 构造函数模块
     */
    public RecyclerAdapter(){
         this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> listener){

        this(new ArrayList<Data>(),listener);
    }

    public RecyclerAdapter(List<Data> dataList,AdapterListener<Data> listener){
        this.mDataList = dataList;
        this.mListener = listener;
    }

    /**
     * 复写默认的类型布局返回
     * @param position 坐标
     * @return 类型，其实复写后返回的都是XML文件的ID
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position,mDataList.get(position));
    }

    /**
     * 得到布局的类型
     * @param position 坐标
     * @param data 当前的数据
     * @return XML文件的ID，用于创建ViewHolder
     */
    protected abstract int  getItemViewType(int position,Data data);

    /**
     * 创建一个ViewHolder
     * @param viewGroup  RecyclerView
     * @param viewType   界面的类型,约定为XML布局的id
     * @return  ViewHolder
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder( ViewGroup viewGroup, int viewType) {
        //得到LayoutInflater用于XML初始化view
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //把XML id为viewType的为文件初始化为一个root View
        View root = inflater.inflate(viewType,viewGroup,false);
        //通过子类必须实现的方法，得到一个ViewHolder
        ViewHolder<Data> holder = onCreateViewHolder(root,viewType);


        //设置View的Tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder,holder);

        //设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);


        //进行界面注解绑定
        holder.unbinder =   ButterKnife.bind(holder,root);
        //绑定callback
        holder.callback = this;

        return null;
    }



    /**
     * 得到一个新的ViewHolder
     * @param root 根布局
     * @param viewType  布局类型，其实就是xml的id
     * @return
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root,int viewType);

    /**
     * 绑定数据到一个Holder上
     * @param dataViewHolder ViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> dataViewHolder, int position) {
        //得到需要绑定的数据
        Data data = mDataList.get(position);
        //触发Holder的绑定方法
        dataViewHolder.bind(data);
    }

    /**
     * 得到当前集合的数据量
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一个数据并通知插入
     * @param data
     */
    public void add(Data data){
        mDataList.add(data);
        //notifyDataSetChanged(); //通知更新
        notifyItemInserted(mDataList.size() -1);
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     * @param dataList
     */
    public void add(Data... dataList){
        if (dataList!= null && dataList.length > 0){
            int startPos = mDataList.size();
            Collections.addAll(mDataList,dataList);
            notifyItemRangeInserted(startPos,dataList.length);
        }
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     * @param dataList
     */
    public void add(Collection<Data> dataList){
        if (dataList!= null && dataList.size() > 0){
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(startPos,dataList.size());
        }
    }

    /**
     * 删除操作
     */
    public void clear(){
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换为一个新的集合，其中包括了清空
     * 一个新的集合
     * @param dataList
     */
    public void replace(Collection<Data> dataList){
      mDataList.clear();
      if (dataList == null || dataList.size() == 0)
          return;
      mDataList.addAll(dataList);
      notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null){
            //得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回调方法
            this.mListener.onItemClick(viewHolder,mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null){
            //得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回调方法
            this.mListener.onItemLongClick(viewHolder,mDataList.get(pos));
            return true;
        }
        return false;
    }

    /**
     * 设置适配器的监听
     * @param adapterListener AdapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener){
        this.mListener = adapterListener;
    }

    /**
     * 我们自定义的监听器
     * @param <Data> 范型
     */
    public interface AdapterListener<Data>{
        //当Cell点击的时候触发
        void onItemClick(RecyclerAdapter.ViewHolder holder,Data data);

        //当Cell长按时候触发
        void onItemLongClick(RecyclerAdapter.ViewHolder holder,Data data);
    }

    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder{
        private Unbinder unbinder;
        private AdapterCallback<Data> callback;
        protected Data mData;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         *用于绑定数据的触发
         * @param data
         */
        void bind(Data data){
            this.mData = data;
            onBind(data);
        }


        /**
         * 当触发绑定数据的时候的回调，必须复写
         * @param data 绑定的数据
         */
        protected abstract void onBind(Data data);

        /**
         * Holder自己对自己的data进行更新操作
         * @param data Data数据
         */
        public void updateData(Data data){
            if (this.callback != null){
                this.callback.update(data,this);
            }
        }
    }
}
