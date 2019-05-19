package net.qiujuer.italker.push.fragment.main;


import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.widget.GalleryView;
import net.qiujuer.italker.push.R;
import butterknife.BindView;


public class ActiveFragment extends Fragment {

    @BindView(R.id.galleryView)
     GalleryView mGallery;

    public ActiveFragment() {
        // Required empty public constructor
    }
    
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initData() {
        super.initData();

        mGallery.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });
    }
}
