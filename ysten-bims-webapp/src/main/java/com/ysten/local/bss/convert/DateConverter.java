package com.ysten.local.bss.convert;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by frank on 14-5-13.
 */
public class DateConverter implements Converter<String, Date> {

    // 转换的格式
    private String dateFormatPattern;

    public DateConverter(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(dateFormatPattern);
        try {
            return df.parse(source);
        } catch (ParseException e) {
            throw new IllegalArgumentException(String.format("类型转换失败，需要格式%s，但格式是[%s]", dateFormatPattern, source));
        }
    }
}
