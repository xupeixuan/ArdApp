package com.xpx.base.wrapper;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.squareup.leakcanary.LeakCanary;
import com.xpx.base.util.debug.ViewServer;

/**
 * Created by xupeixuan on 2016/10/28.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        installLeakCanary();
        installViewServer();
    }

    private void installLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    private void installViewServer() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ViewServer.get(activity).addWindow(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                ViewServer.get(activity).setFocusedWindow(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ViewServer.get(activity).removeWindow(activity);
            }
        });
    }
}
