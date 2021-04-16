package com.qunli.demo;

import android.widget.ImageView;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.basis.base.BaseActivity;
import com.bcq.adapter.RefreshAdapter;
import com.bcq.adapter.SampleAdapter;
import com.bcq.adapter.recycle.RcyHolder;
import com.bcq.refresh.IRefresh;
import com.bcq.refresh.XRecyclerView;
import com.kit.CommUtil;
import com.kit.UIKit;

import java.util.ArrayList;
import java.util.List;

public class IconActivity extends BaseActivity {

    private final static int[] icons = new int[]{
            R.drawable.icon_add_circle,
            R.drawable.icon_add,
            R.drawable.icon_back,
            R.drawable.icon_add_rect,
            R.drawable.icon_check_box,
            R.drawable.icon_clear,
            R.drawable.icon_clear_circle,
            R.drawable.icon_left,
            R.drawable.icon_right,
            R.drawable.icon_right_ok,
            R.drawable.icon_star,
            R.drawable.icon_uncheck_box,
            R.drawable.icon_unstar
    };
    private final static List<Integer> iconAs = new ArrayList<>();

    static {
        int len = icons.length;
        for (int i = 0; i < len; i++) {
            iconAs.add(icons[i]);
        }
    }

    RefreshAdapter adapter;


    @Override
    public int setLayoutId() {
        return R.layout.activity_icon;
    }

    @Override
    public void init() {
//        XRecyclerView refresh = getView(R.id.refresh);
        XRecyclerView refresh = UIKit.getFirstViewByClass(getLayout(),XRecyclerView.class,-1);
//        final GridLayoutManager layoutmanager = new GridLayoutManager(this, 4);
        StaggeredGridLayoutManager layoutmanager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        refresh.setLayoutManager(layoutmanager);
        adapter = new SampleAdapter<Integer, RcyHolder>(this, R.layout.item_icon) {
            @Override
            public void convert(RcyHolder iHolder, Integer o, int position, int layoutId) {
                ImageView view = iHolder.getView(R.id.image);
                view.setImageResource(o);
            }
        };
        adapter.setRefreshView(refresh);
        adapter.setData(iconAs, true);
    }
}
