package com.ysten.local.bss.device.bean;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于数据库Date类型的序列生成器
 * @author li.t
 *
 */
public class NumberGenerator{

    private static final Logger LOGGER = LoggerFactory.getLogger(NumberGenerator.class);
    
    private static final int THREE = 3;
    private static final int NINE_THOUSAND_NINE_HUNDRED_AND_NINETY_NINE = 9999;
    
    private static AtomicLong increment = new AtomicLong(0);
    
    private static boolean init = false;
    
    @PostConstruct
    public void init() {
        if( !init ) {
            initData();
            init = true;
        }
    }
    
    private void initData(){
    }
    
    
    public static String appenZero(String str)
    {
    	StringBuffer seq = new StringBuffer();
    	int l = str.length();
		switch (l) {
		case 1:
			seq.append("00").append(str);
			break;
		case 2:
			seq.append("0").append(str);
			break;
		default:
			seq.append(str);
			break;
		}
		return seq.toString();
    }
    
    
    public static String getNextCode() {
    	InetAddress localAddress = null;
    	String str ="";
        try {
            localAddress = InetAddress.getLocalHost();
            str = generationId(localAddress.getHostAddress().split("\\.")[THREE]);
        } catch (Exception e) {
        	LOGGER.error("get local host exception{}.",e);
        	return "";
        }
        return str;
    }
    
    public static  String generationId(String serverId) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer generationId = new StringBuffer();

		generationId.append(sdf.format(new Date()));	
		generationId.append(appenZero(serverId));
		generationId.append(appenZero(String.valueOf(getNext())));
		return generationId.toString();
	}
    
	private static synchronized long getNext() {
		long seqLong = increment.getAndIncrement();
		if (seqLong > NINE_THOUSAND_NINE_HUNDRED_AND_NINETY_NINE) {
			increment = new AtomicLong(0);
			seqLong = increment.getAndIncrement();
		}
		return seqLong;
	}

}
