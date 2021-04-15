package com.qunli.demo;

import androidx.recyclerview.widget.GridLayoutManager;

import com.bcq.adapter.RefreshAdapter;
import com.bcq.adapter.SampleAdapter;
import com.bcq.adapter.recycle.RcyHolder;
import com.basis.base.BaseActivity;
import com.bcq.refresh.progress.IndicatorView;
import com.bcq.refresh.progress.Style;
import com.bcq.refresh.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IndicatorActivity extends BaseActivity {

    RefreshAdapter adapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_view;
    }

    @Override
    public void init() {
        XRecyclerView refresh = getView(R.id.refresh);
        final GridLayoutManager layoutmanager = new GridLayoutManager(this, 4);
        refresh.setLayoutManager(layoutmanager);
        refresh.enableRefresh(false);
        refresh.enableLoad(false);
        adapter = new SampleAdapter<Integer, RcyHolder>(this, R.layout.item_indicator) {
            @Override
            public void convert(RcyHolder iHolder, Integer o, int position, int layoutId) {
                IndicatorView view = (IndicatorView) iHolder.getView(R.id.indicator);
                view.setStyle(Style.valueOf(o));
                iHolder.setText(R.id.info, "index = " + o);
            }
        };
        adapter.setRefreshView(refresh);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(i);
        }
        adapter.setData(list, false);
    }

}
