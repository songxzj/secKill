package org.seckill.aspectpojo;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Audience {

    /**
     * 定义命名的切点
     */
    @Pointcut("execution(* org.seckill.service.SeckillService.getById(..))")   // 不带参数
    public void getById(){}

    @Pointcut("execution(* org.seckill.service.SeckillService.cc(long, int)) && args(seckillId, num)")  // 带参数
    public void cc(long seckillId, int num){}


    /**
     * 目标方法调用之前执行
     */
    @Before("getById()")
    public void before(){
        System.out.println("BEFORE:------------------------------------------------");
    }

    @Before("cc(seckillId, num)")
    public void showSeckillId(long seckillId, int num){
        System.out.println("seckillId - 1 = " + (seckillId -1));
        System.out.println("seckillId = " + seckillId);
        System.out.println("seckillId + 1 = " + (seckillId + 1));
    }

    /**
     * 目标方法返回后调用
     */
    @AfterReturning("getById()")
    public void afterReturning(){
        System.out.println("AFTERRETURNING:------------------------------------------------");
    }

    /**
     * 目标方法抛出异常后调用
     */
    @AfterThrowing("getById()")
    public void afterThrowing(){
        System.out.println("AFTERTHROWING:------------------------------------------------");
    }


    /**
     * 环绕通知
     * @param joinPoint
     */
    /*@Around("getById()")
    public void around(ProceedingJoinPoint joinPoint){
        try {
            System.out.println("sec");
            joinPoint.proceed();
            System.out.println("AFTERRETURNING:------------------------------------------------");
        } catch (Throwable e) {
            System.out.println("AFTERTHROWING:------------------------------------------------");
        }
    }*/




}
