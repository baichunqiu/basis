package com.basis.widget.indicator;

/**
 * Created by chenupt@gmail.com on 2/28/15.
 * Description : Listener for tab
 */
public interface TabClickListener {

    /**
     * Handle click event when tab is clicked.
     * @param position ViewPager item position.
     * @return Call setCurrentItem if return true.
     */
    boolean onTabClick(int position);

}
