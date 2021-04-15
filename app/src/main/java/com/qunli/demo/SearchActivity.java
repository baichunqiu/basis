package com.qunli.demo;

import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.basis.base.BaseActivity;
import com.basis.widget.SearchEditText;
import com.basis.widget.SearchLayout;
import com.bcq.adapter.RefreshAdapter;
import com.bcq.adapter.SampleAdapter;
import com.bcq.adapter.recycle.RcyHolder;
import com.bcq.refresh.XRecyclerView;
import com.bcq.refresh.progress.IndicatorView;
import com.bcq.refresh.progress.Style;
import com.kit.utils.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    RefreshAdapter adapter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        initSearchLayout();
//        initSearchView();
        XRecyclerView refresh = getView(R.id.refresh);
        final GridLayoutManager layoutmanager = new GridLayoutManager(this, 4);
        refresh.setLayoutManager(layoutmanager);
//        refresh.enableRefresh(false);
//        refresh.enableLoad(false);
        adapter = new SampleAdapter<Integer, RcyHolder>(this, R.layout.item_indicator) {
            @Override
            public void convert(RcyHolder iHolder, Integer o, int position, int layoutId) {
                IndicatorView view = (IndicatorView) iHolder.getView(R.id.indicator);
                view.setStyle(Style.valueOf(o));
                iHolder.setText(R.id.info, "index = " + o);
            }
        };
        adapter.setRefreshView(refresh);
        refresh(100);
    }

    private void refresh(int max) {
        if (max > 28) max = 28;
        Logger.e(TAG, "max:" + max);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            list.add(i);
        }
        adapter.setData(list, true);
    }

    private void initSearchLayout() {
        SearchLayout searchLayout = getView(R.id.search_layout);
        searchLayout.setVisibility(View.VISIBLE);
        searchLayout.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchLayout.hideSearchButton(true);
        searchLayout.setOnSearchListener(new SearchEditText.OnSearchListener() {
            @Override
            public void onSearch(String search) {
                Logger.e(TAG, "onSearch:" + search);
                if (!TextUtils.isEmpty(search)) {
                    int i = Integer.valueOf(search);
                    refresh(i);
                }
            }
        });
    }

    private void initSearchView() {
        SearchView mSearchView = getView(R.id.search);
        mSearchView.setVisibility(View.VISIBLE);
        mSearchView.setIconifiedByDefault(false);//设置搜索图标是否显示在搜索框内
        //1:回车
        //2:前往
        //3:搜索
        //4:发送
        //5:下一項
        //6:完成
//        mSearchView.setImeOptions(2);//设置输入法搜索选项字段，默认是搜索，可以是：下一页、发送、完成等
//        mSearchView.setInputType(1);//设置输入类型
//        mSearchView.setMaxWidth(200);//设置最大宽度
        mSearchView.setQueryHint("搜索");//设置查询提示字符串
//        mSearchView.setSubmitButtonEnabled(true);//设置是否显示搜索框展开时的提交按钮
        //设置SearchView下划线透明
        setUnderLinetransparent(mSearchView);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Logger.e(TAG, "=====query=" + query);
                return false;
            }

            //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                Logger.e(TAG, "=====newText=" + newText);
                return false;
            }
        });
    }

    private void setUnderLinetransparent(SearchView searchView) {
        try {
            Class<?> argClass = searchView.getClass();
            // mSearchPlate是SearchView父布局的名字
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            ownField.setAccessible(true);
            View mView = (View) ownField.get(searchView);
            mView.setBackgroundColor(Color.TRANSPARENT);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
