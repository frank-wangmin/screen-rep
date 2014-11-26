package com.ysten.local.bss.util;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

/**
 * 2013.05.27
 * @author qinxuefei
 * 
 */
public class FtpUtils {

    private static Logger LOGGER = Logger.getLogger(FtpUtils.class);

    private FTPClient ftp = null;

    /**
     * 构造方法 new此类 建立FTP连接 操作完成后需要调用disConnect方法断开连接
     * 
     * @param server
     *            FTP服务器地址
     * @param port
     *            端口号
     * @param username
     *            用户
     * @param passwd
     *            密码
     */
    public FtpUtils(String server, int port, String username, String passwd) throws Exception {
        ftp = new FTPClient();
        try {
            ftp.connect(server, port);
            ftp.login(username, passwd);
        } catch (Exception e) {
            LOGGER.error("[FTP工具类]建立FTP连接失败");
//            LOGGER.error("[FTP工具类]建立FTP连接失败", e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 断开FTP连接
     * 
     * @return
     */
    public boolean disConnect() {
        if (ftp != null) {
            try {
                ftp.disconnect();
                return true;
            } catch (IOException e) {
                LOGGER.error("【FTP工具类】断开FTP连接异常", e);
                return false;
            }
        }
        return true;
    }

    /**
     * 删除文件
     * 
     * @param fileName
     *            要删除的服务端文件 如：000/000.txt
     * @return
     */
    public boolean deleteFile(String fileName) {
        try {
            return ftp.deleteFile(fileName);
        } catch (IOException e) {
            LOGGER.error("[FTP工具类]删除服务端文件异常", e);
            return false;
        }
    }

    /**
     * 获得FTP服务器目录下得文件列表
     * 
     * @param path
     *            FTP服务器目录 如：000 根目录直接 "/"
     * @return
     */
    public List<String> getFileList(String path) {
        List<String> list = new ArrayList<String>();
        ftp.setControlEncoding("GBK");// 解决中文乱码
        FTPFile[] flist = null;
        try {
            flist = ftp.listFiles(path);
            for (int i = 0; i < flist.length; i++) {
                if (!flist[i].isDirectory()) {
                    list.add(flist[i].getName());
                }
            }
        } catch (IOException e) {
            LOGGER.error("[FTP工具类]获取文件列表异常", e);

        }
        return list;
    }

    /**
     * 上传一个本地文件到FTP服务器
     * 
     * @param remoteDir
     *            要上传到服务器的目录 如：000/
     * @param remoteFileName
     *            上传到服务器的文件名 如：000.txt
     * @param localFile
     *            本地文件 如:c:/000.txt
     * @return
     */
    public boolean upload(String remoteDir, String remoteFileName, File localFile) {
        InputStream stream = null;
        try {
            stream = new FileInputStream(localFile);
            // 设置本地模式
            LOGGER.info("ready to upload, now directory : " + ftp.printWorkingDirectory());
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.changeWorkingDirectory(remoteDir);
            LOGGER.info("changed directory : " + ftp.printWorkingDirectory());
            LOGGER.info("file name : " + localFile.getName() + " is going to be uploaded as : " + remoteFileName);
            return ftp.storeFile(remoteDir + remoteFileName, stream);
        } catch (Exception e) {
            LOGGER.error("【FTP工具类】上传文件异常", e);
            return false;
        } finally {
            try {
                if (stream != null){
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从FTP服务器下载一个文件到本地目录，文件名一致
     * 
     * @param remoteDir
     *            服务端目录 如：000/
     * @param remoteFileName
     *            要下载的文件名
     * @param localDir
     *            本地目录 如：c:/000/
     * @return
     */
    public boolean download(String remoteDir, String remoteFileName, String localDir) {

        File file = new File(localDir + remoteFileName);
        OutputStream out = null;
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.retrieveFile(remoteDir + remoteFileName, out);
            return true;
        } catch (Exception e) {
            LOGGER.error("[FTP工具类]下载文件异常", e);
            return false;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 从FTP服务器获取一个文件
     * @param 
     * 		remoteUrl 远程url：ftp://ftpuser:FonsView!35&(@223.87.4.69/ystenmv/reply/2014050716_318878-success.xml
     * @return
     * 		File 返回文件类
     * @throws java.net.URISyntaxException
     */
    public File download(String remoteUrl) throws URISyntaxException {
    	URI uri = new URI(remoteUrl);
        File file = new File(uri);
        OutputStream out = null;
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.retrieveFile(remoteUrl, out);
            return file;
        } catch (Exception e) {
            LOGGER.error("[FTP工具类]下载文件异常", e);
            return null;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 将xml写入文件
     * @param xml
     * @param fileName
     */
     public static void writeXmlToFile(String xml,String fileName) {
         try{
             File f = new File(fileName);
             if (f.exists()){
                 f.delete();
             }
             f.createNewFile();//创建新文件
             BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"UTF-8"));
             output.write(xml);
             output.close();
         }catch(Exception e){
             LOGGER.error("write xml to file exception - :", e);
         }
     }
     
     public boolean downloadTo(String remoteDir, String remoteFileName, String localDir){
    	 String[] files = localDir.split("\\"+File.separator);
    	 OutputStream out = null;
         try {
        	 if(files.length > 0){
            	 StringBuffer preFile = new StringBuffer(files[0]); 
            	 for(int i=1; i<files.length; i++){
            		 preFile.append(File.separator).append(files[i]);
            		 File tFile = new File(preFile+"");
            		 if(!tFile.exists()){
            			 tFile.mkdir();
            		 }
            	 } 
        	 }
        	 File file = new File(localDir + File.separator +remoteFileName);
             if (!file.exists()){
                 file.createNewFile();
             }
             out = new FileOutputStream(file);
             ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
             ftp.retrieveFile(remoteDir + remoteFileName, out);
             return true;
         } catch (Exception e) {
             LOGGER.error("[FTP工具类]下载文件异常...");
             return false;
         } finally {
         }
     } 
}