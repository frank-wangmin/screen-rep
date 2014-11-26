package com.ysten.cache.key;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ognl.Ognl;

/**
 * 
 * @author li.t
 *
 */
public class GeneralKeyGenerator implements KeyGenerator {
	private Pattern pattern = Pattern.compile("#\\{([^}]*)}");
	private static final Map<Method,Map<String,Integer>> methodIndex = Collections.synchronizedMap(new HashMap<Method, Map<String,Integer>>());
	
	@Override
	public String generatorKey(Method method, String key, Object[] params) throws Exception {
		Map<String,Object> valueRoot = getValueRoot(method,params);
		StringBuffer stringBuffer = new StringBuffer();
		Matcher matcher = pattern.matcher(key);
        while (matcher.find()) {
		    Object value = Ognl.getValue(matcher.group(1), valueRoot);
		    if(value == null){
		        throw new Exception(new StringBuffer("the value of ").append(matcher.group(1)).append(" is null").toString());
		    }
            /*if(value instanceof String){
                String valueStr = (String)value;
                if(StringUtils.isEmpty(StringUtils.trimWhitespace(valueStr))){
                    throw new Exception(new StringBuffer("the value of ").append(matcher.group(1)).append(" is blank").toString());
                }
            }*/
			matcher.appendReplacement(stringBuffer, String.valueOf(value));
		}
		matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
	}
	
	private Map<String,Object> getValueRoot(Method method, Object[] params) {
	    Map<String, Object> root = new HashMap<String, Object>();
	    for(Entry<String, Integer> entry : getParamIndex(method).entrySet()){
	        root.put(entry.getKey(), params[entry.getValue()]);
	    }
        return root;
    }

    private Map<String,Integer> getParamIndex(Method method){
		Map<String, Integer> paramIndex = methodIndex.get(method);
		if(paramIndex==null){
			paramIndex = new MethodParser(method).parseMethod();
			methodIndex.put(method, paramIndex);
		}
		return paramIndex;
	}

}
