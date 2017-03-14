package com.qilue.pluginstudy.hook;

import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by fuzhengchao on 17/3/14.
 */

public class AMSHookHandler implements InvocationHandler {
    private Object mObj;

    public AMSHookHandler(IBinder bindler) {

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
