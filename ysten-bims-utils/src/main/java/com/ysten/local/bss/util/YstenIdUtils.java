package com.ysten.local.bss.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class YstenIdUtils {
	public static String createYstenId(String distCode, String mac){
		String ystenId = "";
		ystenId += distCode.trim().equals("")? "9999" : distCode.trim();
		ystenId += mac.trim().equals("")? "999999999999" : mac.trim(); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmssSSSS");
		ystenId += sdf.format(new Date());
		return ystenId;
	}
	public static String createYstenId(String distCode, String spId,String deviceType,String mac){
		StringBuffer ysten = new StringBuffer();
		String ystenId = "";
		ystenId = ysten.append(distCode.trim()).append(spId.trim()).append(deviceType.trim()).append(mac.trim()).toString();
		return appenZero(ystenId);
	}
	 public static String appenZero(String str)
	    {
	    	StringBuffer seq = new StringBuffer();
	    	int l = 32 - str.length();
			switch (l) {
			case 10:
				seq.append(str).append("0000000000");
				break;
			case 9:
				seq.append(str).append("000000000");
				break;
			case 8:
				seq.append(str).append("00000000");
				break;
			case 7:
				seq.append(str).append("0000000");
				break;
			case 6:
				seq.append(str).append("000000");
				break;
			case 5:
				seq.append(str).append("00000");
				break;
			case 4:
				seq.append(str).append("0000");
				break;
			case 3:
				seq.append(str).append("000");
				break;
			case 2:
				seq.append(str).append("00");
				break;
			case 1:
				seq.append(str).append("0");
				break;
			default:
				seq.append(str);
				break;
			}
			return seq.toString();
	    }
//    public static String createYstenId(String key,String ystenId) throws NoSuchAlgorithmException {
//        key = String.valueOf(System.currentTimeMillis()) + key;
//        StringBuffer buf = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(key.getBytes());
//            byte b[] = md.digest();
//            int i;
//            buf = new StringBuffer("");
//            for (int offset = 0; offset < b.length; offset++) {
//                i = b[offset];
//                if (i < 0)
//                    i += 256;
//                if (i < 16)
//                    buf.append("0");
//                buf.append(Integer.toHexString(i));
//            }
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        int length = ystenId.length();
//        return ystenId+buf.toString().substring(0, 32-length);
//    }
	
	
}