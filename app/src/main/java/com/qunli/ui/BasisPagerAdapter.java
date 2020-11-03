package com.qunli.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.kit.UIKit;

import java.util.ArrayList;
import java.util.List;

public abstract class BasisPagerAdapter<T> extends PagerAdapter {
    private List<View> viewList = new ArrayList<>(4);
    private String[] titles;
    List<T> datas;
    private OnItemClickListener mListener;

    public BasisPagerAdapter(String[] titles, List<T> imgList) {
        this.titles = titles;
        createImageViews();
        datas = imgList;
    }

    private void createImageViews() {
        for (int i = 0; i < 4; i++) {
            viewList.add(new ImageView(UIKit.getContext()));
        }
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof View) {
            View view = (View) object;
            viewList.add(view);
            container.removeView(view);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View currentView = viewList.remove(0);
        final T data = datas.get(position);
        cover(currentView, data, position);
        container.addView(currentView);
        currentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position, data);
                }
            }
        });
        return currentView;
    }

    protected abstract void cover(View view, T t, int position);


    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T image);
    }
}
