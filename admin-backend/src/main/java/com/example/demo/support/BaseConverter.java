package com.example.demo.support;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConverter<TFrom, TTo> implements EntityConverter<TFrom, TTo> {
    @Override
    public List<TTo> convert(List<TFrom> source) {
        return source.stream().map(this::convert).collect(Collectors.toList());
    }
}
