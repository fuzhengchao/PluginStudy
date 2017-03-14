package com.qilue.pluginstudy.hook;

import android.content.ClipData;
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
    private Class<?> mIinterface;
    private Class<?> mSubClass;
    private IBinder mBase;


    public BinderProxyHookHandler(IBinder base) {
        mBase = base;

        try {
            mIinterface = Class.forName("android.content.IClipboard");
            mSubClass = Class.forName("android.content.IClipboard$Stub");
        } catch (Exception e) {

        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            Log.d("qilue", "hook queryLocalInterface");

            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[] {IInterface.class, mIinterface}, new BinderHookHandler(mBase, mSubClass));
        }

        return method.invoke(mBase, args);
    }
}
