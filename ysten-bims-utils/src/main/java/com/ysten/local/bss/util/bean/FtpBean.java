package com.ysten.local.bss.util.bean;

public class FtpBean {
    private String userName;
    private String password;
    private String service;
    private String port;
    
//    public FtpBean(String userName,String password,String service,String port){
//        this.userName = userName;
//        this.password = password;
//        this.service = service;
//        this.port = port;
//    }
    
    /**
     * 
     * @param ftpServer like ftp://test:123456@192.168.1.1:22
     */
    public FtpBean(String ftpServer){
        ftpServer = ftpServer.trim();
        int split = ftpServer.lastIndexOf("@");
        String user = ftpServer.substring(6, split);
        String[] server = ftpServer.substring(split+1).split(":");
        split = user.indexOf(":");
        this.userName = user.substring(0, split);
        this.password = user.substring(split+1);
        this.service = server[0];
        this.port = server[1];
    }
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }
    
}
