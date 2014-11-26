//package com.huashu.boss.external.ws;
//
//public class External2OssProxy implements com.huashu.boss.external.ws.External2Oss {
//  private String _endpoint = null;
//  private com.huashu.boss.external.ws.External2Oss external2Oss = null;
//  
//  public External2OssProxy() {
//    _initExternal2OssProxy();
//  }
//  
//  public External2OssProxy(String endpoint) {
//    _endpoint = endpoint;
//    _initExternal2OssProxy();
//  }
//  
//  private void _initExternal2OssProxy() {
//    try {
//      external2Oss = (new com.huashu.boss.external.ws.External2OssServiceLocator()).getExternal2Oss();
//      if (external2Oss != null) {
//        if (_endpoint != null)
//          ((javax.xml.rpc.Stub)external2Oss)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
//        else
//          _endpoint = (String)((javax.xml.rpc.Stub)external2Oss)._getProperty("javax.xml.rpc.service.endpoint.address");
//      }
//      
//    }
//    catch (javax.xml.rpc.ServiceException serviceException) {}
//  }
//  
//  public String getEndpoint() {
//    return _endpoint;
//  }
//  
//  public void setEndpoint(String endpoint) {
//    _endpoint = endpoint;
//    if (external2Oss != null)
//      ((javax.xml.rpc.Stub)external2Oss)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
//    
//  }
//  
//  public com.huashu.boss.external.ws.External2Oss getExternal2Oss() {
//    if (external2Oss == null)
//      _initExternal2OssProxy();
//    return external2Oss;
//  }
//  
//  public java.lang.String ossRequest(com.huashu.boss.external.ws.data.WsRequestParam requestParam) throws java.rmi.RemoteException{
//    if (external2Oss == null)
//      _initExternal2OssProxy();
//    return external2Oss.ossRequest(requestParam);
//  }
//  
//  
//}