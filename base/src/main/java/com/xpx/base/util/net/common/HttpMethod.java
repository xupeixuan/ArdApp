package com.xpx.base.util.net.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by xupeixuan on 2016/10/23.
 */

public class HttpMethod {
    public static final int GET = 1;
    public static final int POST = 1<<1;
    public static final int DELETE = 1<<2;
    public static final int PUT = 1<<3;
    public static final int HEAD = 1<<4;
    public static final int OPTIONS = 1<<5;

    @IntDef({HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.HEAD, HttpMethod.OPTIONS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HttpMethodType{}
}
