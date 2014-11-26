package com.ysten.local.bss.device.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ysten.local.bss.device.domain.PanelImgBox;
import com.ysten.local.bss.device.domain.PanelNavBox;
import com.ysten.local.bss.device.domain.PanelTextBox;
import com.ysten.local.bss.device.repository.IXPanelRepository;
import com.ysten.local.bss.device.repository.mapper.PanelImgBoxMapper;
import com.ysten.local.bss.device.repository.mapper.PanelNavBoxMapper;
import com.ysten.local.bss.device.repository.mapper.PanelTextBoxMapper;
import com.ysten.utils.page.Pageable;

/**
 * 
 * @author
 *
 */
@Repository
public class XPanelRepositoryImpl implements IXPanelRepository{

	@Autowired
	private PanelTextBoxMapper panelTextBoxMapper;
	@Autowired
	private PanelNavBoxMapper panelNavBoxMapper;
	@Autowired
	private PanelImgBoxMapper panelImgBoxMapper;
	@Override
	public Pageable<PanelTextBox> findAllTextBoxByCondition(String title,Long progromId, int isNew, Integer pageNo, Integer pageSize) {
		List<PanelTextBox> panelTextBoxList = this.panelTextBoxMapper.findAllByCondition(title, progromId, isNew, pageNo, pageSize);
		int total = this.panelTextBoxMapper.findCountByCondition(title, progromId, isNew);
		return new Pageable<PanelTextBox>().instanceByPageNo(panelTextBoxList,total,pageNo,pageSize);
	}

	@Override
	public Pageable<PanelImgBox> findAllImgBoxByCondition(String title,Long progromId, Integer pageNo, Integer pageSize) {
		List<PanelImgBox> panelImgBoxList = this.panelImgBoxMapper.findAllByCondition(title, progromId, pageNo, pageSize);
		int total = this.panelImgBoxMapper.findCountByCondition(title, progromId);
		return new Pageable<PanelImgBox>().instanceByPageNo(panelImgBoxList, total, pageNo, pageSize);
	}

	@Override
	public Pageable<PanelNavBox> findAllNavBoxByTitle(String title, Integer pageNo,Integer pageSize) {
		List<PanelNavBox> panelNavBoxList = this.panelNavBoxMapper.findAllByTitle(title, pageNo, pageSize);
		int total = this.panelNavBoxMapper.findCountByTitle(title);
		return new Pageable<PanelNavBox>().instanceByPageNo(panelNavBoxList, total, pageNo, pageSize);
	}

	@Override
	public PanelTextBox getTextBoxById(Long id) {		
		return this.panelTextBoxMapper.getById(id);
	}

	@Override
	public boolean saveTextBox(PanelTextBox panelTextBox) {
		return 1 == this.panelTextBoxMapper.save(panelTextBox);
	}

	@Override
	public boolean updateTextBox(PanelTextBox panelTextBox) {
		return 1 == this.panelTextBoxMapper.update(panelTextBox);
	}

	@Override
	public boolean deleteTextBox(List<Long> ids) {		
		return ids.size() == this.panelTextBoxMapper.delete(ids);
	}

	@Override
	public PanelImgBox getImgBoxById(Long id) {
		return this.panelImgBoxMapper.getById(id);
	}

	@Override
	public boolean saveImgBox(PanelImgBox panelImgBox) {
		return 1 == this.panelImgBoxMapper.save(panelImgBox);
	}

	@Override
	public boolean updateImgBox(PanelImgBox panelImgBox) {
		return 1 == this.panelImgBoxMapper.update(panelImgBox);
	}

	@Override
	public boolean deleteImgBox(List<Long> ids) {
		return ids.size() == this.panelImgBoxMapper.delete(ids);
	}

	@Override
	public PanelNavBox getNavBoxById(Long id) {
		return this.panelNavBoxMapper.getById(id);
	}

	@Override
	public boolean saveNavBox(PanelNavBox panelNavBox) {
		return 1 == this.panelNavBoxMapper.save(panelNavBox);
	}

	@Override
	public boolean updateNavBox(PanelNavBox panelNavBox) {
		return 1 == this.panelNavBoxMapper.update(panelNavBox);
	}

	@Override
	public boolean deleteNavBox(List<Long> ids) {
		return ids.size() == this.panelNavBoxMapper.delete(ids);
	}

	@Override
	public List<PanelImgBox> getByImgBoxId(Long imgBoxId) {
		return this.panelImgBoxMapper.getByImgBoxId(imgBoxId);
	}

	@Override
	public List<PanelTextBox> findAllTextBox() {
		return this.panelTextBoxMapper.findAll();
	}

	@Override
	public List<PanelNavBox> findAllNavBox() {
		return this.panelNavBoxMapper.findAll();
	}

	@Override
	public List<PanelImgBox> findAllImgBox() {
		return this.panelImgBoxMapper.findAll();
	}

}
