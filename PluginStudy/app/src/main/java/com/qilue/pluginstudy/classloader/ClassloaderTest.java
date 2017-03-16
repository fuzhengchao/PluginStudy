package com.qilue.pluginstudy.classloader;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.qilue.common.PluginInterface;

import java.io.File;

import dalvik.system.DexClassLoader;

/**
 * Created by fuzhengchao on 17/3/16.
 */

public class ClassloaderTest {

    public static void testLoadClass(Context context) {
        ClassLoader classLoader = loadApk(context);

        try {

            Class<?> pluginClass = classLoader.loadClass("com.qilue.plugin_a.PluginImp");
//            Class<?> pluginInterfaceClass = classLoader.loadClass("com.qilue.common.PluginInterface");
//            Object obj = pluginClass.newInstance();
//
//            Method method = pluginClass.getDeclaredMethod("run");
//            method.invoke(obj);


            PluginInterface p = (PluginInterface) pluginClass.newInstance();
            p.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static ClassLoader loadApk(Context context) {
        String dexPath = Environment.getExternalStorageDirectory() + File.separator + "plugin_a-debug.apk";
        String dexDir = context.getDir("dex", 0).getAbsolutePath();

        File f = new File(dexPath);
        if (f.exists()) {
            Log.e("test", "plugin exists");
        }

        ClassLoader classLoader = new DexClassLoader(dexPath, dexDir,null, context.getClassLoader());
        return classLoader;
    }
}
