package com.xrecycle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.progress.IndicatorView;
import com.progress.Style;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArrowRefreshHeader extends LinearLayout implements BaseRefreshHeader {
    private LinearLayout mContainer;
    private ImageView mArrowImageView;
    private SimpleViewSwitcher mProgressBar;
    private TextView mStatusTextView;
    private int mState = STATE_NORMAL;

    private TextView mHeaderTimeView;
    private LinearLayout mHeaderRefreshTimeContainer;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private static final int ROTATE_ANIM_DURATION = 180;

    public int mMeasuredHeight;
    private IndicatorView progressView;

    public void destroy() {
        mProgressBar = null;
        if (progressView != null) {
            progressView.destroy();
            progressView = null;
        }
        if (mRotateUpAnim != null) {
            mRotateUpAnim.cancel();
            mRotateUpAnim = null;
        }
        if (mRotateDownAnim != null) {
            mRotateDownAnim.cancel();
            mRotateDownAnim = null;
        }
    }

    public ArrowRefreshHeader(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public ArrowRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void setRefreshTimeVisible(boolean show) {
        if (mHeaderRefreshTimeContainer != null)
            mHeaderRefreshTimeContainer.setVisibility(show ? VISIBLE : GONE);
    }

    private void initView() {
        // 初始情况，设置下拉刷新view高度为0
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                R.layout.re_listview_header, null);

        mHeaderRefreshTimeContainer
                = (LinearLayout) mContainer.findViewById(R.id.header_refresh_time_container);

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);

        mArrowImageView = (ImageView) findViewById(R.id.listview_header_arrow);
        mStatusTextView = (TextView) findViewById(R.id.refresh_status_textview);

        //init the progress view
        mProgressBar = (SimpleViewSwitcher) findViewById(R.id.listview_header_progressbar);
//        progressView = new IndicatorView(getContext());
//        progressView.setColor(0xffB5B5B5);
//        progressView.setStyle(Style.BallSpinFadeLoader);
        createIndicator();
        if (mProgressBar != null)
            mProgressBar.setView(progressView);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

        mHeaderTimeView = (TextView) findViewById(R.id.last_refresh_time);
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    public void setProgressStyle(Style style) {
        if (style == Style.Sys) {
            if (mProgressBar != null)
                mProgressBar.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        } else {
            createIndicator();
            progressView.setStyle(style);
            mProgressBar.setView(progressView);
        }
    }

    private void createIndicator(){
        if (progressView == null){
            progressView = (IndicatorView) LayoutInflater.from(getContext()).inflate(R.layout.re_default_indicator,null,false);
        }
    }
    public void setArrowImageView(int resid) {
        mArrowImageView.setImageResource(resid);
    }

    public void setState(int state) {
        if (state == mState) return;

        if (state == STATE_REFRESHING) {    // 显示进度
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
            if (mProgressBar != null)
                mProgressBar.setVisibility(View.VISIBLE);
            smoothScrollTo(mMeasuredHeight);
        } else if (state == STATE_DONE) {
            mArrowImageView.setVisibility(View.INVISIBLE);
            if (mProgressBar != null)
                mProgressBar.setVisibility(View.INVISIBLE);
        } else {    // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }
        mHeaderTimeView.setText(formatDate2String());
        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_RELEASE_TO_REFRESH) {
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                mStatusTextView.setText(R.string.re_pull_down_refresh);
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_RELEASE_TO_REFRESH) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mStatusTextView.setText(R.string.re_losen_to_refresh);
                }
                break;
            case STATE_REFRESHING:
                mStatusTextView.setText(R.string.re_refreshing);
                break;
            case STATE_DONE:
                mStatusTextView.setText(R.string.re_refresh_done);
                break;
            default:
        }

        mState = state;
    }

    public int getState() {
        return mState;
    }

    @Override
    public void refreshComplete() {
        mHeaderTimeView.setText(formatDate2String());
        setState(STATE_DONE);
        postDelayed(new Runnable() {
            public void run() {
                reset();
            }
        }, 200);
    }

    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        return lp.height;
    }

    @Override
    public void onMove(float delta) {
        if (getVisibleHeight() > 0 || delta > 0) {
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                if (getVisibleHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) // not visible.
            isOnRefresh = false;

        if (getVisibleHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height <= mMeasuredHeight) {
            //return;
        }
        if (mState != STATE_REFRESHING) {
            smoothScrollTo(0);
        }
        if (mState == STATE_REFRESHING) {
            int destHeight = mMeasuredHeight;
            smoothScrollTo(destHeight);
        }
        return isOnRefresh;
    }

    public void reset() {
        smoothScrollTo(0);
        postDelayed(new Runnable() {
            public void run() {
                setState(STATE_NORMAL);
            }
        }, 500);
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public String formatDate2String() {
        SimpleDateFormat formatPattern = new SimpleDateFormat("HH:mm:ss");
        String resultTimeStr = formatPattern.format(new Date());
        return resultTimeStr;
    }

}