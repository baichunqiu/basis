package com.basis.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basis.R;
import com.basis.base.AppHelper;
import com.basis.base.BaseActivity;

/**
 * @author: BaiCQ
 * @ClassName: TitleBar
 * @date: 2018/10/10
 * @Description: TitleBar 自定义控件（包含自定义属性）
 * <p><declare-styleable name="TitleBar"> </p>
 * <p>     <attr name="title" format="string" /> 标题文字</p>
 * <p>     <attr name="title_color" format="color" />标题文字颜色</p>
 * <p>     <attr name="title_size" format="dimension" />标题文字字号（单位sp）</p>
 * <p>
 * <p>     <attr name="left_icon" format="reference|color" />标题左侧icon </p>
 * <p>     <attr name="right_icon" format="reference|color" />标题右侧icon </p>
 * <p>
 * <p>     <attr name="right_text" format="string" /> 标题右侧文字 </p>
 * <p>     <attr name="right_size" format="dimension" />标题右侧文字字号 </p>
 * <p>     <attr name="right_background" format="reference|color"/> 标题右侧文字背景 </p>
 * <p>
 * <p>     <attr name="titlebar_height_percent" format="float" /> 标题高度占屏宽的百分比，如：12 指标题栏高度是屏幕宽度的12%</p>
 * <p>     <attr name="titlebar_margin_percent" format="float" /> 标题栏左右icon聚边缘的margin的占屏宽百分比</p>
 * </declare-styleable>
 */
