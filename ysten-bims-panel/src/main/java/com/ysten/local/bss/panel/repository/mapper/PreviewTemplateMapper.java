package com.ysten.local.bss.panel.repository.mapper;

import com.ysten.local.bss.panel.domain.PreviewTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by frank on 14-5-16.
 */
public interface PreviewTemplateMapper {

    PreviewTemplate getTargetById(Long id);

    Integer insert(PreviewTemplate previewTemplate);

    Integer deleteByIds(@Param("ids") List<Long> ids);

    Integer deleteById(Long id);

    Integer update(PreviewTemplate previewTemplate);

    List<PreviewTemplate> getTargetList(@Param("Id") Long Id,@Param("name") String name, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    Integer getTargetCount(@Param("Id") Long Id,@Param("name") String name);

    List<PreviewTemplate> findAllCustomedTargetList();

    List<PreviewTemplate> findAllOuterList();

    List<PreviewTemplate> findAllEpgList();

    List<PreviewTemplate> findIdAndEpgIdList();


    /**
     * Get the template by epgTemplateId
     * @param epgTemplateId
     * @return previewTemplate
     */
    PreviewTemplate getTemplateByEpgTempId(@Param("epgTemplateId") Long epgTemplateId);

    /**
     *  delete all the epg data
     * @return
     */
    int deleteByEpgIds();

    Integer batchSavePreviewTemplate(List<PreviewTemplate> list);

    Integer batchUpdate(List<PreviewTemplate> list);

}
