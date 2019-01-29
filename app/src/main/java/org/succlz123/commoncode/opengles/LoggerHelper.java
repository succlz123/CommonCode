package org.succlz123.commoncode.opengles;

import android.util.Log;

/**
 * Created by succlz123 on 2018/2/21.
 */

public class LoggerHelper {
    public static final boolean ON = true;

    public static void d(String tag, String msg) {
        if (ON)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (ON)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (ON)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (ON)
            Log.e(tag, msg);
    }
}
