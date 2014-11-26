///**
// * WsRequestParam.java
// *
// * This file was auto-generated from WSDL
// * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
// */
//
//package com.huashu.boss.external.ws.data;
//
//public class WsRequestParam  implements java.io.Serializable {
//    private java.lang.String encryptInfo;
//
//    private java.lang.String extendInfo;
//
//    private java.lang.String requestContent;
//
//    private int requestNo;
//
//    private int requestSystemNo;
//
//    private int versionNo;
//
//    public WsRequestParam() {
//    }
//
//    public WsRequestParam(
//           java.lang.String encryptInfo,
//           java.lang.String extendInfo,
//           java.lang.String requestContent,
//           int requestNo,
//           int requestSystemNo,
//           int versionNo) {
//           this.encryptInfo = encryptInfo;
//           this.extendInfo = extendInfo;
//           this.requestContent = requestContent;
//           this.requestNo = requestNo;
//           this.requestSystemNo = requestSystemNo;
//           this.versionNo = versionNo;
//    }
//
//
//    /**
//     * Gets the encryptInfo value for this WsRequestParam.
//     * 
//     * @return encryptInfo
//     */
//    public java.lang.String getEncryptInfo() {
//        return encryptInfo;
//    }
//
//
//    /**
//     * Sets the encryptInfo value for this WsRequestParam.
//     * 
//     * @param encryptInfo
//     */
//    public void setEncryptInfo(java.lang.String encryptInfo) {
//        this.encryptInfo = encryptInfo;
//    }
//
//
//    /**
//     * Gets the extendInfo value for this WsRequestParam.
//     * 
//     * @return extendInfo
//     */
//    public java.lang.String getExtendInfo() {
//        return extendInfo;
//    }
//
//
//    /**
//     * Sets the extendInfo value for this WsRequestParam.
//     * 
//     * @param extendInfo
//     */
//    public void setExtendInfo(java.lang.String extendInfo) {
//        this.extendInfo = extendInfo;
//    }
//
//
//    /**
//     * Gets the requestContent value for this WsRequestParam.
//     * 
//     * @return requestContent
//     */
//    public java.lang.String getRequestContent() {
//        return requestContent;
//    }
//
//
//    /**
//     * Sets the requestContent value for this WsRequestParam.
//     * 
//     * @param requestContent
//     */
//    public void setRequestContent(java.lang.String requestContent) {
//        this.requestContent = requestContent;
//    }
//
//
//    /**
//     * Gets the requestNo value for this WsRequestParam.
//     * 
//     * @return requestNo
//     */
//    public int getRequestNo() {
//        return requestNo;
//    }
//
//
//    /**
//     * Sets the requestNo value for this WsRequestParam.
//     * 
//     * @param requestNo
//     */
//    public void setRequestNo(int requestNo) {
//        this.requestNo = requestNo;
//    }
//
//
//    /**
//     * Gets the requestSystemNo value for this WsRequestParam.
//     * 
//     * @return requestSystemNo
//     */
//    public int getRequestSystemNo() {
//        return requestSystemNo;
//    }
//
//
//    /**
//     * Sets the requestSystemNo value for this WsRequestParam.
//     * 
//     * @param requestSystemNo
//     */
//    public void setRequestSystemNo(int requestSystemNo) {
//        this.requestSystemNo = requestSystemNo;
//    }
//
//
//    /**
//     * Gets the versionNo value for this WsRequestParam.
//     * 
//     * @return versionNo
//     */
//    public int getVersionNo() {
//        return versionNo;
//    }
//
//
//    /**
//     * Sets the versionNo value for this WsRequestParam.
//     * 
//     * @param versionNo
//     */
//    public void setVersionNo(int versionNo) {
//        this.versionNo = versionNo;
//    }
//
//    private java.lang.Object __equalsCalc = null;
//    public synchronized boolean equals(java.lang.Object obj) {
//        if (!(obj instanceof WsRequestParam)) return false;
//        WsRequestParam other = (WsRequestParam) obj;
//        if (obj == null) return false;
//        if (this == obj) return true;
//        if (__equalsCalc != null) {
//            return (__equalsCalc == obj);
//        }
//        __equalsCalc = obj;
//        boolean _equals;
//        _equals = true && 
//            ((this.encryptInfo==null && other.getEncryptInfo()==null) || 
//             (this.encryptInfo!=null &&
//              this.encryptInfo.equals(other.getEncryptInfo()))) &&
//            ((this.extendInfo==null && other.getExtendInfo()==null) || 
//             (this.extendInfo!=null &&
//              this.extendInfo.equals(other.getExtendInfo()))) &&
//            ((this.requestContent==null && other.getRequestContent()==null) || 
//             (this.requestContent!=null &&
//              this.requestContent.equals(other.getRequestContent()))) &&
//            this.requestNo == other.getRequestNo() &&
//            this.requestSystemNo == other.getRequestSystemNo() &&
//            this.versionNo == other.getVersionNo();
//        __equalsCalc = null;
//        return _equals;
//    }
//
//    private boolean __hashCodeCalc = false;
//    public synchronized int hashCode() {
//        if (__hashCodeCalc) {
//            return 0;
//        }
//        __hashCodeCalc = true;
//        int _hashCode = 1;
//        if (getEncryptInfo() != null) {
//            _hashCode += getEncryptInfo().hashCode();
//        }
//        if (getExtendInfo() != null) {
//            _hashCode += getExtendInfo().hashCode();
//        }
//        if (getRequestContent() != null) {
//            _hashCode += getRequestContent().hashCode();
//        }
//        _hashCode += getRequestNo();
//        _hashCode += getRequestSystemNo();
//        _hashCode += getVersionNo();
//        __hashCodeCalc = false;
//        return _hashCode;
//    }
//
//    // Type metadata
//    private static org.apache.axis.description.TypeDesc typeDesc =
//        new org.apache.axis.description.TypeDesc(WsRequestParam.class, true);
//
//    static {
//        typeDesc.setXmlType(new javax.xml.namespace.QName("http://data.ws.external.boss.huashu.com", "WsRequestParam"));
//        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("encryptInfo");
//        elemField.setXmlName(new javax.xml.namespace.QName("", "encryptInfo"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
//        elemField.setNillable(true);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("extendInfo");
//        elemField.setXmlName(new javax.xml.namespace.QName("", "extendInfo"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
//        elemField.setNillable(true);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("requestContent");
//        elemField.setXmlName(new javax.xml.namespace.QName("", "requestContent"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
//        elemField.setNillable(true);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("requestNo");
//        elemField.setXmlName(new javax.xml.namespace.QName("", "requestNo"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("requestSystemNo");
//        elemField.setXmlName(new javax.xml.namespace.QName("", "requestSystemNo"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//        elemField = new org.apache.axis.description.ElementDesc();
//        elemField.setFieldName("versionNo");
//        elemField.setXmlName(new javax.xml.namespace.QName("", "versionNo"));
//        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
//        elemField.setNillable(false);
//        typeDesc.addFieldDesc(elemField);
//    }
//
//    /**
//     * Return type metadata object
//     */
//    public static org.apache.axis.description.TypeDesc getTypeDesc() {
//        return typeDesc;
//    }
//
//    /**
//     * Get Custom Serializer
//     */
//    public static org.apache.axis.encoding.Serializer getSerializer(
//           java.lang.String mechType, 
//           java.lang.Class _javaType,  
//           javax.xml.namespace.QName _xmlType) {
//        return 
//          new  org.apache.axis.encoding.ser.BeanSerializer(
//            _javaType, _xmlType, typeDesc);
//    }
//
//    /**
//     * Get Custom Deserializer
//     */
//    public static org.apache.axis.encoding.Deserializer getDeserializer(
//           java.lang.String mechType, 
//           java.lang.Class _javaType,  
//           javax.xml.namespace.QName _xmlType) {
//        return 
//          new  org.apache.axis.encoding.ser.BeanDeserializer(
//            _javaType, _xmlType, typeDesc);
//    }
//
//}
