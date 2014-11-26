package com.ysten.local.bss.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ysten.local.bss.util.bean.Required;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EntityUtils {

    @SuppressWarnings("unchecked")
    public static Object createTargetCopyFromSrc(Object source, Object target, String[] filterFields) throws Exception {
        @SuppressWarnings("rawtypes")
        Class classType = source.getClass();
        Object objectCopy = target.getClass().getConstructor(new Class[]{}).newInstance(new Object[]{});
        Field fields[] = classType.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field newField = fields[i];
            String fieldName = newField.getName();
            if ("serialVersionUID".equals(fieldName) || ArrayUtils.contains(filterFields, fieldName)) {
                continue;
            }
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            String setMethodName = "set" + firstLetter + fieldName.substring(1);
            Method getMethod = classType.getMethod(getMethodName, new Class[]{});
            Method setMethod = target.getClass().getMethod(setMethodName, new Class[]{newField.getType()});
            Object value = getMethod.invoke(source, new Object[]{});
            setMethod.invoke(objectCopy, new Object[]{value});
        }
        return objectCopy;
    }


    public static String checkNull(Object target) {
        return  EntityUtils.checkNull(target, true, "all");
    }

    public static String checkNull(Object target, boolean strict, String group) {
        if(group == null) group = "all";
        String message = null;
        try {
            Field[] fields = target.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);

                Annotation annotation = field.getAnnotation(Required.class);
                if (annotation != null) {
                    String[] g = ((Required)annotation).group();
                    if(group.equals("all") || ArrayUtils.contains(g, group)){
                        Object value = field.get(target);
                        String type = field.getType().getName();

                        boolean checkResult = EntityUtils.process(strict, type, value);

                        if(!checkResult){
                            message = ((Required)annotation).message();
                        }

                        if("".equals(message) ) {
                            message = "Parameter \"" + field.getName() + "\" is null";
                        }

                        boolean into = ((Required)annotation).into();
                        if(into){
                            message = EntityUtils.checkNull(value, strict, group);
                        }
                    }
                }

                field.setAccessible(false);

                if(message != null){
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            message = "System error";
        }
        return message;
    }

    private static boolean process(boolean strict, String typeName, Object value){
        boolean checkResult = true;
        if(strict){
            if("java.lang.String".equals(typeName)){
                String realValue = (String) value;
                if(StringUtils.isEmpty(realValue)){
                    checkResult = false;
                }
            } else {
                if(value == null){
                    checkResult = false;
                }
            }
        } else {
            if(value == null){
                checkResult = false;
            }
        }
        return checkResult;
    }
}
