package com.example.demo.support;

import org.springframework.core.convert.converter.Converter;

import java.util.List;

public interface EntityConverter<TFrom, TTo> extends Converter<TFrom, TTo> {

    List<TTo> convert(List<TFrom> source);

}
