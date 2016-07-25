package wang.yuchao.android.library.util.sample;

import android.app.Application;

import wang.yuchao.android.library.util.CommonUtilLibrary;

/**
 * Created by wangyuchao on 16/7/25.
 */
public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CommonUtilLibrary.init(this);
    }
}
