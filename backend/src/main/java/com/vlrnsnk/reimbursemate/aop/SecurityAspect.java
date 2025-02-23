package com.vlrnsnk.reimbursemate.aop;

import jakarta.servlet.http.HttpSession;
import com.vlrnsnk.reimbursemate.exception.AuthenticationException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    private final HttpSession session;

    public SecurityAspect(HttpSession session) {
        this.session = session;
    }

    @Before("(within(com.vlrnsnk.reimbursemate.controller..*))" +
            "&& !within(com.vlrnsnk.reimbursemate.controller.AuthController)")
    public void checkAuthenticated() {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new AuthenticationException("User is not authenticated");
        }
    }
}
