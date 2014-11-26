package com.ysten.local.bss.device.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.ysten.local.bss.device.domain.PanelImgBox;


public interface PanelImgBoxMapper {
    /**
     * 根据条件查找图片集
     * @param title
     * @param progromId
     * @param pageNo
     * @param pageSize
     * @return
     */
	  List<PanelImgBox> findAllByCondition(@Param("title") String title,@Param("progromId") Long progromId,@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

	  int findCountByCondition(@Param("title") String title,@Param("progromId") Long progromId);
	  
	  PanelImgBox getById(@Param("id") Long id);
	  
	  int save(PanelImgBox panelImgBox);
	  
	  int update(PanelImgBox panelImgBox);
	  
	  int delete(@Param("ids")List<Long> ids);
	  
	  List<PanelImgBox> getByImgBoxId(@Param("imgBoxId") Long imgBoxId);
	  
	  List<PanelImgBox> findAll();
}