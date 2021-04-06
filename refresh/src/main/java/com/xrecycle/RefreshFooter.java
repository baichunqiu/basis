package com.xrecycle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.spinkit.Indicator;
import com.spinkit.SpinKitView;
import com.spinkit.Style;


public class RefreshFooter extends LinearLayout implements Indicator {

    private SimpleViewSwitcher mProgressBar;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;

    private TextView mText;
    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;

    private Indicator indicatorView;

	public RefreshFooter(Context context) {
		super(context);
		initView();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public RefreshFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

    public void setLoadingHint(String hint) {
        loadingHint = hint;
    }

    public void setNoMoreHint(String hint) {
        noMoreHint = hint;
    }

    public void setLoadingDoneHint(String hint) {
        loadingDoneHint = hint;
    }

    @Override
    public void setStyle(Style style) {
        if (null == mProgressBar)return;
        if(style == Style.SYSTEM){
            mProgressBar.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        }else{
            if (null == indicatorView) indicatorView = new SpinKitView(getContext());
            indicatorView.setStyle(style);
            mProgressBar.setView((View) indicatorView);
        }
    }

    @Override
    public void setStyleIndex(int styleIndex) {
        if (styleIndex<0){
            setStyle(Style.SYSTEM);
        }else {
            setStyle(Style.values()[styleIndex % 15]);
        }
    }

    @Override
    public void setColor(int color) {
        if (null != indicatorView)indicatorView.setColor(color);
    }

    public void initView(){
        setGravity(Gravity.CENTER);
        setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mProgressBar = new SimpleViewSwitcher(getContext());
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        indicatorView = new SpinKitView(getContext());
        mProgressBar.setView((View) indicatorView);

        addView(mProgressBar);
        mText = new TextView(getContext());
        mText.setText(getContext().getString(R.string.listview_loading));

        if(loadingHint == null || loadingHint.equals("")){
            loadingHint = (String)getContext().getText(R.string.listview_loading);
        }
        if(noMoreHint == null || noMoreHint.equals("")){
            noMoreHint = (String)getContext().getText(R.string.nomore_loading);
        }
        if(loadingDoneHint == null || loadingDoneHint.equals("")){
            loadingDoneHint = (String)getContext().getText(R.string.loading_done);
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins( (int)getResources().getDimension(R.dimen.textandiconmargin),0,0,0 );

        mText.setLayoutParams(layoutParams);
        addView(mText);
    }

    public void  setState(int state) {
        switch(state) {
            case STATE_LOADING:
                mProgressBar.setVisibility(View.VISIBLE);
                mText.setText(loadingHint);
                this.setVisibility(View.VISIBLE);
                    break;
            case STATE_COMPLETE:
                mText.setText(loadingDoneHint);
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText(noMoreHint);
                mProgressBar.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }
}
