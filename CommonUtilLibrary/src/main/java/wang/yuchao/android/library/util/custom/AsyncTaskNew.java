package wang.yuchao.android.library.util.custom;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

/**
 * 不受线程池管理控制的AsyncTask(mUiHandler+thread)，可以更新UI，其他同AsyncTask
 * 如果不想更新UI，那么直接使用Thread更好
 * Created by wangyuchao on 15/11/21.
 */
public abstract class AsyncTaskNew<Params, Progress, Result> {

    private final static int START = 0;
    private final static int BACKGROUND = 1;
    private final static int PROGRESS = 2;
    private final static int END = 3;
    private final static int CANCEL = 4;
    private HandlerThread mHandlerThread;
    private InnerHandler mInnerHandler;
    private UiHandler mUiHandler;
    private Activity mActivity;
    private boolean isCancel;

    public AsyncTaskNew(Activity activity) {
        mActivity = activity;
        mHandlerThread = new HandlerThread("AsyncTaskNew", Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mInnerHandler = new InnerHandler(mHandlerThread.getLooper());//异步handler
        mUiHandler = new UiHandler(Looper.getMainLooper());//UI的handler
    }

    public void execute(Params... params) {
        Message message = new Message();
        message.what = START;
        message.obj = params;
        mUiHandler.sendMessage(message);
    }

    /**
     * 需要重写的，线程开始时需要执行的UI操作
     */
    protected void onPreExecute() {
    }

    /**
     * 后台运行的操作
     */
    protected abstract Result doInBackground(Params... params);

    /**
     * 需要重写的，线程中间更新执行的UI操作
     */
    protected void onProgressUpdate(Progress... values) {
    }

    /**
     * 用户需要更新状态时，需要调用的
     */
    protected final void publishProgress(Progress... values) {
        Message message = new Message();
        message.what = PROGRESS;
        message.obj = values;
        mUiHandler.sendMessage(message);
    }

    /**
     * 需要重写的，线程结束后执行的UI操作，需要注意的是需要看UI所在的Activity是否还在
     */
    protected void onPostExecute(Result result) {
    }

    public final boolean isCancelled() {
        return mHandlerThread.isInterrupted();
    }

    /**
     * 不一定能真正取消掉
     */
    public final void cancel() {
        if (!mHandlerThread.isInterrupted()) {
            mHandlerThread.quit();
            try {
                mHandlerThread.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Message message = new Message();
        message.what = CANCEL;
        mUiHandler.sendMessage(message);
    }

    protected void onCancelled() {
    }

    /**
     * 是否可以显示UI:如果已经点击了cancel或者Activity不存在
     */
    protected boolean isValidShowUI() {
        if (isCancel || mActivity == null || mActivity.isFinishing() || (Build.VERSION.SDK_INT >= 17 && mActivity.isDestroyed())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 与new thread绑定的handler
     */
    private class InnerHandler extends Handler {
        public InnerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BACKGROUND:
                    Message message = new Message();
                    message.what = END;
                    message.obj = doInBackground((Params[]) msg.obj);
                    mUiHandler.sendMessage(message);
                    break;
            }
        }
    }

    /**
     * UiHandler
     */
    private class UiHandler extends Handler {
        public UiHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START:
                    onPreExecute();
                    Message message = new Message();
                    message.obj = msg.obj;
                    message.what = BACKGROUND;
                    mInnerHandler.sendMessage(message);
                    break;
                case PROGRESS:
                    if (isValidShowUI()) {
                        onProgressUpdate((Progress[]) msg.obj);
                    }
                    break;
                case END:
                    if (isValidShowUI()) {
                        onPostExecute((Result) msg.obj);
                        mHandlerThread.quit();
                    }
                    break;
                case CANCEL:
                    isCancel = true;
                    onCancelled();
                    break;
            }
        }
    }

}
