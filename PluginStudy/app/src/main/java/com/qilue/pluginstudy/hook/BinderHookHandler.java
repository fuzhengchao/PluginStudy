package com.qilue.pluginstudy.hook;

import android.content.ClipData;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by fuzhengchao on 2017/2/12.
 *
 * 感觉hook 的是IClipboard 对象
 */

public class BinderHookHandler implements InvocationHandler {
    // android.content.IClipboard 对象
    private Object mBase;

    public BinderHookHandler(IBinder binder, Class<?> stubClass) {
        try {
            Method asInterfaceMethod =stubClass.getDeclaredMethod("asInterface", IBinder.class);
            // 静态方法，第一个参数为null
            mBase = asInterfaceMethod.invoke(null, binder);
        } catch (Exception e) {
            throw new RuntimeException("hooked failed!");
        }

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getPrimaryClip".equals(method.getName())) {
            Log.d("qilue", "hook getPrimaryClip");
            return ClipData.newPlainText(null, "you are hooked");
        }

        if ("hasPrimaryClip".equals(method.getName())) {
            return true;
        }

        return method.invoke(mBase, args);
    }
}
