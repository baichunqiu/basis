package com.basis.widget;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.basis.R;
import com.basis.base.BaseActivity;
import com.basis.widget.interfaces.IWrapBar;
import com.kit.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class ActionWrapBar implements IWrapBar<ActionWrapBar> {
    private ActionBar actionBar;
    private int title;
    private boolean backHide = false;//back 是否隐藏
    private boolean noneBar = false;//action bar 是否隐藏
    private List<OpMenu> options;
    private OnMenuSelectedListener onMenuSelectedListener;

    private ActionBar inflateDefaultActionBar(BaseActivity activity) {
        ActionBar result = null;
        View defaultBarView = LayoutInflater.from(activity).inflate(R.layout.basis_action_bar_default, null, false);
        View content = (ViewGroup) activity.getLayout();
        if (content instanceof ViewGroup) {
            ((ViewGroup) content).addView(defaultBarView, 0);
            activity.setSupportActionBar(defaultBarView.findViewById(R.id.basis_toolbar));
            result = activity.getSupportActionBar();
        }
        return result;
    }

    public ActionWrapBar(AppCompatActivity activity) {
        actionBar = activity.getSupportActionBar();
        if (null == actionBar) {
            actionBar = inflateDefaultActionBar((BaseActivity) activity);
        }
        if (null == actionBar) {
            throw new IllegalArgumentException("No Support ActionBarWrapper For ActionBar is null ! ");
        }
    }

    @Override
    public ActionWrapBar setHide(boolean noneBar) {
        this.noneBar = noneBar;
        return this;
    }

    @Override
    public ActionWrapBar setBackHide(boolean hide) {
        this.backHide = hide;
        return this;
    }

    @Override
    public ActionWrapBar setTitle(@StringRes int title) {
        this.title = title;
        return this;
    }

    @Override
    public ActionWrapBar setOnMenuSelectedListener(OnMenuSelectedListener onMenuSelectedListener) {
        this.onMenuSelectedListener = onMenuSelectedListener;
        return this;
    }

    @Override
    public ActionWrapBar addOptionMenu(String title) {
        return addOptionMenu(title, -1);
    }

    @Override
    public ActionWrapBar addOptionMenu(String title, @DrawableRes int icon) {
        if (null == options) {
            options = new ArrayList<>(4);
        }
        OpMenu opMenu = new OpMenu(icon, title);
        opMenu.setIndex(options.size());
        options.add(opMenu);
        return this;
    }

    @Override
    public ActionWrapBar work() {
        if (null != actionBar) {
            if (title > 0) actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(!backHide);
            if (noneBar) {
                actionBar.hide();
            } else {
                actionBar.show();
            }
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
