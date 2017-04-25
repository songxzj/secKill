package org.seckill.aspectpojo;



public aspect CriticAspect {

    public CriticAspect(){}

    pointcut aaaa() : execution(* org.seckill.service.SeckillService.getById(..));


    after() : aaaa(){
      System.out.println("xixi");
    }
}
