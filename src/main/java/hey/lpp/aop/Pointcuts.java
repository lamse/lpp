package hey.lpp.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    //hello.springaop.app 패키지와 하위 패키지
    @Pointcut("execution(* hey.lpp.controller.product..*(..))")
    public void allProduct(){}
}
