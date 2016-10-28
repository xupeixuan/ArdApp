package com.xpx.base.wrapper;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by xupeixuan on 2016/10/28.
 */

public class ToastUtil {
    private static Context sContext = BaseApplication.getInstance();
    private static Toast sToast = Toast.makeText(sContext, "", Toast.LENGTH_SHORT);

    public static void show(String text) {
        sToast.setText(text);
        sToast.show();
    }

    public static void show(@StringRes int resId) {
        sToast.setText(resId);
        sToast.show();
    }

    public static void show(@StringRes int resId, int duration) {
        sToast.setText(resId);
        sToast.setDuration(duration);
        sToast.show();
    }
}
