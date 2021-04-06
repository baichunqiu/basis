package com.spinkit;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.spinkit.sprite.Sprite;
import com.xrecycle.R;


/**
 * Created by BaiCQ.
 */
public class SpinKitView extends ProgressBar implements Indicator{

    private Style mStyle;
    private int mColor;
    private Sprite mSprite;

    public SpinKitView(Context context) {
        this(context, null);
    }

    public SpinKitView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SpinKitViewStyle);
    }

    public SpinKitView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.SpinKitView);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SpinKitView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SpinKitView, defStyleAttr,
                defStyleRes);
        mStyle = Style.indexOf(a.getInt(R.styleable.SpinKitView_SpinKit_Style, 0));
        mColor = a.getColor(R.styleable.SpinKitView_SpinKit_Color, Color.BLACK);
        a.recycle();
        init();
        setIndeterminate(true);
    }

    private void init() {
        Sprite sprite = SpriteFactory.create(mStyle);
        Log.e("Spinkit","mStyle = "+mStyle);
        setIndeterminateDrawable(sprite);
    }

    @Override
    public void setIndeterminateDrawable(Drawable d) {
        if (!(d instanceof Sprite)) {
            throw new IllegalArgumentException("this drawable must be instanceof Sprite");
        }
        setIndeterminateDrawable((Sprite) d);
    }

    public void setIndeterminateDrawable(Sprite d) {
        super.setIndeterminateDrawable(d);
        mSprite = d;
        if (mSprite.getColor() == 0) {
            mSprite.setColor(mColor);
        }
        onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
        if (getVisibility() == VISIBLE) {
            mSprite.start();
        }
    }

    @Override
    public Sprite getIndeterminateDrawable() {
        return mSprite;
    }

    @Override
    public void setStyleIndex(int styleIndex) {
        mStyle = Style.indexOf(styleIndex);
        init();
    }

    @Override
    public void setStyle(Style style) {
        mStyle = style;
        init();
    }

    @Override
    public void setColor(int color) {
        this.mColor = color;
        if (mSprite != null) {
            mSprite.setColor(color);
        }
        invalidate();
    }

    @Override
    public void unscheduleDrawable(Drawable who) {
        super.unscheduleDrawable(who);
        if (who instanceof Sprite) {
            ((Sprite) who).stop();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            if (mSprite != null && getVisibility() == VISIBLE) {
                mSprite.start();
            }
        }
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        if (screenState == View.SCREEN_STATE_OFF) {
            if (mSprite != null) {
                mSprite.stop();
            }
        }
    }

}
