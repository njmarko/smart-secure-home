package com.example.demo.support;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConverter<TFrom, TTo> implements EntityConverter<TFrom, TTo> {
    @Override
    public List<TTo> convert(List<TFrom> source) {
        return source.stream().map(this::convert).collect(Collectors.toList());
    }

    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Autowired
    public final void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}
