<?xml version="1.0" encoding="UTF-8"?><#t>
<Service id="getUpgradeInfor"><#t>
		<#if epgDeviceSoftware?exists><#t>
			<state>upgrade</state><#t>
			<upgrades><#t>
				<upgrade platformId="<#if epgDeviceSoftware.epgSoftwareCode?exists>${epgDeviceSoftware.epgSoftwareCode.code?default('')}</#if>"
				versionId="<#if epgDeviceSoftware?exists>${epgDeviceSoftware.versionSeq?default('')}</#if>"
				versionName="<#if epgDeviceSoftware?exists>${epgDeviceSoftware.versionName?default('')}</#if>"
				packageType="<#if epgDeviceSoftware?exists>${epgDeviceSoftware.packageType?default('')}</#if>"
				packageLocation="<#if epgDeviceSoftware?exists>${epgDeviceSoftware.packageLocation?default('')}</#if>"
			    packageStatus="<#if epgDeviceSoftware?exists>${epgDeviceSoftware.packageStatus?default('')}</#if>"
			    isForce ="<#if epgDeviceSoftware.mandatoryStatus =='MANDATORY'>true<#else>false</#if>"
			    md5="<#if epgDeviceSoftware?exists>${epgDeviceSoftware.md5?default('')}</#if>"/><#t>
		   </upgrades><#t>
					
		<#else><#t>
			<state>notupgrade</state><#t>
		</#if><#t>
		
</Service><#t>