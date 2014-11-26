package com.ysten.local.bss.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * post以流方式发送请求
     * 
     * @param url 发送目标
     * @param paramContent 发送内容
     * @return StringBuffer 请求响应
     */
	public static StringBuffer submitPost(String url, String paramContent) {
		StringBuffer responseMessage = null;
		java.net.URLConnection connection = null;
		java.net.URL reqUrl = null;
		OutputStreamWriter reqOut = null;
		InputStream in = null;
		BufferedReader br = null;
		try {
		    LOGGER.info("post method end then url - :" + url + "\ncontent - :" + paramContent);
			responseMessage = new StringBuffer();
			reqUrl = new java.net.URL(url);
			connection = reqUrl.openConnection();
			connection.setDoOutput(true);
			reqOut = new OutputStreamWriter(connection.getOutputStream());
			reqOut.write(paramContent);
			reqOut.flush();
			int charCount = -1;
			in = connection.getInputStream();

			br = new BufferedReader(new InputStreamReader(in, "GBK"));
			while ((charCount = br.read()) != -1) {
				responseMessage.append((char) charCount);
			}
			LOGGER.info("post method end then response message - :" + responseMessage);
		} catch (Exception ex) {
		    LOGGER.error("post data exception - :", ex);
		} finally {
			try {
				if(in != null) in.close();
				if(reqOut != null) reqOut.close();
			} catch (Exception e) {
			    LOGGER.error("close connection exception - :", e);
			}

		}
		return responseMessage;
	}
	/**
	 * 将要返回的结果以流的方式写入HttpServletResponse返回响应
	 * @param request 
	 * @param response
	 */
	public static void responseResult(HttpServletResponse response,
			String responseResult) {
		try {
			PrintWriter out = response.getWriter();
			out.write(responseResult);
		} catch (IOException e) {
		    LOGGER.error("response the result exception - :", e);
		}
	}
	
	/**
	 * 以Post方式请求，返回数据封装
	 * @param url
	 * @param param
	 * @param character
	 * @return
	 */
	public static HttpResponseWrapper doPost(String url, String param, String character){
		StringBuffer responseMessage = null;
		HttpURLConnection connection = null;
		URL _url;
		OutputStreamWriter out = null;
		InputStream in = null;
		BufferedReader br = null;
		try {
			_url = new URL(url);
			connection = (HttpURLConnection)_url.openConnection();
			connection.setDoInput(true);   
			connection.setDoOutput(true);   
			connection.setRequestMethod("POST");   
			connection.setUseCaches(false);   
			connection.setInstanceFollowRedirects(false);   
//			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			connection.setRequestProperty("Content-Type", "text/xml"); 
			connection.connect();
			
			out = new OutputStreamWriter(connection.getOutputStream(), character);
			out.write(param);
			out.flush();
			
			int httpStatus = connection.getResponseCode();
			int charCount = -1;
			in = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(in, character));
			while ((charCount = br.read()) != -1) {
				if(responseMessage == null) responseMessage = new StringBuffer();
				responseMessage.append((char) charCount);
			}
			HttpResponseWrapper wrapper = new HttpResponseWrapper(httpStatus, responseMessage.toString());
			return wrapper;
		} catch (Exception ex) {
			// TODO: handle exception
			throw new RuntimeException(ex.getMessage());
		}finally{
			try {
				if(out != null) out.close();
				if(in != null) in.close();
				if(connection != null) connection.disconnect();
			} catch (IOException e) {
				// TODO: handle exception
				LOGGER.error("close connection exception - :", e);
			}
		}
	}
	
	/**
	 * 调用http请求(需要添加额外的头信息，头信息放在Map中)
	 * @param url
	 * @param param
	 * @param heads
	 * @param character
	 * @return
	 */
	public static HttpResponseWrapper doPostWithExtraHeads(String url, String param, Map<String, String> heads, String character) {
	    StringBuffer responseMessage = null;
        HttpURLConnection connection = null;
        URL _url;
        OutputStreamWriter out = null;
        InputStream in = null;
        BufferedReader br = null;
        try {
            _url = new URL(url);
            connection = (HttpURLConnection)_url.openConnection();
            connection.setDoInput(true);   
            connection.setDoOutput(true);   
            connection.setRequestMethod("POST");   
            connection.setUseCaches(false);   
            connection.setInstanceFollowRedirects(false);   
          connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
            Iterator it = heads.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
                connection.setRequestProperty(entry.getKey(), entry.getValue()); 
            }
            connection.setRequestProperty("Content-Type", "text/xml"); 
            connection.connect();
            
            out = new OutputStreamWriter(connection.getOutputStream(), character);
            out.write(param);
            out.flush();
            
            int httpStatus = connection.getResponseCode();
            int charCount = -1;
            in = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(in, character));
            while ((charCount = br.read()) != -1) {
                if(responseMessage == null) responseMessage = new StringBuffer();
                responseMessage.append((char) charCount);
            }
            HttpResponseWrapper wrapper = new HttpResponseWrapper(httpStatus, responseMessage.toString());
            return wrapper;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }finally{
            try {
                if(out != null) out.close();
                if(in != null) in.close();
                if(connection != null) connection.disconnect();
            } catch (IOException e) {
                LOGGER.error("close connection exception - :", e);
            }
        }
    }

	/**
	 * InputStream 转成string
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String inputStreamToString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	
    /**
     * post以流方式接收
     */
    public static String asString(InputStream input, String charsetName) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        convertInputStream(input, output);
        return output.toString(charsetName);
    }
    
    public static int convertInputStream(InputStream in, OutputStream out) throws IOException {
        try {
            int byteCount = 0;
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
                LOGGER.debug("file:bytesRead=" + bytesRead + ", byteCount=" + byteCount);
            }
            out.flush();
            return byteCount;
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                LOGGER.warn("Could not close InputStream", ex);
            }
            try {
                out.close();
            } catch (IOException ex) {
                LOGGER.warn("Could not close OutputStream", ex);
            }
        }
    }
	
	public static String get(String url){
		StringBuffer sb = new StringBuffer();
		try {
			URL urlClient = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) urlClient.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
            System.out.println("**********************************************" + httpConnection.getHeaderField("statuscode"));
            int charCount = -1;
			while ((charCount = br.read()) != -1) {
				sb.append((char) charCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.warn("请求："+url+"异常：", e);
		} 
		return new String(sb);
	}
	
	/**
	 * HttpResponse包装类
	 * 
	 * @author XuSemon
	 */
	public static class HttpResponseWrapper{
		private int httpStatus;
		private String response;
		public HttpResponseWrapper(int httpStatus, String response){
			this.httpStatus = httpStatus;
			this.response = response;
		}
		public int getHttpStatus() {
			return httpStatus;
		}
		public void setHttpStatus(int httpStatus) {
			this.httpStatus = httpStatus;
		}
		public String getResponse() {
			return response;
		}
		public void setResponse(String response) {
			this.response = response;
		}
	}

    /*public static String[] getHDCAuthentication(String url) throws IOException {
        StringBuffer sb = new StringBuffer();
        URL urlClient = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) urlClient.openConnection();
        httpConnection.setRequestMethod("GET");
        httpConnection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
        String statusCode = httpConnection.getHeaderField("statuscode");
        int charCount = -1;
        while ((charCount = br.read()) != -1) {
            sb.append((char) charCount);
        }
        String[] arr = new String[2];
        arr[0] = statusCode;
        arr[1] = sb.toString();
        return arr;
    }*/

}
