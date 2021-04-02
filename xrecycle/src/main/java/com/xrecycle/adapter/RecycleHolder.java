package com.xrecycle.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: BaiCQ
 * @createTime: 2017/2/26 15:08
 * @className: ViewHolder
 * @Description: 通用ViewHolder
 */
public class RecycleHolder extends RecyclerView.ViewHolder implements IHolder<RecycleHolder>{
    private SparseArray<View> viewArrays;

    public RecycleHolder(View rootView) {
        super(rootView);
        viewArrays = new SparseArray();
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    @Override
    public <T extends View> T getView(int viewId) {
        View view = viewArrays.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewArrays.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public View rootView() {
        return itemView;
    }

    @SuppressLint("NewApi")
    @Override
    public RecycleHolder setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    @Override
    public RecycleHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public RecycleHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    @Override
    public RecycleHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    @Override
    public RecycleHolder setSelected(int viewId, boolean selected) {
        View view = getView(viewId);
        view.setSelected(selected);
        return this;
    }

    @Override
    public RecycleHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    @Override
    public RecycleHolder setTextColor(int viewId, int color) {
        TextView view = getView(viewId);
        view.setTextColor(color);
        return this;
    }

    @Override
    public RecycleHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

//    @Override
//    public RecycleHolder loadUrl(int viewId, String imageUrl, int def) {
//        ImageView intoView = getView(viewId);
//        ImageLoader.loadUrl(intoView, imageUrl, def, ImageLoader.Size.SZ_250);
//        return this;
//    }

    @Override
    public RecycleHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    @Override
    public RecycleHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    @Override
    public RecycleHolder setBackgroundResource(int viewId, int resource) {
        View view = getView(viewId);
        view.setBackgroundResource(resource);
        return this;
    }
}
