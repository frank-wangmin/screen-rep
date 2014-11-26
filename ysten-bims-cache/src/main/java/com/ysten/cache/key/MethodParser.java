package com.ysten.cache.key;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.ysten.cache.annotation.KeyParam;

/**
 * 方法解析器<br/>
 * 根据注解对方法进行解析
 * @author li.t
 *
 */
public class MethodParser {
	private Method targetMethod;
	
	public MethodParser(Method targetMethod){
		this.targetMethod = targetMethod;
	}

	public Map<String,Integer> parseMethod() {
		Annotation[][] annotations = this.targetMethod.getParameterAnnotations();
		Map<String,Integer> paramIndex = new HashMap<String, Integer>();
		if(annotations!= null && annotations.length>0){
			for(int i=0 ;i<annotations.length;i++){
				Annotation[] paramAnnotations = annotations[i];
				String name = getName(paramAnnotations);
				if(name!=null && !"".equals(name.trim())){
					paramIndex.put(name.trim(), i);
				}
			}
		}
		return paramIndex;
	}

	private String getName(Annotation[] paramAnnotations) {
		if(paramAnnotations!=null&&paramAnnotations.length>0){
			for(Annotation annotation : paramAnnotations){
				if(annotation instanceof KeyParam){
					return ((KeyParam)annotation).value();
				}
			}
		}
		return null;
	}
	
}
