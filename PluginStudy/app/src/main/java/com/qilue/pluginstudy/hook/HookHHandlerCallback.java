package com.qilue.pluginstudy.hook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;

/**
 * Created by fuzhengchao on 17/3/15.
 *
 * 设置给ActivityManager H handler
 */

public class HookHHandlerCallback implements Handler.Callback {
    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what) {
            case 100:  //ActivityThread里面 "LAUNCH_ACTIVITY" 这个字段的值是100
                handleLaunchActivity(msg);
                break;
        }


        return false;
    }

    private void handleLaunchActivity(Message msg) {
        Object activityClientRecordObj = msg.obj;

        try {
            Field intentField = activityClientRecordObj.getClass().getDeclaredField("intent");
            intentField.setAccessible(true);
            Intent intent = (Intent) intentField.get(activityClientRecordObj);

            Intent originIntent = intent.getParcelableExtra("origin_intent");

            // 将 intent 中目标地址换成原始目标地址
            intent.setComponent(originIntent.getComponent());


        } catch (Exception e) {

        }
    }
}
