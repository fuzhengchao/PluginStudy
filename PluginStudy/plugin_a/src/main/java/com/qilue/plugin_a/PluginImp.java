package com.qilue.plugin_a;

import android.util.Log;

import com.example.MyClass;
import com.qilue.common.PluginInterface;


/**
 * Created by fuzhengchao on 17/3/16.
 */

public class PluginImp implements PluginInterface{

    public PluginImp () {
        new MyClass();
    }

    @Override
    public void run() {
        Log.e("test", "PluginImp run...");
    }

}
