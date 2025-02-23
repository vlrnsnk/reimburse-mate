package com.vlrnsnk.reimbursemate.aop;

import com.vlrnsnk.reimbursemate.exception.AuthorizationException;
import jakarta.servlet.http.HttpSession;
import com.vlrnsnk.reimbursemate.exception.AuthenticationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.vlrnsnk.reimbursemate.model.User;

@Aspect
@Component
public class SecurityAspect {

    private final HttpSession session;

    public SecurityAspect(HttpSession session) {
        this.session = session;
    }

    /**
     * Check if user is authenticated
     */
    @Before("(within(com.vlrnsnk.reimbursemate.controller..*))" +
            "&& !within(com.vlrnsnk.reimbursemate.controller.AuthController)")
    public void checkAuthenticated() {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new AuthenticationException("User is not authenticated");
        }
    }

    /**
     * Pointcut for methods annotated with @RequiresRole
     */
    @Pointcut("@annotation(com.vlrnsnk.reimbursemate.aop.RequiresRole)")
    public void requiresRolePointcut() {}

    /**
     * Check if user has the required role
     *
     * @param joinPoint ProceedingJoinPoint
     * @param requiresRole RequiresRole
     * @return Object
     */
    @Around("requiresRolePointcut() && @annotation(requiresRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RequiresRole requiresRole) throws Throwable {
        checkAuthenticated();

        User.Role userRole = (User.Role) session.getAttribute("role"); // Enum type from session
        User.Role requiredRole = requiresRole.value();  // Enum from annotation

        if (userRole == null || !userRole.equals(requiredRole)) {
            throw new AuthorizationException("User does not have the required role: " + requiredRole);
        }

        return joinPoint.proceed();
    }

}
