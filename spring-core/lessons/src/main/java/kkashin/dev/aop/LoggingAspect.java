package kkashin.dev.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

//    @Before("execution(* kkashin.dev.TaskManager.*(..))")
//    public void logBefore(JoinPoint joinPoint) {
//        System.out.println("TaskManager before method call: " + joinPoint.getSignature().getName());
//    }
//
//    @AfterReturning(
//            value = "execution(* kkashin.dev.TaskManager.*(..))",
//            returning = "result"
//    )
//    public void logAfter(JoinPoint joinPoint, Object result) {
//        System.out.println(
//                "TaskManager after returning: " +
//                joinPoint.getSignature().getName() +
//                "\nresult = " + result
//        );
//    }
//
//    @AfterThrowing(
//            value = "execution(* kkashin.dev.TaskManager.*(..))",
//            throwing = "exc"
//    )
//    public void afterThrowing(JoinPoint joinPoint, Exception exc) {
//        System.out.println(
//                "TaskManager after throwing: " +
//                        joinPoint.getSignature().getName() +
//                        "\nexception = " + exc.getMessage()
//        );
//    }
//
//    @After("execution(* kkashin.dev.TaskManager.*(..))")
//    public void logAfter(JoinPoint joinPoint) {
//        System.out.println("TaskManager after method call: " + joinPoint.getSignature().getName());
//    }
//
//    @Around("execution(* kkashin.dev.TaskManager.*(..))")
//    public Object logAround(
//            ProceedingJoinPoint proceedingJoinPoint
//    ) throws Throwable {
//        System.out.println("Before call");
//        Object result = proceedingJoinPoint.proceed();
//        System.out.println("After call");
//
//        return result;
//    }

    @Before("@annotation(loggable)")
    public void log(JoinPoint joinPoint, Loggable loggable) {
        int times = loggable.times();

        if (times > 0) {
            for (int i = 0; i < times; i++) {
                System.out.printf("LOG %s: before method=%s\n",
                        loggable.value(),
                        joinPoint.getSignature().getName());
            }
        } else {
            throw new RuntimeException("Times in annotation LOGGABLE cannot be less than one");
        }
    }
}
