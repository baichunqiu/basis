package com.qunli.demo;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.basis.base.BaseActivity;
import com.kit.UIKit;

public class SamepleFragmentActivity extends BaseActivity {

    @Override
    public int setLayoutId() {
        return R.layout.activity_sample_fragment;
    }

    @Override
    public void init() {
        getWrapBar().setTitle(R.string.str_fragment).work();
        boolean recycle = getIntent().getBooleanExtra(UIKit.KEY_BASE, false);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, recycle ? new RcyListFragment() : new LvListFragment());
        transaction.commitNow();
    }

}