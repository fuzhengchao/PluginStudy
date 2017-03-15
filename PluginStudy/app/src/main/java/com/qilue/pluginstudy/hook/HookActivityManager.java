package com.qilue.pluginstudy.hook;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.qilue.pluginstudy.SecondActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Created by fuzhengchao on 17/3/14.
 */

public class HookActivityManager {

    public static void hookActivityManager() throws Exception {
        hookAmsNative();

        replaceHCallback();
    }


    private static void replaceHCallback() throws Exception {
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Field activityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        activityThreadField.setAccessible(true);
        Object activityThreadObj = activityThreadField.get(null);

        Field hHandlerField = activityThreadClass.getDeclaredField("mH");
        hHandlerField.setAccessible(true);
        // 获取 H Handler
        Object hHandler =  hHandlerField.get(activityThreadObj);

        Class<?> handlerClass = Handler.class;
        Field callbackField = handlerClass.getDeclaredField("mCallback");
        callbackField.setAccessible(true);
        callbackField.set(hHandler, new HookHHandlerCallback());

    }

    private static void hookAmsNative() throws Exception {
        Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
        Field defaultField = activityManagerNativeClass.getDeclaredField("gDefault");
        defaultField.setAccessible(true);
        Object gDefault = defaultField.get(null); // 获取到 gDefault 变量的值

        // 反射 SingleTon
        Class<?> singletonClass = Class.forName("android.util.Singleton");
        Field instanceField = singletonClass.getDeclaredField("mInstance");
        instanceField.setAccessible(true);

        // 获取 ActivityManager 对象
        Object activityManagerObj = instanceField.get(gDefault);

        Class<?> iActivityManager = Class.forName("android.app.IActivityManager");

        // 获取 ams 动态代理
        Object amsProxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[] {iActivityManager}, new AmsHookHandler(activityManagerObj));

        // 用代理替换原有 ams
        instanceField.set(gDefault, amsProxy);
    }

    private static class AmsHookHandler implements InvocationHandler {
        private Object mAmsObj;

        public AmsHookHandler(Object amsObj) {
            mAmsObj = amsObj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("startActivity".equals(method.getName())) {
                Log.e("hook", "hook ActivityManager startActivity");

                int intentIndex = 0;
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Intent) {
                        intentIndex = i;
                        break;
                    }
                }

                Intent originIntent = (Intent) args[intentIndex];
                Intent newIntent = new Intent();
                newIntent.setComponent(new ComponentName("com.qilue.pluginstudy", SecondActivity.class.getCanonicalName()));
                newIntent.putExtra("origin_intent", originIntent);
                args[intentIndex] = newIntent;

            }


            return method.invoke(mAmsObj, args);
        }
    }
}
