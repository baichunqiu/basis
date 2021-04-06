package com.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;

import com.spinkit.animation.SpriteAnimatorBuilder;
import com.spinkit.sprite.RectSprite;
import com.spinkit.sprite.Sprite;
import com.spinkit.sprite.SpriteContainer;


/**
 * Created by ybq.
 */
public class Wave extends SpriteContainer {

    @Override
    public Sprite[] onCreateChild() {
        int modifyDelay = 6;//修改 添加动画开始延迟时间
        WaveItem[] waveItems = new WaveItem[5];
        for (int i = 0; i < waveItems.length; i++) {
            waveItems[i] = new WaveItem();
            //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (Build.VERSION.SDK_INT >=24) {
                waveItems[i].setAnimationDelay(600 / modifyDelay + i * 100);
            } else {
                waveItems[i].setAnimationDelay(-1200 / modifyDelay + i * 100);
            }

        }
        return waveItems;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds = clipSquare(bounds);
        int rw = bounds.width() / getChildCount();
        int width = bounds.width() / 5 * 3 / 5;
        for (int i = 0; i < getChildCount(); i++) {
            Sprite sprite = getChildAt(i);
            int l = bounds.left + i * rw + rw / 5;
            int r = l + width;
            sprite.setDrawBounds(l, bounds.top, r, bounds.bottom);
        }
    }

    private class WaveItem extends RectSprite {

        WaveItem() {
            setScaleY(0.4f);
        }

        @Override
        public ValueAnimator onCreateAnimation() {
            float fractions[] = new float[]{0f, 0.2f, 0.4f, 1f};
            return new SpriteAnimatorBuilder(this).scaleY(fractions, 0.4f, 1f, 0.4f, 0.4f).
                    duration(1200).
                    easeInOut(fractions)
                    .build();
        }
    }
}
