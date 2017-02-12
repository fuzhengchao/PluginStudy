package com.qilue.pluginstudy.aidl;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by fuzhengchao on 2017/2/12.
 */

public class AdditionServiceConnection implements ServiceConnection {
    IAdditionService mIAdditionService;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d("qilue", "AdditionServiceConnection#onServiceConnected");
        mIAdditionService = IAdditionService.Stub.asInterface((IBinder) service);

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d("qilue", "AdditionServiceConnection#onServiceDisconnected");
    }


    public int add(int a, int b) {
        int result = 0;
        try {
            result = mIAdditionService.add(a, b);
        } catch (Exception e) {

        }

        return result;
    }
}
