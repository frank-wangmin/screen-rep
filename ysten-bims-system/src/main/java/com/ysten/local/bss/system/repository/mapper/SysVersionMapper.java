package com.ysten.local.bss.system.repository.mapper;

import com.ysten.local.bss.system.domain.SysVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysVersionMapper extends BaseMapper<SysVersion, Long> {


    List<SysVersion> findSysVersions(@Param("versionId") String versionId,
                                 @Param("versionName") String versionName, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    int getCountByName(@Param("versionId") String versionId, @Param("versionName") String versionName);

}