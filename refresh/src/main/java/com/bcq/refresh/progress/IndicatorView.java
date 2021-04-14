package com.bcq.refresh.progress;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.bcq.refresh.R;
import com.bcq.refresh.progress.indicator.BallBeatIndicator;
import com.bcq.refresh.progress.indicator.BallClipRotateIndicator;
import com.bcq.refresh.progress.indicator.BallClipRotateMultipleIndicator;
import com.bcq.refresh.progress.indicator.BallClipRotatePulseIndicator;
import com.bcq.refresh.progress.indicator.BallGridBeatIndicator;
import com.bcq.refresh.progress.indicator.BallGridPulseIndicator;
import com.bcq.refresh.progress.indicator.BallPulseIndicator;
import com.bcq.refresh.progress.indicator.BallPulseRiseIndicator;
import com.bcq.refresh.progress.indicator.BallPulseSyncIndicator;
import com.bcq.refresh.progress.indicator.BallRotateIndicator;
import com.bcq.refresh.progress.indicator.BallScaleIndicator;
import com.bcq.refresh.progress.indicator.BallScaleMultipleIndicator;
import com.bcq.refresh.progress.indicator.BallScaleRippleIndicator;
import com.bcq.refresh.progress.indicator.BallScaleRippleMultipleIndicator;
import com.bcq.refresh.progress.indicator.BallSpinFadeLoaderIndicator;
import com.bcq.refresh.progress.indicator.BallTrianglePathIndicator;
import com.bcq.refresh.progress.indicator.BallZigZagDeflectIndicator;
import com.bcq.refresh.progress.indicator.BallZigZagIndicator;
import com.bcq.refresh.progress.indicator.BaseIndicatorController;
import com.bcq.refresh.progress.indicator.CubeTransitionIndicator;
import com.bcq.refresh.progress.indicator.LineScaleIndicator;
import com.bcq.refresh.progress.indicator.LineScalePartyIndicator;
import com.bcq.refresh.progress.indicator.LineScalePulseOutIndicator;
import com.bcq.refresh.progress.indicator.LineScalePulseOutRapidIndicator;
import com.bcq.refresh.progress.indicator.LineSpinFadeLoaderIndicator;
import com.bcq.refresh.progress.indicator.PacmanIndicator;
import com.bcq.refresh.progress.indicator.SemiCircleSpinIndicator;
import com.bcq.refresh.progress.indicator.SquareSpinIndicator;
import com.bcq.refresh.progress.indicator.TriangleSkewSpinIndicator;

/**
 * Created by Jack on 2015/10/15
 * <p>
 * .BallPulse,
 * .BallGridPulse,
 * .BallClipRotate,
 * .BallClipRotatePulse,
 * .SquareSpin,
 * .BallClipRotateMultiple,
 * .BallPulseRise,
 * .BallRotate,
 * .CubeTransition,
 * .BallZigZag,
 * .BallZigZagDeflect,
 * .BallTrianglePath,
 * .BallScale,
 * .LineScale,
 * .LineScaleParty,
 * .BallScaleMultiple,
 * .BallPulseSync,
 * .BallBeat,
 * .LineScalePulseOut,
 * .LineScalePulseOutRapid,
 * .BallScaleRipple,
 * .BallScaleRippleMultiple,
 * .BallSpinFadeLoader,
 * .LineSpinFadeLoader,
 * .TriangleSkewSpin,
 * .Pacman,
 * .BallGridBeat,
 * .SemiCircleSpin
 */
public class IndicatorView extends View implements Indicator{
    public static final int DEFAULT_SIZE = 30;
    private Style indicatorStyle;
    private int indicatorColor;
    private Paint mPaint;
    private BaseIndicatorController mIndicatorController;

    private boolean mHasAnimation;

