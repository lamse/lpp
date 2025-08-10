package hey.lpp.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* hey.lpp.controller.product..*(..))")
    public void allProduct(){}

    @Pointcut("execution(* hey.lpp.controller.api.product..*(..))")
    public void allApiProduct(){}

    @Pointcut("execution(* hey.lpp.controller.product.ProductController.viewProduct(..))")
    public void viewProduct(){}

    @Pointcut("execution(* hey.lpp.controller.api.product.ProductApiController.viewProduct(..))")
    public void viewApiProduct(){}

    @Pointcut("execution(* hey.lpp.controller.product.ProductOfferChatController.chatList(..))")
    public void viewOfferChat(){}

    @Pointcut("(allProduct() || allApiProduct()) && !viewProduct() && !viewApiProduct() && !viewOfferChat()")
    public void loginCheck() {}
}
