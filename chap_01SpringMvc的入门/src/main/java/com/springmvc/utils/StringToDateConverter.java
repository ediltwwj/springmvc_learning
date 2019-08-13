package com.springmvc.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 13967
 * @date 2019/8/13 12:36
 * 把字符串转换为日期
 */
public class StringToDateConverter implements Converter<String, Date> {

    /**
     * @param source 传入的字符串
     * @return
     */
    @Override
    public Date convert(String source) {
        if(source == null){
            throw new RuntimeException("请您传入数据");
        }
        // 会导致原来yyyy/MM/dd的格式无法使用
        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.parse(source);
        }catch (Exception e){
            throw new RuntimeException("数据类型转换出现错误");
        }
    }
}