    public IndicatorView(Context context) {
        this(context, null, R.attr.indicator_style, R.style.re_progress_default_style);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.indicator_style, R.style.re_progress_default_style);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.re_progress_default_style);
    }

    public void destroy() {
        mHasAnimation = true;
        if (mIndicatorController != null) {
            mIndicatorController.destroy();
            mIndicatorController = null;
        }
        mPaint = null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView, defStyleAttr, defStyleRes);
        indicatorStyle = Style.valueOf(a.getInt(R.styleable.IndicatorView_indicator_style, 0));
        indicatorColor = a.getColor(R.styleable.IndicatorView_indicator_color, Color.WHITE);
        a.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(indicatorColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        applyIndicator();
    }

    public void setStyle(Style indicatorStyle) {
        this.indicatorStyle = indicatorStyle;
        applyIndicator();
    }

    public void setColor(int color) {
        indicatorColor = color;
        mPaint.setColor(indicatorColor);
        this.invalidate();
    }

    private void applyIndicator() {
        switch (indicatorStyle) {
            case BallPulse:
                mIndicatorController = new BallPulseIndicator();
                break;
            case BallGridPulse:
                mIndicatorController = new BallGridPulseIndicator();
                break;
            case BallClipRotate:
                mIndicatorController = new BallClipRotateIndicator();
                break;
            case BallClipRotatePulse:
                mIndicatorController = new BallClipRotatePulseIndicator();
                break;
            case SquareSpin:
                mIndicatorController = new SquareSpinIndicator();
                break;
            case BallClipRotateMultiple:
                mIndicatorController = new BallClipRotateMultipleIndicator();
                break;
            case BallPulseRise:
                mIndicatorController = new BallPulseRiseIndicator();
                break;
            case BallRotate:
                mIndicatorController = new BallRotateIndicator();
                break;
            case CubeTransition:
                mIndicatorController = new CubeTransitionIndicator();
                break;
            case BallZigZag:
                mIndicatorController = new BallZigZagIndicator();
                break;
            case BallZigZagDeflect:
                mIndicatorController = new BallZigZagDeflectIndicator();
                break;
            case BallTrianglePath:
                mIndicatorController = new BallTrianglePathIndicator();
                break;
            case BallScale:
                mIndicatorController = new BallScaleIndicator();
                break;
            case LineScale:
                mIndicatorController = new LineScaleIndicator();
                break;
            case LineScaleParty:
                mIndicatorController = new LineScalePartyIndicator();
                break;
            case BallScaleMultiple:
                mIndicatorController = new BallScaleMultipleIndicator();
                break;
            case BallPulseSync:
                mIndicatorController = new BallPulseSyncIndicator();
                break;
            case BallBeat:
                mIndicatorController = new BallBeatIndicator();
                break;
            case LineScalePulseOut:
                mIndicatorController = new LineScalePulseOutIndicator();
                break;
            case LineScalePulseOutRapid:
                mIndicatorController = new LineScalePulseOutRapidIndicator();
                break;
            case BallScaleRipple:
                mIndicatorController = new BallScaleRippleIndicator();
                break;
            case BallScaleRippleMultiple:
                mIndicatorController = new BallScaleRippleMultipleIndicator();
                break;
            case BallSpinFadeLoader:
                mIndicatorController = new BallSpinFadeLoaderIndicator();
                break;
            case LineSpinFadeLoader:
                mIndicatorController = new LineSpinFadeLoaderIndicator();
                break;
            case TriangleSkewSpin:
                mIndicatorController = new TriangleSkewSpinIndicator();
                break;
            case Pacman:
                mIndicatorController = new PacmanIndicator();
                break;
            case BallGridBeat:
                mIndicatorController = new BallGridBeatIndicator();
                break;
            case SemiCircleSpin:
                mIndicatorController = new SemiCircleSpinIndicator();
                break;
        }
        mIndicatorController.setTarget(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(dp2px(DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(dp2px(DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!mHasAnimation) {
            mHasAnimation = true;
            applyAnimation();
        }
    }

    @Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (mIndicatorController == null)
                return;
            if (v == GONE || v == INVISIBLE) {
                mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.END);
            } else {
                mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.START);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mIndicatorController == null)
            return;
        mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.CANCEL);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mIndicatorController == null)
            return;
        mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.START);
    }

    void drawIndicator(Canvas canvas) {
        if (mIndicatorController == null)
            return;
        mIndicatorController.draw(canvas, mPaint);
    }

    void applyAnimation() {
        if (mIndicatorController == null)
            return;
        mIndicatorController.initAnimation();
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }


}
