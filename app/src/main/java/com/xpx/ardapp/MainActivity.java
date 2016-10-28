package com.xpx.ardapp;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.xpx.base.wrapper.BaseActivity;
import com.xpx.base.wrapper.ToastUtil;

public class MainActivity extends BaseActivity {

    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;
    private static int REQUEST_ADMIN_CODE = 0X11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tryLockScreen();
    }

    private void tryLockScreen() {
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, AdminReceiver.class);
        if (mDevicePolicyManager.isAdminActive(mComponentName)) {
            lockScreen();
        } else {
            applyAdmin();
        }
    }

    private void lockScreen() {
        mDevicePolicyManager.lockNow();
        finish();
    }

    private void applyAdmin() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "申请管理员权限，是为了实现一键锁屏( ╯□╰ )");
        startActivityForResult(intent, REQUEST_ADMIN_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADMIN_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                ToastUtil.show(R.string.tip_for_uninstall, Toast.LENGTH_LONG);
                lockScreen();
            } else {
                ToastUtil.show(R.string.forbid_lock_screen);
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
