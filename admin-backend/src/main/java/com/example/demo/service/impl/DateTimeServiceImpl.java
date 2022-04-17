package com.example.demo.service.impl;

import com.example.demo.service.DateTimeService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateTimeServiceImpl implements DateTimeService {
    @Override
    public Date now() {
        return new Date();
    }
}
