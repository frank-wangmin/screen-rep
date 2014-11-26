package com.ysten.local.bss.facade.utils;

import com.ysten.local.bss.device.domain.AppSoftwarePackage;
import com.ysten.local.bss.device.utils.EnumConstantsInterface;


public class ProfileUtils {
	
	
    /**
     * 获取应用升级xml
     */
    public static String getResultXml(AppSoftwarePackage appUpgrade,String appSoftwareCode,String str){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<Service id=\"getAppUpgradeInfo\">");
        if(appUpgrade != null){
            str = "upgrade";
            sb.append("<state>").append(str).append("</state>");
            sb.append("<upgrades>");
            sb.append("<upgrade platformId=\"").append(appSoftwareCode);
            sb.append("\" versionId=\"").append(appUpgrade.getVersionSeq());
            sb.append("\" versionName=\"").append(appUpgrade.getVersionName());
            sb.append("\" packageType=\"").append(appUpgrade.getPackageType());
            sb.append("\" packageLocation=\"").append(appUpgrade.getPackageLocation());
            sb.append("\" packageStatus=\"").append(appUpgrade.getPackageStatus());
            sb.append("\" upgradeState=\"").append("");
            sb.append("\" isForce=\"").append(appUpgrade.getMandatoryStatus()==EnumConstantsInterface.MandatoryStatus.MANDATORY?"true":"false");
            sb.append("\" md5=\"").append(appUpgrade.getMd5()).append("\" />");
            sb.append("</upgrades>");
        }else{
            sb.append("<state>").append("notupgrade").append("</state>");
            sb.append("<upgrades>");
            sb.append("</upgrades>");
        }
        sb.append("</Service>"); 
        return sb.toString();
    }
}
