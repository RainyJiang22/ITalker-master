package net.jacky.italker.push.frags.panel;

import android.os.Bundle;
import android.view.View;

import net.jacky.italker.common.widget.recycler.RecyclerAdapter;
import net.jacky.italker.face.Face;
import net.jacky.italker.push.R;

import java.util.List;

/**
 * @author jacky
 * @version 1.0.0
 */
public class FaceAdapter extends RecyclerAdapter<Face.Bean> {

    public FaceAdapter(List<Face.Bean> beans, AdapterListener<Face.Bean> listener) {
        super(beans, listener);
    }

    @Override
    protected int getItemViewType(int position, Face.Bean bean) {
        return R.layout.cell_face;
    }

    @Override
    protected ViewHolder<Face.Bean> onCreateViewHolder(View root, int viewType) {
        return new FaceHolder(root);
    }
}
