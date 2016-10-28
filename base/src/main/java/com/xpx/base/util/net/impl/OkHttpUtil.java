package com.xpx.base.util.net.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {
    private static final OkHttpClient.Builder mHttpBuilder = new OkHttpClient.Builder();

    private static OkHttpClient mOkHttpClient = mHttpBuilder.build();

    public static void setConnectTimeout(long timeout, TimeUnit unit) {
        mHttpBuilder.connectTimeout(timeout, unit);
    }

    public static void setReadTimeout(long timeout, TimeUnit unit) {
        mHttpBuilder.readTimeout(timeout, unit);
    }

    private static void buildHttpClient() {
        mOkHttpClient = mHttpBuilder.build();
    }

    public static Response execute(Request request) throws IOException {
        return mOkHttpClient.newCall(request).execute();
    }

    public static void enqueue(Request request, Callback responseCallback) {
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    public static void registerNetConnectTimeout(Context context) {
        if (context == null)
            return;

        // 方便起见，就不提供初始化方法，此处直接预先设置一下。
        ChangedNetTimeOutListener.setConnectTimeoutByNetStatus(context);

        ChangedNetTimeOutListener changedNetTimeOutListener = new ChangedNetTimeOutListener();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(changedNetTimeOutListener, intentFilter);
    }

    private static class ChangedNetTimeOutListener extends BroadcastReceiver {
        private static final int DEFAULT_TIMEOUT = 30;
        private static final int TIMEOUT_WIFI_4G = 20;
        private static final int TIMEOUT_3G = 25;
        private static final int TIMEOUT_2G = 30;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                setConnectTimeoutByNetStatus(context);
                buildHttpClient();
            }
        }

        public static void setConnectTimeoutByNetStatus(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info == null || !info.isConnectedOrConnecting()) {
                // 设置默认参数
                OkHttpUtil.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                return;
            }

            switch (info.getType()) {
                case ConnectivityManager.TYPE_WIFI:             // WIFI/有线网
                case ConnectivityManager.TYPE_WIMAX:
                case ConnectivityManager.TYPE_ETHERNET:
                    OkHttpUtil.setConnectTimeout(TIMEOUT_WIFI_4G, TimeUnit.SECONDS);
                    OkHttpUtil.setReadTimeout(TIMEOUT_WIFI_4G, TimeUnit.SECONDS);

                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (info.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_LTE:  // 4G
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                            OkHttpUtil.setConnectTimeout(TIMEOUT_WIFI_4G, TimeUnit.SECONDS);
                            OkHttpUtil.setReadTimeout(TIMEOUT_WIFI_4G, TimeUnit.SECONDS);
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS: // 3G
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                            OkHttpUtil.setConnectTimeout(TIMEOUT_3G, TimeUnit.SECONDS);
                            OkHttpUtil.setReadTimeout(TIMEOUT_3G, TimeUnit.SECONDS);
                            break;
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 2G
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                            OkHttpUtil.setConnectTimeout(TIMEOUT_2G, TimeUnit.SECONDS);
                            OkHttpUtil.setReadTimeout(TIMEOUT_2G, TimeUnit.SECONDS);
                            break;
                        default:
                            OkHttpUtil.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                            OkHttpUtil.setReadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                    }
                    break;
                default:
                    OkHttpUtil.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                    OkHttpUtil.setReadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
        }
    }
}
