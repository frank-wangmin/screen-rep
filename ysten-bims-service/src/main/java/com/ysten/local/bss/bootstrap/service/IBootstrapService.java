package com.ysten.local.bss.bootstrap.service;

import com.ysten.local.bss.device.exception.ServiceInfoNotFoundException;

import java.text.ParseException;

public interface IBootstrapService {

    String getBootstrapByYstenId(String ystenId,String deviceId, String mac, String isReturnYstenId) throws ServiceInfoNotFoundException, Exception;

    String getDefaultBootstrap(String ystenId, String deviceId, String mac) throws ServiceInfoNotFoundException, Exception;

    String saveInputDevice(String mac, String deviceId, String ystenId, String isReturnYstenId) throws ParseException,Exception;

    String saveInputDeviceInCenter(String mac, String deviceId, String ystenId, String isReturnYstenId) throws Exception;

    String updateDeviceInCenter(String mac, String deviceId, String ystenId) throws Exception;

    String updateInputDevice(String mac, String deviceId, String ystenId) throws Exception;
}