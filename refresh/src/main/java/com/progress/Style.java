package com.progress;

/**
 * 指示器样式定义
 */
public enum Style {
    BallPulse(0),
    BallGridPulse(1),
    BallClipRotate(2),
    BallClipRotatePulse(3),
    SquareSpin(4),
    BallClipRotateMultiple(5),
    BallPulseRise(6),
    BallRotate(7),
    CubeTransition(8),
    BallZigZag(9),
    BallZigZagDeflect(10),
    BallTrianglePath(11),
    BallScale(12),
    LineScale(13),
    LineScaleParty(14),
    BallScaleMultiple(15),
    BallPulseSync(16),
    BallBeat(17),
    LineScalePulseOut(18),
    LineScalePulseOutRapid(19),
    BallScaleRipple(20),
    BallScaleRippleMultiple(21),
    BallSpinFadeLoader(22),
    LineSpinFadeLoader(23),
    TriangleSkewSpin(24),
    Pacman(25),
    BallGridBeat(26),
    SemiCircleSpin(27),
    Sys(-1);

    private int value;

    Style(int value) {
        this.value = value;
    }

    public static Style valueOf(int value) {
        if (value < 0) {
            return Sys;
        } else {
            return Style.values()[value % 28];
        }
    }
}