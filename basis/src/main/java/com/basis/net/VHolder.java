package com.basis.net;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.basis.R;
import com.bcq.refresh.IRefresh;
import com.kit.CommUtil;
import com.kit.UIKit;
import com.kit.utils.Logger;

public class VHolder {
    protected final String TAG = "Controller # VHolder";
    protected View none;
    protected View show;
    protected IRefresh refresh;

    public enum Type {show, none}

    /**
     * 使用布局视图 自动填充
     *
     * @param layout
     */
    public VHolder(View layout) {
        this.refresh = UIKit.getView(layout, R.id.basis_refresh);
        this.show = UIKit.getView(layout, R.id.basis_show_content);
        this.none = UIKit.inflate(R.layout.basis_none);
        //未设置basis_refresh，以layout为跟节点，向下遍历两层视图树查找， 一般RefreshView的位置不会太深
        if (null == refresh) {
//            if (layout instanceof IRefresh) {
//                Logger.e(TAG,"根视图 IRefresh");
//                refresh = (IRefresh) layout;
//            } else if (layout instanceof ViewGroup) {
//                refresh = CommUtil.findChildByTypeCalss((ViewGroup) layout, IRefresh.class,1, -1);
//            }
            refresh = UIKit.getFirstViewByClass(layout, IRefresh.class,-1);
        }
        if (null == refresh) {
            throw new IllegalArgumentException("Can Not Find Refresh View, Maybe The Id You Set is Not 'basis_refresh',");
        }
        //添加no_data到show_data同级, 如未设置show_data布局 默认使用使用RefreshView
        if (null == show) show = (View) refresh;
        ViewGroup extraParent = null != show ? (ViewGroup) show.getParent() : (ViewGroup) layout;
        Logger.e(TAG,"extraParent:"+extraParent.getClass().getSimpleName());
        extraParent.addView(none, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    @Deprecated
    public VHolder(View show, View none, IRefresh refresh) {
        if (null == none || null == refresh) {
            throw new IllegalArgumentException("Views Show or None or Refresh Can Not Null !");
        }
        this.none = none;
        this.refresh = refresh;
        this.show = null != show ? show : (View) refresh;
    }

    public boolean check() {
        return null != none && null != show && null != refresh;
    }

    public final void showType(Type type) {
        reset();
        Logger.e(TAG, "showType: type = " + type);
        (Type.show == type ? show : none).setVisibility(View.VISIBLE);
    }

    public void reset() {
        show.setVisibility(View.GONE);
        none.setVisibility(View.GONE);
    }
}