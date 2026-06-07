package kkashin.dev.aspects;

import kkashin.dev.annotations.NotNullArgs;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
@Aspect
public class NotNullArgsAspect {

    @Around("@annotation(notNullArgs)")
    public Object aroundNullableMethod(
            ProceedingJoinPoint pjp,
            NotNullArgs notNullArgs
    ) {
        Object[] args = pjp.getArgs();

        boolean hasNulls = Arrays.stream(args).anyMatch(Objects::isNull);

        if (hasNulls) {
            throw new IllegalArgumentException(
                    "[NOT_NULL_ARGS_ASPECT]: Cannot pass null as arg of method " + pjp.getSignature().getName()
            );
        }

        try {
            return pjp.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
