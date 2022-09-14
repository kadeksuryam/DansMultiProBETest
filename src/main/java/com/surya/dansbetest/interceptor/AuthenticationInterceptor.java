package com.surya.dansbetest.interceptor;

import com.surya.dansbetest.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(!handlerMethod.getBeanType().getPackage().getName().startsWith("com.surya.dansbetest.controller")) {
            return true;
        }

        AllowAnonymous skipAuthentication = handlerMethod.getMethodAnnotation(AllowAnonymous.class);
        if(Objects.nonNull(skipAuthentication)) {
            return true;
        }

        if(!Objects.nonNull(request.getHeader(HttpHeaders.AUTHORIZATION))) {
            response.setStatus(HttpStatus.FORBIDDEN.value());

            return false;
        }

        String token = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer", "");

        return jwtUtil.isAuthenticated(token);
    }
}
