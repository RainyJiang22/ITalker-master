package net.qiujuer.italker.push.fragment.media;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.italker.common.tools.UiTool;
import net.qiujuer.italker.common.widget.GalleryView;
import net.qiujuer.italker.push.R;

/**
 * 图片选择Fragment
 */
public class GalleryFragment extends BottomSheetDialogFragment
    implements GalleryView.SelectedChangeListener{

    private GalleryView mGallery;
    private OnSelectedListener mListener;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //先使用默认的Dialog
        return new TransStateButtonSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View root =  inflater.inflate(R.layout.fragment_gallery, container, false);

      mGallery = (GalleryView) root.findViewById(R.id.galleryView);
       return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGallery.setup(getLoaderManager(),this);
    }

    @Override
    public void onSelectedCountChanged(int count) {

        //如果选中的一张图片
        if (count>0){
            //隐藏自己
            dismiss();
            if (mListener != null){
                //得到所有的选中的图片的路径
                String[] path = mGallery.getSelectedPath();
                //返回第一张
                mListener.onSelectedImage(path[0]);
                //取消和唤起之间的应用，加快内存回收
                mListener = null;
            }
        }
    }

    /**
     * 设置事件监听，并返回自己
     * @param listener
     * @return
     */
    public GalleryFragment setListener(OnSelectedListener listener){
        mListener = listener;
        return this;
    }

    /**
     * 选中图片的监听器
     */
    public interface OnSelectedListener {
        void onSelectedImage(String path);
    }


    private static class TransStateButtonSheetDialog extends BottomSheetDialog{

        public TransStateButtonSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStateButtonSheetDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        protected TransStateButtonSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            final Window window = getWindow();
            if (window == null)
                return;

            //拿到屏幕的高度
            int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
            //得到状态栏高度
            int statusHeight  = UiTool.getStatusBarHeight(getOwnerActivity());


            //实际高度 = 屏幕高度 - 状态栏高度
            int dialogHeight = screenHeight - statusHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    dialogHeight <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        }
    }

}
