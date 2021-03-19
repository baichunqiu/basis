package com.basis.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.basis.R;
import com.kit.utils.Logger;
import com.kit.UIKit;

/**
 * @author: BaiCQ
 * @ClassName: SearchLayout
 * @Description: 搜索组合布局
 */
public class SearchLayout extends LinearLayout {
    private Context context;
    private View rootView;
    //搜索控件
    private ImageView iv_delete;
    private SearchEditText et_search;
    private View fl_search;
    private SearchEditText.OnSearchListener osl;
    //是否监听文字变化，默认不监听
    private boolean textWatcher = false;

    public SearchLayout(Context context) {
        this(context, null);
    }

    public SearchLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflateWidget();
        initSearchListener();
    }

    private void inflateWidget() {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_search, this);
        et_search = UIKit.getView(rootView, R.id.et_search);
        iv_delete = UIKit.getView(rootView, R.id.iv_search_delete);
        iv_delete.setVisibility(GONE);
        fl_search = UIKit.getView(rootView, R.id.fl_search);
    }

    /**
     * 初始化搜索控件的点击事件
     */
    private void initSearchListener() {
        //删除搜索内容
        iv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("SearchLayout", "iv_delete");
                et_search.setText("");
                if (null != osl) osl.onSearch("");
            }
        });
        //软键盘搜索按键
        et_search.setOnSearchListener(new SearchEditText.OnSearchListener() {
            @Override
            public void onSearch(String search) {
                if (null != osl) osl.onSearch(search);
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                UIKit.setVisiable(iv_delete, !TextUtils.isEmpty(s));
                if (textWatcher && null != osl) {
                    osl.onSearch(s.toString());
                }
            }
        });
        //搜索按钮点击事件
        fl_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                hintInput();
                if (null != osl) {
                    osl.onSearch(et_search.getText().toString().trim());
                }
            }
        });
    }

    /**
     * 隐藏软键盘
     */
    private void hintInput() {
//        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        // 隐藏键盘
//        inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0); //强制隐藏键盘
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏键盘
        if (inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 设置textWarcher enable
     *
     * @param textWatcher
     */
    public void enableTextWatcher(boolean textWatcher) {
        this.textWatcher = textWatcher;
    }

    /**
     * 接口监听
     *
     * @param osl 实现的搜索接口子类的实例对象
     */
    public void setOnSearchListener(SearchEditText.OnSearchListener osl) {
        this.osl = osl;
    }

    public SearchLayout setHint(int stringId) {
        if (null != et_search)
            et_search.setHint(stringId);
        return this;
    }

    public SearchLayout clear() {
        if (null != et_search) {
            et_search.setText("");
            hintInput();
        }
        return this;
    }

    public String getText() {
        if (null == et_search) return "";
        return et_search.getText().toString().trim();
    }
}
