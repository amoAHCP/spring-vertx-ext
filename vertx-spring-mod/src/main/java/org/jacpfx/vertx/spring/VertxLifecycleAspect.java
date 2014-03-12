package org.jacpfx.vertx.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Created by amo on 05.03.14.
 */
@Aspect
public class VertxLifecycleAspect {

    @Autowired
    public ApplicationContext context;

    @After(value = "execution(* *.start(..))")
    public void afterStart(JoinPoint joinPoint) {

        System.out.println("afterStart() is running!");
        System.out.println("hijacked : " + joinPoint.getTarget());
        System.out.println("******"+joinPoint.getSignature());
    }

    // TODO check if @SpringVertx  is available
    @After(value = "execution(* *.stop(..))")
    public void afterStop(JoinPoint joinPoint) {

        System.out.println("afterStop() is running!");
        System.out.println("hijacked : " + joinPoint.getTarget());
        System.out.println("******"+joinPoint.getSignature());
        if(AnnotationConfigApplicationContext.class.isAssignableFrom(context.getClass())) {
            final ApplicationContext parent = AnnotationConfigApplicationContext.class.cast(context).getParent();
            if(parent==null) {
                AnnotationConfigApplicationContext.class.cast(context).stop();
            }else {
                if(GenericApplicationContext.class.isAssignableFrom(parent.getClass())) {
                    GenericApplicationContext.class.cast(parent).stop();
                }
            }
        }

    }
}
