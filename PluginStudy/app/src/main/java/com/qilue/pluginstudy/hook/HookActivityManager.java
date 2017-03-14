package com.qilue.pluginstudy.hook;

import android.os.IBinder;
import android.os.IInterface;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by fuzhengchao on 17/3/14.
 */

public class HookActivityManager {

    public static void hookActivityManager() throws Exception {

        Class<?> amsClass = Class.forName("android.app.IActivityManager");

        // 下面这一段的意思实际就是: ServiceManager.getService("clipboard");
        // 只不过 ServiceManager这个类是@hide的
        Class<?> serviceManager = Class.forName("android.os.ServiceManager");
        Method getService = serviceManager.getDeclaredMethod("getService", String.class);
        IBinder rawBinder = (IBinder) getService.invoke(null, ACTIVITY_SERVICE);

        // 然后在 queryLocalInterface 返回一个IInterface对象, hook掉我们感兴趣的方法即可.
        IBinder hookedBinder = (IBinder) Proxy.newProxyInstance(serviceManager.getClassLoader(),
                new Class<?>[]{IBinder.class},
                new AMSProxyHookHandler(rawBinder));

        // 把这个hook过的Binder代理对象放进ServiceManager的cache里面
        // 以后查询的时候 会优先查询缓存里面的Binder, 这样就会使用被我们修改过的Binder了
        Field cacheField = serviceManager.getDeclaredField("sCache");
        cacheField.setAccessible(true);
        Map<String, IBinder> cache = (Map) cacheField.get(null);
        cache.put(ACTIVITY_SERVICE, hookedBinder);
    }
}
