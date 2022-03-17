package com.zsh.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyUtils {

    private static Advice advice=new Advice();

    public static <T> T  getProxy(T t){
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(t.getClass());
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                advice.before();
                method.invoke(t,objects);
                advice.after();
                return null;
            }
        });
        return (T)enhancer.create();
    }
}
