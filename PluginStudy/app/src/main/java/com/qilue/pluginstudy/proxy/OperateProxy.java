package com.qilue.pluginstudy.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by fuzhengchao on 17/3/13.
 */

public class OperateProxy implements InvocationHandler {
    private Object mTarget;

    public OperateProxy(Object target) {
        mTarget = target;
    }


    /**
     *
     * @param proxy 代理对象
     * @param method 真实对象方法
     * @param args 真是对象方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {



        Log.e("test", "OperateProxy: " + proxy.getClass() + "  method: " + method.getName());
        Object obj = method.invoke(mTarget, args);
        return obj;
    }
}
