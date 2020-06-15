package com.basis.widget.indicator;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.kit.UIKit;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页的适配器
 *
 * @param <T>
 */
public class GuidPagerAdapter<T> extends PagerAdapter {
    private List<ImageView> viewList = new ArrayList<>(4);
    private String[] titles;
    List<T> datas;
    private OnItemClickListener mListener;

    public GuidPagerAdapter(String[] titles, List<T> imgList) {
        this.titles = titles;
        datas = imgList;
        createImageViews();
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
        if (object instanceof ImageView) {
            ImageView view = (ImageView) object;
            view.setBackground(null);
            view.setImageBitmap(null);
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
        final ImageView current = viewList.remove(0);
        final T data = datas.get(position);
        if (data instanceof Integer) {
            current.setBackgroundResource((Integer) data);
        } else if (data instanceof Drawable) {
            current.setBackground((Drawable) data);
        }
        container.addView(current);
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position, data);
                }
            }
        });
        return current;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
