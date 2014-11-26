package com.ysten.local.bss.bean.gd;

public class Head {
	
	private static String defaultXmlHeard = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ADI xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" BizDomain=\"2\">";

	private static String defaultXmlEnd = "</ADI>";
	
	private static String objectsStart = "<Objects>";
	
	private static String objectsEnd = "</Objects>";

	private static String mappingsStart = "<Mappings>";
	
	private static String mappingsEnd = "</Mappings>";
	
	public static String getObjectsStart() {
		return objectsStart;
	}

	public static String getObjectsEnd() {
		return objectsEnd;
	}

	public static String getMappingsStart() {
		return mappingsStart;
	}

	public static String getMappingsEnd() {
		return mappingsEnd;
	}

	public static String getDefaultXmlHeard() {
		return defaultXmlHeard;
	}

	public static String getDefaultXmlEnd() {
		return defaultXmlEnd;
	}
}
