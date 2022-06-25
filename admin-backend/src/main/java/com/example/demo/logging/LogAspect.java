package com.example.demo.logging;

import lombok.RequiredArgsConstructor;

import com.example.demo.events.RequestReceived;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final LogService logService;

    @Before("execution(* com.example.demo.controller.*.*(..))")
    public void log(JoinPoint joinPoint) {
        // TODO: Create some more complicated message here, containing IP address, some headers and what not
    	String method = joinPoint.toLongString();
        logService.save(new RequestReceived(method));
    }
}
