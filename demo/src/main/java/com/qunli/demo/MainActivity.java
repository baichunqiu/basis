package com.qunli.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import androidx.appcompat.app.AppCompatActivity;

import com.kit.UIKit;

public class MainActivity extends AppCompatActivity {

    @Override
    @Deprecated
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.recycle_complete :
            case R.id.recycle_sample:
                UIKit.startActivityByBasis(this, RecycleActivity.class,id == R.id.recycle_sample);
                break;
            case R.id.refresh_complete:
            case R.id.refresh_sample:
                UIKit.startActivityByBasis(this, RefreshActivity.class,id == R.id.refresh_sample);
                break;
            case R.id.adapter_lv:
            case R.id.adapter_recycle:
                UIKit.startActivityByBasis(this, AdapterActivity.class,id == R.id.adapter_lv);
                break;
        }
    }
}
