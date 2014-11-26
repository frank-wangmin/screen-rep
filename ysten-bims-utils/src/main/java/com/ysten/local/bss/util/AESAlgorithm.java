package com.ysten.local.bss.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AESAlgorithm {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(AESAlgorithm.class);
//	
//	/**
//	 * encryption
//	 * @param content
//	 * @param password
//	 * @return
//	 * @author HanksXu
//	 * @date 2013-10-18
//	 */
//	private static byte[] encryptJSYDPassword(String content, String password, String instance) {
//		BouncyCastleProvider provider = new BouncyCastleProvider();
//		try {
//			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
//			Cipher cipher = Cipher.getInstance(instance, provider);// 创建密码器
//			byte[] byteContent = content.getBytes("utf-8");
//			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
//			byte[] result = cipher.doFinal(byteContent);
//			key = null;
//			cipher = null;
//			return result; // 加密
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (NoSuchPaddingException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (InvalidKeyException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (IllegalBlockSizeException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (BadPaddingException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		}
//		return null;
//	}
//	
//
//	/**
//	 * encryption
//	 * @param content
//	 * @param password
//	 * @return
//	 * @author HanksXu
//	 * @date 2013-10-18
//	 */
//	private static byte[] encrypt(String content, String password, String instance) {
//		try {
//			KeyGenerator kgen = KeyGenerator.getInstance("AES");
//			kgen.init(128, new SecureRandom(password.getBytes()));
//			SecretKey secretKey = kgen.generateKey();
//			byte[] enCodeFormat = secretKey.getEncoded();
//			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//			Cipher cipher = Cipher.getInstance(instance);// 创建密码器
//			byte[] byteContent = content.getBytes("utf-8");
//			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
//			byte[] result = cipher.doFinal(byteContent);
//			return result; // 加密
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (NoSuchPaddingException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (InvalidKeyException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (IllegalBlockSizeException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (BadPaddingException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		}
//		return null;
//	}
//
//	/**
//	 * 
//	 * @param content
//	 * @param password
//	 * @param instance
//	 * @return
//	 */
//	private static byte[] decrypt(byte[] content, String password, String instance) {
//		try {
//			KeyGenerator kgen = KeyGenerator.getInstance("AES");
//			kgen.init(128, new SecureRandom(password.getBytes()));
//			SecretKey secretKey = kgen.generateKey();
//			byte[] enCodeFormat = secretKey.getEncoded();
//			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//			Cipher cipher = Cipher.getInstance(instance);
//			cipher.init(Cipher.DECRYPT_MODE, key);
//			byte[] result = cipher.doFinal(content);
//			return result;
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (NoSuchPaddingException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (InvalidKeyException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (IllegalBlockSizeException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		} catch (BadPaddingException e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage());
//		}
//		return null;
//	}
//	
//	/**
//	 * convert binary into hexadecimal
//	 * @param buf
//	 * @return
//	 * @author HanksXu
//	 * @date 2013-10-18
//	 */
//	 private static String parseByte2HexStr(byte buf[]) {
//		if(null == buf || buf.length <= 0) {
//			return null;
//		}
//		StringBuffer buffer = new StringBuffer();
//		for (int i = 0; i < buf.length; i++) {
//			String hex = Integer.toHexString(buf[i] & 0xFF);
//			if (hex.length() == 1) {
//				hex = '0' + hex;
//			}
//			buffer.append(hex.toUpperCase());
//		}
//		return buffer.toString();
//	}
//
//	/**
//	 * 
//	 * @param hexStr
//	 * @return
//	 * @author HanksXu
//	 * @date 2013-10-18
//	 */
//	private static byte[] parseHexStr2Byte(String hexStr) {
//		if (hexStr.length() < 1)
//			return null;
//		byte[] result = new byte[hexStr.length() / 2];
//		for (int i = 0; i < hexStr.length() / 2; i++) {
//			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
//			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
//					16);
//			result[i] = (byte) (high * 16 + low);
//		}
//		return result;
//	}
//	
//	/**
//	 * 
//	 * @param content
//	 * @param password
//	 * @return
//	 * @author HanksXu
//	 * @date 2013-10-21
//	 */
//	public static String getToken(String content, String password, String instance) {
//		byte[] encryptResult = encrypt(content, password, instance);
//        return parseByte2HexStr(encryptResult);
//	}
//	
	/**
	 * encrypt the password for device login JSYD.
	 * @param password
	 * @param key
	 * @return
	 */
	public static String getEncryptPassword(String password, String key) {
//		byte[] encryptResult = getToken(password, key, "AES/ECB/ZeroBytePadding");
//		return parseByte2HexStr(encryptResult);
		try {
			return getToken(password, key, "AES/ECB/ZeroBytePadding");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			return "";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			return "";
		} catch (BadPaddingException e) {
			e.printStackTrace();
			return "";
		}
	}
//	
//	/**
//	 * 
//	 * @param content
//	 * @param password
//	 * @return
//	 * @author HanksXu
//	 * @date 2013-10-21
//	 */
//	public static String getContentFromToken(String content, String password, String instance) {
//		 byte[] decryptFrom = parseHexStr2Byte(content);
//	     byte[] decryptResult = decrypt(decryptFrom, password, instance);
//	     return new String(decryptResult);
//	}
//	
//	public static void main(String[] args) {
//		String password = "******";
//		System.out.println(getEncryptPassword(password, "1r39df456j8wet45"));
//		
//	}

	private static final BouncyCastleProvider provider = new BouncyCastleProvider();

	private static byte[] encrypt(String content, String password, String instance) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance(instance, provider);// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
	}

	private static byte[] decrypt(byte[] content, String password, String instance) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance(instance, provider);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result;
	}
	
	private static String parseByte2HexStr(byte buf[]) {
			if(null == buf || buf.length <= 0) {
				return null;
			}
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < buf.length; i++) {
				String hex = Integer.toHexString(buf[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				buffer.append(hex);
			}
			return buffer.toString();
		}

	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	/**
	 * 密码加密
	 * @param content 密码
	 * @param password 秘钥
	 * @param instance 加密算法
	 * @return 加密后的密文
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static String getToken(String content, String password, String instance) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		byte[] encryptResult = encrypt(content, password, instance);
		return parseByte2HexStr(encryptResult);
	}
	
	/**
	 * 密码解密
	 * @param content 密文
	 * @param password 秘钥
	 * @param instance 算法
	 * @return 解密后的明文
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static String getContentFromToken(String content, String password, String instance) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		byte[] decryptFrom = parseHexStr2Byte(content);
		byte[] decryptResult = decrypt(decryptFrom, password, instance);
		return new String(decryptResult);
	}

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		String encryptResult = getToken("123456", "1r39df456j8wet45", "AES/ECB/ZeroBytePadding");
		System.out.println("加密前：" + encryptResult);
		
		//解密
		String decryptResult = getContentFromToken("f147dac14e10e317234fec29140c0519","1r39df456j8wet45","AES/ECB/ZeroBytePadding");
		System.out.println("解密后：" + decryptResult);
	}
}
