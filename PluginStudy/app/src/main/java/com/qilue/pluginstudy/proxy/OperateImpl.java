package com.qilue.pluginstudy.proxy;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by fuzhengchao on 17/3/13.
 */

public class OperateImpl implements Operate {
    @Override
    public void operateMethod1() {
        Log.e("test", "OperateImpl operateMethod1");
    }

    @Override
    public void operateMethod2() {
        Log.e("test", "OperateImpl operateMethod2");
    }

    @Override
    public void operateMethod3() {
        Log.e("test", "OperateImpl operateMethod3");
    }
}
