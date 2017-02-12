package com.qilue.pluginstudy.hook;

import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by fuzhengchao on 2017/2/12.
 */

public class BinderProxyHookHandler implements InvocationHandler {
    IBinder mBase;
    Class<?> mStub;
    Class<?> mIinterface;

    public BinderProxyHookHandler(IBinder base) {
        this.mBase = base;

        try {
            this.mStub = Class.forName("android.content.IClipboard$Stub");
            this.mIinterface = Class.forName("android.content.IClipboard");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            Log.d("qilue", "hook queryLocalInterface");

            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),
                    new Class[]{IBinder.class, IInterface.class, this.mIinterface},
                    new BinderHookHandler(mBase, mStub));
        }

        return method.invoke(mBase, args);
    }
}
