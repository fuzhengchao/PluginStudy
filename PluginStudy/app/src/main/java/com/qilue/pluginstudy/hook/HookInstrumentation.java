package com.qilue.pluginstudy.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by fuzhengchao on 2017/2/8.
 */

public class HookInstrumentation extends Instrumentation {
    private Instrumentation mBase;

    public HookInstrumentation(Instrumentation base) {
        mBase = base;
    }

    // 由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        Log.d("hook", "HookInstrumentation#execStartActivity");

        try {
            Method execStartActivityMethod = Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class, IBinder.class,
                    IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivityMethod.setAccessible(true);
            return (ActivityResult) execStartActivityMethod.invoke(mBase, who, contextThread, token, target, intent, requestCode, options);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
