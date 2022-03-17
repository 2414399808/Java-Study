package com.zsh.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyUtils {
    private static Advice advice=new Advice();

    public static  <T> T getProxy(T t){
      return (T)Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        advice.before();
                        method.invoke(t, args);
                        advice.after();
                        return null;

                    }
                });
    }
}
