package com.qilue.pluginstudy;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import com.qilue.pluginstudy.hook.HookActivityManager;
import com.qilue.pluginstudy.hook.HookClipboard;
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

//        HookInstrumentation.hookInstrumentation();

        // hook 剪切板
        try {
            HookActivityManager.hookActivityManager();
            HookClipboard.hookClipboard();

        } catch (Exception e) {

        }
    }
}
