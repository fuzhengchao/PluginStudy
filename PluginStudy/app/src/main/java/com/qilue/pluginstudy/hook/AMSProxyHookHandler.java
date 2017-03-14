package com.qilue.pluginstudy.hook;

import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by fuzhengchao on 17/3/14.
 */

public class AMSProxyHookHandler implements InvocationHandler {
    private IBinder mBase;
    private Class<?> mIActivityManager;

    public AMSProxyHookHandler(IBinder base) {
        mBase = base;

        try {
            mIActivityManager = Class.forName("android.app.IActivityManager");
        } catch (Exception e) {

        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            Log.d("qilue", "hook queryLocalInterface");
            return mBase;
//            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[] {IInterface.class, mIActivityManager}, new BinderHookHandler(mBase, ));
        } else if ("startActivity".equals(method.getName())) {
            Log.d("qilue", "hook startActivity");
        }

        return method.invoke(mBase, args);
    }
}