public class TitleBar extends RelativeLayout {
    private static DefaultBuild defaultBuild;
    private String title;
    private TextView titleView, rightTextView;
    private LinearLayout ll_right;
    private int titleColor;
    private float titleSize, rightSize;
    private ImageView leftIconView/*,rightIconView*/;
    private Drawable rightIcon, leftIcon;
    private boolean leftHind = false;
    private Drawable rightBg;
    private String rightText;
    private int pressDrawable = -1;
    private Context context;
    private int SW = 0;
    private float heightP = 0f;
    private float marginP = 0f;
    private int height = 0;
    private int rightWidth = 0;
    private int defaultMargin = 0;
    private boolean textMode = false;//右侧显示文本
    private OnClickListener onLeftClickListener;
    private OnClickListener onRightClickListener;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttribute(attrs);
        SW = getResources().getDisplayMetrics().widthPixels;
        height = (int) (SW * heightP);
        rightWidth = height * 16 / 10;
        defaultMargin = (int) (SW * marginP);
        init();
    }

    /**
     * 获取自动义属性
     *
     * @param attrs
     */
    private void initAttribute(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        title = ta.getString(R.styleable.TitleBar_title);
        titleColor = ta.getColor(R.styleable.TitleBar_title_color, 0);
        titleSize = ta.getDimension(R.styleable.TitleBar_title_size, 0);
        rightSize = ta.getDimension(R.styleable.TitleBar_right_size, 0);
        heightP = ta.getFloat(R.styleable.TitleBar_titlebar_height_percent, 0) / 100;
        marginP = ta.getFloat(R.styleable.TitleBar_titlebar_margin_percent, 0) / 100;
        leftHind = ta.getBoolean(R.styleable.TitleBar_left_hind,false);
        leftIcon = ta.getDrawable(R.styleable.TitleBar_left_icon);
        rightIcon = ta.getDrawable(R.styleable.TitleBar_right_icon);
        rightText = ta.getString(R.styleable.TitleBar_right_text);
        rightBg = ta.getDrawable(R.styleable.TitleBar_right_background);
        ta.recycle();//回收资源，防止内存泄漏
        if (null != defaultBuild) {
            if (0 == heightP) heightP = defaultBuild._defHeightP / 100;
            if (0 == marginP) marginP = defaultBuild._defMarginP / 100;
            if (0 == titleColor) titleColor = defaultBuild.defTitleColor;
            if (0 == titleSize) titleSize = defaultBuild.defTitleSize;
            if (0 == rightSize) rightSize = defaultBuild.defRightSize;
            if (null == leftIcon) leftIcon = defaultBuild.defLeftDrawable;
            if (null == getBackground() && null != defaultBuild.defBgDrawable) {
                setBackground(defaultBuild.defBgDrawable);
            }
            pressDrawable = defaultBuild.defPressDrawable;
        }
    }

    private void init() {
        LinearLayout ll_left = new LinearLayout(context);
        if (pressDrawable > 0) ll_left.setBackgroundResource(pressDrawable);
        ll_left.setOrientation(LinearLayout.HORIZONTAL);
        ll_left.setGravity(Gravity.CENTER);

        LayoutParams left_Param = new LayoutParams(height, height);
        left_Param.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        left_Param.setMargins(defaultMargin, 0, 0, 0);
        //add left
        addView(ll_left, left_Param);

        ll_right = new LinearLayout(context);
        if (!textMode && pressDrawable > 0) ll_right.setBackgroundResource(pressDrawable);
        ll_right.setOrientation(LinearLayout.HORIZONTAL);
        ll_right.setGravity(Gravity.CENTER);

        LayoutParams right_Param = new LayoutParams(textMode ? rightWidth : height, height);
        right_Param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        right_Param.setMargins(0, 0, defaultMargin, 0);
        //add right
        addView(ll_right, right_Param);

        if (!leftHind)buildLeft(ll_left);
        buildRight();
        buildTitle();

        ll_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onLeftClickListener) {
                    onLeftClickListener.onClick(v);
                } else {
                    //todo 默认获取top activity finish
                    if (context instanceof BaseActivity) {
                        ((BaseActivity) context).onBackCode();
                    } else {
                        BaseActivity top = AppHelper.getInstance().getTopActivity();
                        if (null != top) {
                            top.onBackCode();
                        }
                    }
                }
            }
        });

        ll_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onRightClickListener) onRightClickListener.onClick(v);
            }
        });
        ll_right.setClickable(false);
        ll_left.setClickable(!leftHind);
    }

    private void buildLeft(ViewGroup leftLayout) {
        leftIconView = new ImageView(context);
        leftIconView.setBackground(leftIcon);
        leftLayout.addView(leftIconView);
    }

    private void buildRight() {
        rightTextView = new TextView(context);
        rightTextView.setGravity(Gravity.CENTER);
        if (!textMode) {
            if (null != rightIcon) rightTextView.setBackground(rightIcon);
            ll_right.addView(rightTextView);
        } else {
            if (null != rightBg) rightTextView.setBackground(rightBg);
            rightTextView.setText(rightText);
            rightTextView.setTextColor(titleColor);
            rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightSize);
            ll_right.addView(rightTextView);
        }
        refreshMode();
    }

    /**
     * 根据右侧显示mode：text/icon 刷新右侧控件的LayoutParams
     */
    private void refreshMode() {
        if (null != ll_right) {
            LayoutParams lrp = (LayoutParams) ll_right.getLayoutParams();
            lrp.width = textMode ? rightWidth : height;
            ll_right.setLayoutParams(lrp);
            if (!textMode && pressDrawable > 0) {
                ll_right.setBackgroundResource(pressDrawable);
            } else {
                ll_right.setBackground(null);
            }
        }
        if (null != rightTextView) {
            LinearLayout.LayoutParams rtvp = (LinearLayout.LayoutParams) rightTextView.getLayoutParams();
            rtvp.width = textMode ? rightWidth * 11 / 16 : ViewGroup.LayoutParams.WRAP_CONTENT;
            rtvp.height = textMode ? height * 11 / 20 : ViewGroup.LayoutParams.WRAP_CONTENT;
            rightTextView.setLayoutParams(rtvp);
            rightTextView.setText(textMode ? rightText : "");
        }
    }

    private void buildTitle() {
        titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextColor(titleColor);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        titleView.setGravity(Gravity.CENTER);
        LayoutParams titleParams = new LayoutParams(LayoutParams.MATCH_PARENT, height);
        titleParams.setMargins(height + defaultMargin, 0, rightWidth + defaultMargin, 0);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(titleView, titleParams);
    }

    public TitleBar setTitle(String title) {
        if (null != titleView) {
            if (null == title) title = "";
            titleView.setText(title);
        }
        return this;
    }

    public TitleBar setTitle(String title, int titleColorId) {
        if (null != titleView) {
            if (null == title) title = "";
            if (titleColorId > 0) titleView.setTextColor(getResources().getColor(titleColorId));
            titleView.setText(title);
        }
        return this;
    }

    public TitleBar setTitle(int titleId) {
        if (null != titleView && titleId > 0) titleView.setText(titleId);
        return this;
    }

    public TitleBar setTitle(int titleId, int titleColorId) {
        if (null != titleView) {
            if (titleId > 0) titleView.setText(titleId);
            if (titleColorId > 0) titleView.setTextColor(getResources().getColor(titleColorId));
        }
        return this;
    }

    public TitleBar setLeftIcon(int leftIconId) {
        if (null != leftIconView && leftIconId > 0) leftIconView.setImageResource(leftIconId);
        return this;
    }

    public TitleBar setRightIcon(int rightIconId) {
        if (null != rightTextView) {
            textMode = false;
            refreshMode();
            if (rightIconId > 0) rightTextView.setBackgroundResource(rightIconId);
        }
        return this;
    }

    public TitleBar setRightText(int rightTextId, int rightBgId) {
        if (null != rightTextView) {
            textMode = true;
            refreshMode();
            if (rightTextId > 0) rightTextView.setText(rightTextId);
            if (rightBgId > 0) rightTextView.setBackgroundResource(rightBgId);
        }
        return this;
    }

    public TitleBar setOnLeftListener(OnClickListener onLeftListener) {
        this.onLeftClickListener = onLeftListener;
        return this;
    }

    public TitleBar setOnRightListener(OnClickListener onRightListener) {
        this.onRightClickListener = onRightListener;
        if (null != ll_right) ll_right.setClickable(true);
        return this;
    }

    /**
     * 设置默认Builder
     *
     * @param defaultBuild
     */
    public static void setDefaultBuild(DefaultBuild defaultBuild) {
        TitleBar.defaultBuild = defaultBuild;
    }

    public static class DefaultBuild {
        public float _defHeightP = 12.5f;
        protected float _defMarginP = 0f;
        protected int defTitleSize = sp2px(16);
        protected int defRightSize = sp2px(14);
        protected int defTitleColor = Color.parseColor("#FFFFFF");
        protected Drawable defBgDrawable;
        protected Drawable defLeftDrawable;
        protected int defPressDrawable = -1;

        /**
         * 设置默认参数
         *
         * @param heightP titleBar高度的占屏幕宽的百分比
         * @param marginP 左右margin的百分比
         */
        public DefaultBuild(float heightP, float marginP) {
            this._defHeightP = heightP;
            this._defMarginP = marginP;
        }

        /**
         * 设置title字色
         *
         * @param defTitleColor
         * @return
         */
        public DefaultBuild buildTitleColor(int defTitleColor) {
            this.defTitleColor = defTitleColor;
            return this;
        }

        /**
         * 设置title字号
         *
         * @param defTitleSize 单位sp
         * @return
         */
        public DefaultBuild buildTitleSize(int defTitleSize) {
            this.defTitleSize = sp2px(defTitleSize);
            this.defRightSize = sp2px(defTitleSize - 3);
            return this;
        }

        /**
         * 设置titlebar的背景Drawable
         *
         * @param bgDrawable 背景Drawable
         * @return
         */
        public DefaultBuild buildBackGround(Drawable bgDrawable) {
            this.defBgDrawable = bgDrawable;
            return this;
        }

        /**
         * 设置背景色
         *
         * @param bgColor 背景色
         * @return
         */
        public DefaultBuild buildBackGroundColor(int bgColor) {
            return buildBackGround(new ColorDrawable(bgColor));
        }

        /**
         * 设置默认返回图标
         *
         * @param leftDrawable
         * @return
         */
        public DefaultBuild buildLeftDrawable(Drawable leftDrawable) {
            this.defLeftDrawable = leftDrawable;
            return this;
        }

        /**
         * 设置press效果的选择器
         *
         * @param defPressDrawable
         * @return
         */
        public DefaultBuild buildPressDrawable(int defPressDrawable) {
            this.defPressDrawable = defPressDrawable;
            return this;
        }

        private int sp2px(int sp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                    Resources.getSystem().getDisplayMetrics());
        }
    }
}
