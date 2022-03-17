package com.zsh.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class MyAspect {


    public void before(){
        System.out.println("前置增强。。。。。");
    }
    public void after(){
        System.out.println("最终增强。。。。。");
    }
    public void afterReturning(){
        System.out.println("后置增强。。。。。");
    }
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕增强。。。。。");
        Object proceed = pjp.proceed();
        System.out.println("前置增强。。。。。");
        return proceed;
    }
    public void afterThrowing(){
        System.out.println("异常增强。。。。。");
    }
}
