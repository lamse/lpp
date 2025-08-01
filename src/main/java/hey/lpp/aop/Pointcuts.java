package hey.lpp.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* hey.lpp.controller.product..*(..))")
    public void allProduct(){}
}
