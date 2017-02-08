package com.qilue.pluginstudy;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.qilue.pluginstudy.hook.HookInstrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by fuzhengchao on 2017/2/9.
 */

public class PluginApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        try {
            // 先获取到当前的ActivityThread对象
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            // 拿到原始的 mInstrumentation字段
            Field instrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            instrumentationField.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) instrumentationField.get(currentActivityThread);

            // 创建代理对象
            Instrumentation hookInstrumentation = new HookInstrumentation(instrumentation);

            // 偷梁换柱
            instrumentationField.set(currentActivityThread, hookInstrumentation);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
