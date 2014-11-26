///**
// * External2OssServiceLocator.java
// *
// * This file was auto-generated from WSDL
// * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
// */
//
//package com.huashu.boss.external.ws;
//
//public class External2OssServiceLocator extends org.apache.axis.client.Service implements com.huashu.boss.external.ws.External2OssService {
//
//    public External2OssServiceLocator() {
//    }
//
//
//    public External2OssServiceLocator(org.apache.axis.EngineConfiguration config) {
//        super(config);
//    }
//
//    public External2OssServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
//        super(wsdlLoc, sName);
//    }
//
//    // Use to get a proxy class for External2Oss
//    private java.lang.String External2Oss_address = "http://172.16.5.11:8001/boss_ei/services/External2Oss";
//
//    public java.lang.String getExternal2OssAddress() {
//        return External2Oss_address;
//    }
//
//    // The WSDD service name defaults to the port name.
//    private java.lang.String External2OssWSDDServiceName = "External2Oss";
//
//    public java.lang.String getExternal2OssWSDDServiceName() {
//        return External2OssWSDDServiceName;
//    }
//
//    public void setExternal2OssWSDDServiceName(java.lang.String name) {
//        External2OssWSDDServiceName = name;
//    }
//
//    public com.huashu.boss.external.ws.External2Oss getExternal2Oss() throws javax.xml.rpc.ServiceException {
//       java.net.URL endpoint;
//        try {
//            endpoint = new java.net.URL(External2Oss_address);
//        }
//        catch (java.net.MalformedURLException e) {
//            throw new javax.xml.rpc.ServiceException(e);
//        }
//        return getExternal2Oss(endpoint);
//    }
//
//    public com.huashu.boss.external.ws.External2Oss getExternal2Oss(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
//        try {
//            com.huashu.boss.external.ws.External2OssSoapBindingStub _stub = new com.huashu.boss.external.ws.External2OssSoapBindingStub(portAddress, this);
//            _stub.setPortName(getExternal2OssWSDDServiceName());
//            //设置webservice 访问超时时间2秒
//            _stub.setTimeout(1000*2);
//            return _stub;
//        }
//        catch (org.apache.axis.AxisFault e) {
//            return null;
//        }
//    }
//
//    public void setExternal2OssEndpointAddress(java.lang.String address) {
//        External2Oss_address = address;
//    }
//
//    /**
//     * For the given interface, get the stub implementation.
//     * If this service has no port for the given interface,
//     * then ServiceException is thrown.
//     */
//    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
//        try {
//            if (com.huashu.boss.external.ws.External2Oss.class.isAssignableFrom(serviceEndpointInterface)) {
//                com.huashu.boss.external.ws.External2OssSoapBindingStub _stub = new com.huashu.boss.external.ws.External2OssSoapBindingStub(new java.net.URL(External2Oss_address), this);
//                _stub.setPortName(getExternal2OssWSDDServiceName());
//                return _stub;
//            }
//        }
//        catch (java.lang.Throwable t) {
//            throw new javax.xml.rpc.ServiceException(t);
//        }
//        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
//    }
//
//    /**
//     * For the given interface, get the stub implementation.
//     * If this service has no port for the given interface,
//     * then ServiceException is thrown.
//     */
//    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
//        if (portName == null) {
//            return getPort(serviceEndpointInterface);
//        }
//        java.lang.String inputPortName = portName.getLocalPart();
//        if ("External2Oss".equals(inputPortName)) {
//            return getExternal2Oss();
//        }
//        else  {
//            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
//            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
//            return _stub;
//        }
//    }
//
//    public javax.xml.namespace.QName getServiceName() {
//        return new javax.xml.namespace.QName("http://ws.external.boss.huashu.com", "External2OssService");
//    }
//
//    private java.util.HashSet ports = null;
//
//    public java.util.Iterator getPorts() {
//        if (ports == null) {
//            ports = new java.util.HashSet();
//            ports.add(new javax.xml.namespace.QName("http://ws.external.boss.huashu.com", "External2Oss"));
//        }
//        return ports.iterator();
//    }
//
//    /**
//    * Set the endpoint address for the specified port name.
//    */
//    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
//        
//if ("External2Oss".equals(portName)) {
//            setExternal2OssEndpointAddress(address);
//        }
//        else 
//{ // Unknown Port Name
//            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
//        }
//    }
//
//    /**
//    * Set the endpoint address for the specified port name.
//    */
//    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
//        setEndpointAddress(portName.getLocalPart(), address);
//    }
//
//}
