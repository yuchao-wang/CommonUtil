package wang.yuchao.android.library.util.custom;

import android.view.View;

import wang.yuchao.android.library.util.ClickUtil;

/**
 * Created by wangyuchao on 16/7/6.
 */
public abstract class OnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        if (!ClickUtil.isQuickDoubleClick()) {
            click(view);
        }
    }

    public abstract void click(View view);
}
