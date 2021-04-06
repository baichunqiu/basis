package com.spinkit;

/**
 * 指示器接口
 */
public interface Indicator {
	/**
	 * 设置样式
	 * @param style
	 */
	void setStyle(Style style);

	/**
	 * 设置样式索引
	 * @param index
	 */
	void setStyleIndex(int index);

	/**
	 * 设置color
	 * @param color
	 */
	void setColor(int color);
}