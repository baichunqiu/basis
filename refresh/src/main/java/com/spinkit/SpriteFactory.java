package com.spinkit;


import com.spinkit.sprite.Sprite;
import com.spinkit.style.ChasingDots;
import com.spinkit.style.Circle;
import com.spinkit.style.CubeGrid;
import com.spinkit.style.DoubleBounce;
import com.spinkit.style.FadingCircle;
import com.spinkit.style.FoldingCube;
import com.spinkit.style.MultiplePulse;
import com.spinkit.style.MultiplePulseRing;
import com.spinkit.style.Pulse;
import com.spinkit.style.PulseRing;
import com.spinkit.style.RotatingCircle;
import com.spinkit.style.RotatingPlane;
import com.spinkit.style.ThreeBounce;
import com.spinkit.style.WanderingCubes;
import com.spinkit.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}
