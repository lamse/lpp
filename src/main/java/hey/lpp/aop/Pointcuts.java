package hey.lpp.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* hey.lpp.controller.product..*(..))")
    public void allProduct(){}

    @Pointcut("execution(* hey.lpp.controller.product.ProductController.viewProduct(..))")
    public void viewProduct(){}

    @Pointcut("execution(* hey.lpp.controller.api.product.ProductApiController.addProduct(..))")
    public void addApiProduct(){}

    @Pointcut("execution(* hey.lpp.controller.api.product.ProductOfferApiController.*(..))")
    public void offerApiProduct(){}

    @Pointcut("execution(* hey.lpp.controller.product.ProductOfferChatController.chatList(..))")
    public void viewOfferChat(){}

    @Pointcut("(allProduct() || offerApiProduct() || addApiProduct()) && !viewProduct() && !viewOfferChat()")
    public void loginCheck() {}
}
