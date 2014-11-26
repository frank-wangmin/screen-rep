package com.ysten.cache.key;

import java.util.ArrayList;
import java.util.List;

import com.ysten.cache.annotation.KeyParam;
import com.ysten.cache.annotation.MethodCache;
import com.ysten.cache.annotation.MethodFlush;

public class MethodClass {

	@MethodCache(key = "com:ysten:iptv:cache:integer")
	public Integer getIntger() {
		return 1;
	}
	
	@MethodCache(key = "com:ysten:iptv:cache:int")
	public int getInt() {
		return 1;
	}
	
	@MethodCache(key = "com:ysten:iptv:cache:list")
	public List getList(){
		return new ArrayList();
	}
	
	@MethodCache(key = "com:ysten:iptv:cache:integerArrary")
	public Integer[] getIntegerArrary(){
		return new Integer[]{1,2,3};
	}
	
	@MethodCache(key = "com:ysten:iptv:cache:intarrary")
	public int[] getIntArrary(){
		return new int[]{1,2,3};
	}
	
	@MethodCache(key = "com:ysten:iptv:cache:integer:#{i}",resetTimeOut=true,useTimeOut=true)
	public Integer getInteger(@KeyParam("i")Integer i){
		return i;
	}
	
	@MethodFlush(keys={"com:ysten:iptv:cache:integer:#{i}"})
	public void clearData(@KeyParam("i")Integer i){
		
	}
	
	public void updatePersion(@KeyParam("person")Person person){
	    
	}
	
	public static class Person{
	    String name;
	    String pwd;
	    String age;
	    
        public Person(String name, String pwd, String age) {
            super();
            this.name = name;
            this.pwd = pwd;
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPwd() {
            return pwd;
        }
        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
        public String getAge() {
            return age;
        }
        public void setAge(String age) {
            this.age = age;
        }
	    
	}

}