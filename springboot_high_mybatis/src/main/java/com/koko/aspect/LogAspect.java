package com.koko.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    //提取切点表达式
    @Pointcut("execution(* com..service.*.*(..))")
    private void pointcut() {

    }

    /* 编写不同位置的增强方法，并选中切点 */

    @Before(value = "pointcut()")
    public void before() {
        System.out.println("闪亮登场!!!");
    }

    @After(value="pointcut()")
    public void after(){
        System.out.println("遗憾离场...");
    }

}
