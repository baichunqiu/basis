package com.basis.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.basis.R;
import com.bcq.net.callback.base.IRefreshView;
import com.kit.utils.Logger;
import com.spinkit.SpinKitView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author: BaiCQ
 * @createTime: 2016/12/02 11:24
 * @className: PullToRefreshListView
 * @Description: 上拉刷新下拉加载更多
 */
public class PullToRefreshListView extends ListView implements OnScrollListener, IRefreshView {
    private final static long TIME_OUT = 5 * 1000;//动画超时时间
    private final static int styleIndex = 2;//动画样式
    private final static int RELEASE_To_REFRESH = 0;//松开立即刷新
    public final static int PULL_To_REFRESH = 1;//下拉刷新
    private final static int REFRESHING = 2;//正在刷新
    private final static int DONE = 3;//默认
    private final static int LOADING = 4;//加载更多的状态
    private final static int RATIO = 3;
    private LayoutInflater inflater;
    private LinearLayout headView;
    private TextView tipsTextview;
    private TextView lastUpdatedTextView;
    private SpinKitView progressBar;
    private RotateAnimation animation;
    private RotateAnimation reverseAnimation;
    private boolean isRecored;
    private int headContentHeight;
    private int startY;
    public int state;
    private boolean isBack;
    private OnRefreshListener refreshListener;
    //是否可以刷新
    private boolean isRefreshable;

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setCacheColorHint(context.getResources().getColor(android.R.color.transparent));
        inflater = LayoutInflater.from(context);
        // 添加load more 功能
        footer = inflater.inflate(R.layout.refresh_footer, null);
        more = (TextView) footer.findViewById(R.id.more);
        loading = (SpinKitView) footer.findViewById(R.id.loading);
        footer.setVisibility(View.GONE);
        headView = (LinearLayout) inflater.inflate(R.layout.refresh_header, null);
        progressBar = (SpinKitView) headView.findViewById(R.id.head_progressBar);
        tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
        lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);

        measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        // 隐藏 headview 的高度
        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        headView.invalidate();
        addHeaderView(headView, null, false);
        addFooterView(footer);
        setOnScrollListener(this);
        animation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(300);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(300);
        reverseAnimation.setFillAfter(true);
        state = DONE;
        isRefreshable = false;
        progressBar.setStyleByIndex(styleIndex);
        loading.setStyleByIndex(styleIndex);
        autoComplete = new CompleteTask(this);
    }

    public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2, int arg3) {
        this.firstItemIndex = firstVisiableItem;
    }

    public boolean onTouchEvent(MotionEvent event) {
        firstItemIndex = getFirstVisiblePosition();
        if (isRefreshable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (firstItemIndex == 0 && !isRecored) {
                        isRecored = true;
                        startY = (int) event.getY();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (state != REFRESHING && state != LOADING) {
                        if (state == DONE) {
                        }
                        if (state == PULL_To_REFRESH) {
                            state = DONE;
                            changeHeaderViewByState();
                        }
                        if (state == RELEASE_To_REFRESH) {
                            state = REFRESHING;
                            changeHeaderViewByState();
                            onRefresh();
                        }
                    }

                    isRecored = false;
                    isBack = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int tempY = (int) event.getY();
                    if (!isRecored && firstItemIndex == 0) {
                        isRecored = true;
                        startY = tempY;
                    }
                    if (state != REFRESHING && isRecored && state != LOADING) {
                        // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
                        // 可以松手去刷新了
                        if (state == RELEASE_To_REFRESH) {
                            setSelection(0);
                            // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                            if (((tempY - startY) / RATIO < headContentHeight)
                                    && (tempY - startY) > 0) {
                                state = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            }
                            // 一下子推到顶了
                            else if (tempY - startY <= 0) {
                                state = DONE;
                                changeHeaderViewByState();
                            }
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                        if (state == PULL_To_REFRESH) {
                            setSelection(0);
                            // 下拉到可以进入RELEASE_TO_REFRESH的状态
                            if ((tempY - startY) / RATIO >= headContentHeight) {
                                state = RELEASE_To_REFRESH;
                                isBack = true;
                                changeHeaderViewByState();
                            }
                            // 上推到顶了
                            else if (tempY - startY <= 0) {
                                state = DONE;
                                changeHeaderViewByState();
                            }
                        }
                        // done状态下
                        if (state == DONE) {
                            if (tempY - startY > 0) {
                                state = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            }
                        }
                        // 更新headView的size
                        if (state == PULL_To_REFRESH) {
                            headView.setPadding(0, -1 * headContentHeight + (tempY - startY) / RATIO, 0, 0);
                        }
                        // 更新headView的paddingTop
                        if (state == RELEASE_To_REFRESH) {
                            headView.setPadding(0, (tempY - startY) / RATIO - headContentHeight, 0, 0);
                        }
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    //通知父层ViewGroup，你不能截获
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    // 当状态改变时候，调用该方法，以更新界面
    public void changeHeaderViewByState() {
        switch (state) {
            case RELEASE_To_REFRESH:
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                tipsTextview.setText(R.string.str_losen_to_refresh);
                break;
            case PULL_To_REFRESH:
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                // 是由RELEASE_To_REFRESH状态转变来的
                if (isBack) {
                    isBack = false;
                    tipsTextview.setText(R.string.pull_down_refresh);
                } else {
                    tipsTextview.setText(R.string.pull_down_refresh);
                }
                break;

            case REFRESHING:
                headView.setPadding(0, 0, 0, 0);
                tipsTextview.setText(R.string.str_refresh_in);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                break;
            case DONE:
                headView.setPadding(0, -1 * headContentHeight, 0, 0);
                tipsTextview.setText(R.string.pull_down_refresh);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        isRefreshable = true;
    }

    public void onRefreshComplete() {
        // 删除底部的footer 重新加载
        footer.setVisibility(View.GONE);
        state = DONE;
        lastUpdatedTextView.setText(getResources().getString(R.string.last_update) + ":" + formatDate2String(new Date()));
        changeHeaderViewByState();
        //remove task
        Handler h = getHandler();
        if (h != null) h.removeCallbacks(autoComplete);
    }

    private void showFooter() {
        footer.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        more.setVisibility(View.VISIBLE);
    }


    public static class CompleteTask implements Runnable {
        private WeakReference<IRefreshView> reference;

        protected CompleteTask(IRefreshView refreshView) {
            reference = new WeakReference<>(refreshView);
        }

        @Override
        public void run() {
            IRefreshView refreshView = reference.get();
            if (null != refreshView) {
                Logger.e("refresh time_out !");
                refreshView.onRefreshComplete();
            }
        }
    }

    private CompleteTask autoComplete;

    private void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
            postDelayed(autoComplete, TIME_OUT);
            if (removeFirst) {//已移除 添加
                addFooterView(footer);
                removeFirst = false;
            }
        }
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public void setAdapter(BaseAdapter adapter) {
        lastUpdatedTextView.setText(getResources().getString(R.string.last_update) + ":" + formatDate2String(new Date()));
        super.setAdapter(adapter);
    }

    private boolean isLoading;// 判断是否正在加载
    private boolean loadEnable = false;
    private boolean isLoadFull;// 加载全部
    private OnLoadListener onLoadListener;
    private View footer;

    private int firstItemIndex;
    private int scrollStates;

    private TextView more;
    private SpinKitView loading;

    @Override
    public void onScrollStateChanged(AbsListView view1, int scrollState) {
        this.scrollStates = scrollState;
        ifNeedLoad(view1, scrollStates);
    }

    // 加载更多监听
    @Override
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.loadEnable = true;
        this.isLoading = false;
        this.isLoadFull = false;
        this.onLoadListener = onLoadListener;
    }

    public boolean isLoadEnable() {
        return loadEnable;
    }

    // 这里的开启或者关闭加载更多，并不支持动态调整
    public void setLoadEnable(boolean loadEnable) {
        this.loadEnable = loadEnable;
        this.removeFooterView(footer);
    }

    public void onLoad() {
        if (onLoadListener != null) {
            loading.startAnimation(reverseAnimation);
            setSelection(getAdapter().getCount());
            onLoadListener.onLoad();
        }
    }

    //是否移除footer
    private boolean removeFirst = false;

    public void setLoadFull(boolean isLoadFull) {
        if (loadEnable) {
            this.isLoadFull = isLoadFull;
            if (isLoadFull) {
                if (!removeFirst) {//未移除  移除
                    removeFooterView(footer);
                    removeFirst = true;
                }
            } else {
                if (removeFirst) {//已移除 添加
                    if (null != footer) {
                        addFooterView(footer);
                        removeFirst = false;
                    }
                }
            }
        }
    }

    // 用于加载更多结束后的回调
    public void onLoadComplete() {
        loading.clearAnimation();
        loadEnable = true;
        isLoading = false;
        footer.setVisibility(View.GONE);
    }

    // 根据listview滑动的状态判断是否需要加载更多
    public void ifNeedLoad(AbsListView view, int scrollState) {

        if (!loadEnable) {
            // 控制是否需要 上拉加载更多的显示
            return;
        }
        try {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                    && !isLoading
                    && view.getLastVisiblePosition() == view.getPositionForView(footer)
                    && getFirstVisiblePosition() != 0) {
                if (isLoadFull) {
                    return;
                }
                showFooter();
                isLoading = true;
                onLoad();
            }
        } catch (Exception e) {
        }
    }

    public static String formatDate2String(Date date) {
        // 接收待返回的时间字符串
        String resultTimeStr = "";
        if (date != null) {
            try {
                SimpleDateFormat formatPattern = new SimpleDateFormat("HH:mm:ss");
                resultTimeStr = formatPattern.format(date);
            } catch (Exception e) {
            }
        }
        return resultTimeStr;
    }
}
