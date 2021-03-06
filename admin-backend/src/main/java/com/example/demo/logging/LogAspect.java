package com.example.demo.logging;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.events.RequestReceived;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final LogService logService;

    @Before("execution(* com.example.demo.controller.*.*(..))")
    public void log(JoinPoint joinPoint) {
        // TODO: Create some more complicated message here, containing IP address, some headers and what not
    	String method = joinPoint.toLongString();
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        logService.save(new RequestReceived(method, request));
    }
}
