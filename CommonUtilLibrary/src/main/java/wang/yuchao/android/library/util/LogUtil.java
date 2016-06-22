package wang.yuchao.android.library.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     1.日志相关类:默认是测试环境
 *     2.支持存储Log日志到本地
 *     3.支持发送日志到服务器
 *     4.支持程序员自己的Log
 *     5.LogUtil.v(message),默认TAG是类名,推荐使用
 *     6.LogUtil.v(tag,message),同Log.v(tag,message)
 * </pre>
 *
 * @author yuchao.wang
 * @since 2014-4-23
 */
public class LogUtil {

    private static boolean isShowLog = true; //默认显示

    /**
     * 写日志对象
     */
    private LogWriter logWriter;

    public static void setIsShowLog(boolean isShowLog) {
        LogUtil.isShowLog = isShowLog;
    }

    public static void v(String message) {
        String[] output = getTagAndDetailMessage(message);
        v(output[0], output[1]);
    }

    public static void v(String tag, String message) {
        if (isShowLog) {
            Log.v(tag, message);
        }
    }

    public static void w(String message) {
        String[] output = getTagAndDetailMessage(message);
        w(output[0], output[1]);
    }

    public static void w(String tag, String message) {
        if (isShowLog) {
            Log.w(tag, message);
        }
    }

    public static void i(String message) {
        String[] output = getTagAndDetailMessage(message);
        i(output[0], output[1]);
    }

    public static void i(String tag, String message) {
        if (isShowLog) {
            Log.i(tag, message);
        }
    }

    public static void d(String message) {
        String[] output = getTagAndDetailMessage(message);
        d(output[0], output[1]);
    }

    public static void d(String tag, String message) {
        if (isShowLog) {
            Log.d(tag, message);
        }
    }

    public static void e(String message) {
        String[] output = getTagAndDetailMessage(message);
        e(output[0], output[1]);
    }

    public static void e(String tag, String message) {
        if (isShowLog) {
            Log.e(tag, message);
        }
    }

    /**
     * 得到默认tag【类名】以及信息详情
     *
     * @param message 要显示的信息
     * @return 默认tag【类名】以及信息详情,默认信息详情【类名+方法名+行号+message】
     */
    private static String[] getTagAndDetailMessage(String message) {
        String output[] = new String[2];
        for (StackTraceElement ste : (new Throwable()).getStackTrace()) {
            //栈顶肯定是LogUtil这个类自己
            if (LogUtil.class.getName().equals(ste.getClassName())) {
                continue;
            }
            //栈顶的下一个就是需要调用这个类的地方
            else {
                int b = ste.getClassName().lastIndexOf(".") + 1;
                output[0] = ste.getClassName().substring(b);//类名
                output[1] = "->" + ste.getMethodName() + "():" + ste.getLineNumber() + "->" + message;//方法以及行号以及message
                break;
            }
        }
        return output;
    }

    /**
     * 整个应用只需要调用一次即可:开始本地记录
     *
     * @param filePath 要写入的目的文件路径
     */
    public void startWriteLogToSdcard(String filePath) {
        if (logWriter == null) {
            try {
                /** LogUtil这个类的pid,必须在类外面得到 */
                logWriter = new LogWriter(filePath, android.os.Process.myPid());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logWriter.start();
    }

    /**
     * 整个应用只需要调用一次即可:结束本地日志记录
     */
    public void endWriteLogToSdcard() {
        if (logWriter != null) {
            logWriter.end();
        }
    }

    /**
     * 写入本地日志线程
     */
    private class LogWriter extends Thread {
        /**
         * 文件路径
         */
        private String mFilePath;
        /**
         * 调用这个类的线程
         */
        private int mPid;
        /**
         * 线程运行标志
         */
        private boolean isRunning = true;

        /**
         * @param filePath 文件路径
         * @param pid
         */
        public LogWriter(String filePath, int pid) {
            this.mPid = pid;
            this.mFilePath = filePath;
        }

        @Override
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);//日期格式化对象
            Process process = null;//进程
            BufferedReader reader = null;
            FileWriter writer = null;
            try {
                //执行命令行
                String cmd = "logcat *:e *:w | grep";
                process = Runtime.getRuntime().exec(cmd);
                //得到输入流
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()), 1024);
                //创建文件
                File file = new File(mFilePath);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                writer = new FileWriter(file, true);
                //循环写入文件
                String line = null;
                while (isRunning) {
                    line = reader.readLine();
                    if (line != null && line.length() > 0) {
                        writer.append("PID:" + this.mPid + "\t" + sdf.format(new Date(System.currentTimeMillis())) + "\t" + line + "\n");
                        writer.flush();
                    } else {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (process != null) {
                    process.destroy();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (writer != null) {
                    try {
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                process = null;
                reader = null;
                writer = null;
            }
        }

        public void end() {
            isRunning = false;
        }
    }
}
