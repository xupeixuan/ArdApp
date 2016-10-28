package com.xpx.ardapp;

import android.content.Intent;
import android.os.Bundle;

import com.xpx.base.wrapper.BaseActivity;

/**
 * Created by xupeixuan on 2016/10/28.
 */

public class PreStartActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(PreStartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
