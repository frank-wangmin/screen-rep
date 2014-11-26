package com.ysten.local.bss.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterSpaceUtils {
	/***
     * 去掉字符串前后的空间，中间的空格保留
     * @param str
     * @return
     */
    public static String filterStartEndSpace(String str){
    	str = str.trim();
        while(str.startsWith(" ")){
        str = str.substring(1,str.length()).trim();
        }
        while(str.endsWith(" ")){
        str = str.substring(0,str.length()-1).trim();
        }
        return str;
    }
  /**
    注：\n 回车(\u000a) 
    \t 水平制表符(\u0009) 
    \s 空格(\u0008) 
    \r 换行(\u000d)
  */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest.trim();
    }
}