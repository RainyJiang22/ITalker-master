package net.qiujuer.italker.push.fragment.account;



import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.italker.common.app.Application;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.widget.PortraitView;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.fragment.media.GalleryFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 *更新用户信息的界面
 */
public class UpdateInfoFragment extends Fragment {


    @BindView(R.id.im_protrait)
    PortraitView mPortrait;

    public UpdateInfoFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }


    @OnClick(R.id.im_protrait)
    void onPortraitClick(){
        new GalleryFragment()
                .setListener(new GalleryFragment.OnSelectedListener() {
                    @Override
                    public void onSelectedImage(String path) {
                        UCrop.Options options = new UCrop.Options();
                        //设置图片处理的格式JPEG
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                        //设置压缩后图片的精度
                        options.setCompressionQuality(96);


                        //得到头像的缓存地址
                        File dPath = Application.getPortraitTmpFile();;

                        UCrop.of(Uri.fromFile(new File(path)),Uri.fromFile(dPath))
                                .withAspectRatio(1,1)  //1：1比例
                                .withMaxResultSize(520,520)//返回最大参数
                                .withOptions(options)   //相关参数
                                .start(getActivity());

                    }
                    //show的时候建议使用ChildFragmentManager
                    //tag:class的名字
                }).show(getChildFragmentManager(),GalleryFragment.class.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //收到从Activity传递过来的回调，然后取出图片的值进行加载
        //如果是能够处理的类型，通过
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
           // 得到对应的uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null){
                 loadPortrait(resultUri);
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 加载Uri到当前的图片中
     * @param uri
     */
    private void loadPortrait(Uri uri){
        Glide.with(this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);
    }
}
