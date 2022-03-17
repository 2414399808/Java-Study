package com.zsh.aopanno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component("myAspect")
@Aspect//标注当前myAspect是一个切面类
public class MyAspect {

    @Pointcut("execution(* com.zsh.aopanno.*.*(..))")
    public void pointxcut(){}
    //配置前置通知
//    @Before(value = "execution(* com.zsh.aopanno.*.*(..))")
    @Before("pointxcut()")
    public void before(){
        System.out.println("前置增强...........");
    }
    @AfterReturning("execution(* com.zsh.aopanno.*.*(..))")
    public void afterReturning(){
        System.out.println("后置增强...........");
    }

    @Around("execution(* com.zsh.aopanno.*.*(..))")
                      //正在执行的连接点（即切点）
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕前增强...........");
        //切点方法
        Object proceed = pjp.proceed();
        System.out.println("环绕后增强...........");
        return proceed;
    }

    @AfterThrowing("execution(* com.zsh.aopanno.*.*(..))")
    public void afterThrowing(){

        System.out.println("异常抛出异常");
    }

    @After("execution(* com.zsh.aopanno.*.*(..))")
    public void after(){
        System.out.println("最终增强...........");
    }
}
