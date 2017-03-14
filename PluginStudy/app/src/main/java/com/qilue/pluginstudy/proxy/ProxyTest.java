package com.qilue.pluginstudy.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by fuzhengchao on 17/3/13.
 */

public class ProxyTest {
    public static void testProxy() {
        OperateProxy proxy = new OperateProxy(new OperateImpl());
        Operate operate = (Operate)(Proxy.newProxyInstance(Operate.class.getClassLoader(), new Class[] {Operate.class}, proxy));
        operate.operateMethod1();
    }
}
