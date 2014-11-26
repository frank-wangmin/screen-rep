package com.ysten.local.bss.convert;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Created by frank on 14-6-10.
 */
public class DateStringConverter implements Converter<Date, String> {

    @Override
    public String convert(Date source) {
        if(source == null){
            return "";
        }
        return null;
    }
}
