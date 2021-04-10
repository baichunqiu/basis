package com.basis.widget;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;

import com.basis.base.BaseActivity;
import com.basis.widget.interfaces.IBarWrap;
import com.kit.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class ActionBarWapper implements IBarWrap<ActionBarWapper> {
    private ActionBar actionBar;
    private int title;
    private boolean backHide = false;//back 是否隐藏
    private boolean hide = false;//action bar 是否隐藏
    private List<OpMenu> options;
    private OnMenuSelectedListener onMenuSelectedListener;

    public ActionBarWapper(BaseActivity activity) {
        actionBar = activity.getSupportActionBar();
        if (null == actionBar) {
            throw new IllegalArgumentException("No Support ActionBarWrapper For ActionBar is null ! ");
        }
    }

    @Override
    public ActionBarWapper setHide(boolean hide) {
        this.hide = hide;
        if (hide) {
            actionBar.hide();
        }
        return this;
    }

    @Override
    public ActionBarWapper setBackHide(boolean hide) {
        this.backHide = hide;
        return this;
    }

    @Override
    public ActionBarWapper setTitle(@StringRes int title) {
        this.title = title;
        return this;
    }

    @Override
    public ActionBarWapper setOnMenuSelectedListener(OnMenuSelectedListener onMenuSelectedListener) {
        this.onMenuSelectedListener = onMenuSelectedListener;
        return this;
    }

    @Override
    public ActionBarWapper addOptionMenu(String title) {
        return addOptionMenu(title, -1);
    }

    @Override
    public ActionBarWapper addOptionMenu(String title, @DrawableRes int icon) {
        if (hide) return this;
        if (null == options) {
            options = new ArrayList<>(4);
        }
        OpMenu opMenu = new OpMenu(icon, title);
        opMenu.setIndex(options.size());
        options.add(opMenu);
        return this;
    }

    @Override
    public ActionBarWapper inflate() {
        if (null != actionBar) {
            if (title > 0) actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(!backHide);
        }
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        menu.clear();
        int len = null == options ? 0 : options.size();
        for (int i = 0; i < len; i++) {
            OpMenu opm = options.get(i);
            MenuItem item;
            if (TextUtils.isEmpty(opm.getTitle())) {
                opm.setTitle(String.valueOf(opm.getIndex()));
                item = menu.add(opm.getTitle()).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
            } else {
                item = menu.add(opm.getTitle()).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            }
            if (opm.getIcon() > 0) item.setIcon(opm.getIcon());
        }
        Logger.e("ActionBarWapper", "action len = " + len);
        return len > 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (null != onMenuSelectedListener) {
            CharSequence title = item.getTitle();
            if (null != title) {
                String text = title.toString();
                Logger.e("ActionBarWapper", "text = " + text);
                int index = -1;
                for (OpMenu menu : options) {
                    if (text.equals(menu.getTitle())) {
                        index = menu.getIndex();
                    }
                }
                if (index > -1) {
                    onMenuSelectedListener.onItemSelected(index);
                }
            } else {
                Logger.e("ActionBarWapper", "MenuItem title is null !");
            }

        }
        return true;
    }
}
