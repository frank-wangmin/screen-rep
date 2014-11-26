package com.ysten.local.bss.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggerUtils {
	
	/**
	 * 
	 * @param message
	 * @param logger
	 */
	public static void printDebugLogger(String message, Logger logger) {
		if(logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}
	
	/**
	 * 
	 * @param message
	 * @param logger
	 */
	public static void printInfoLogger(String message, Logger logger) {
		if(logger.isInfoEnabled()) {
			logger.info(message);
		}
	}
	
	/**
	 * 
	 * @param message
	 * @param logger
	 */
	public static void printErrorLogger(String message, Logger logger) {
		if(logger.isErrorEnabled()) {
			logger.error(message);
		}
	}

	public static void printErrorLogger(String message, Exception e, Logger logger) {
        if (logger.isErrorEnabled()) {
            logger.error(message + " exception: {}", ExceptionUtils.getFullStackTrace(e));
        }
    }

    public static String printErrorStack(Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }

}
