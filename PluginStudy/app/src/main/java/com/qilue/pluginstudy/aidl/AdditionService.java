package com.qilue.pluginstudy.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

// 另一个进程中
public class AdditionService extends Service {
    public AdditionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IAdditionService.Stub() {
            @Override
            public int add(int a, int b) throws RemoteException {
                return a + b;
            }
        };
    }
}
