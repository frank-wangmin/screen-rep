package com.ysten.local.bss.panel.service.impl;

import com.ysten.boss.systemconfig.repository.ISystemConfigRepository;
import com.ysten.local.bss.panel.domain.PanelItem;
import com.ysten.local.bss.panel.enums.PanelItemContentType;
import com.ysten.local.bss.panel.enums.YesOrNo;
import com.ysten.local.bss.panel.repository.IPanelItemRepository;
import com.ysten.local.bss.panel.repository.IPanelPanelItemMapRepository;
import com.ysten.local.bss.panel.service.IPanelItemService;
import com.ysten.local.bss.panel.vo.PanelQueryCriteria;
import com.ysten.local.bss.util.DateUtils;
import com.ysten.local.bss.util.bean.Constant;
import com.ysten.utils.page.Pageable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by frank on 14-5-19.
 */
@Service
public class PanelItemServiceImpl implements IPanelItemService {

    @Autowired
    private IPanelItemRepository panelItemRepository;

    @Autowired
    IPanelPanelItemMapRepository panelPanelItemMapRepository;
    @Autowired
    private ISystemConfigRepository systemConfigRepository;

    @Override
    public Pageable<PanelItem> getPanelItemListByPanelId(Long panelId, Integer pageNo, Integer pageSize) {
        return panelItemRepository.findPanelItemListByPanelId(panelId, pageNo, pageSize);
    }

    private String getCurrentDistrictCode() {
        return systemConfigRepository.getSystemConfigByConfigKey("deployDistrictCode").getConfigValue();
    }

    @Override
    public Pageable<PanelItem> findgetPanelItemList(PanelQueryCriteria panelQueryCriteria) {
        Pageable<PanelItem> pageable = panelItemRepository.findPanelItemList(panelQueryCriteria);
        List<PanelItem> panelItemList = pageable.getRows();
        if (CollectionUtils.isEmpty(panelItemList)) return pageable;
        for (PanelItem panelItem : panelItemList) {

            //关联出父面板项
            if (panelItem.getPanelItemParentId() != null) {
                PanelItem parentItem = panelItemRepository.getPanelItemById(panelItem.getPanelItemParentId());
                if (parentItem != null) panelItem.setParentItemTitle(parentItem.getName());
            }

            //查询出关联面板项
            if (panelItem.getHasSubItem() != null && panelItem.getHasSubItem() == YesOrNo.NO.getValue() && panelItem.getRefItemId() != null) {
                PanelItem relatedItem = panelItemRepository.getPanelItemById(panelItem.getRefItemId());
                if (relatedItem != null) {
                    panelItem.setRelatedItemTitle(relatedItem.getName());
                }
            }
        }
        return pageable;
    }

    /**
     * success:新增成功
     * failed：新增失败
     * related：父转子，父被关联了，不允许修改
     * typeChange：父转父，原父被关联了，不允许修改
     *
     * @param panelItem
     * @return
     */
    @Override
    public String savePanelItemData(PanelItem panelItem) {

        //设置districtCode
        panelItem.setDistrictCode(getCurrentDistrictCode());
        //如果关联引用不为空，判断被关联的面板项是否被重复关联
        if (panelItem.getRefItemId() != null) {
            PanelItem relatedItem = panelItemRepository.getPanelItemById(panelItem.getRefItemId());
            if (relatedItem.getRefItemId() == null) {
                panelItemRepository.savePanelItem(panelItem);
                relatedItem.setRefItemId(panelItem.getId());
                panelItemRepository.updatePanelItem(relatedItem);//done
            } else {
                return Constant.BINED_PANEL_ITEM;
            }
        } else {
            panelItemRepository.savePanelItem(panelItem);
        }
        return Constant.SUCCESS;
    }

    @Override
    public String updatePanelItemData(PanelItem panelItem,Long oprUserId) {
        panelItem.setCreateTime(DateUtils.getCurrentDate());
        panelItem.setUpdateTime(DateUtils.getCurrentDate());
        panelItem.setOprUserId(oprUserId);
        PanelItem oldPanelItem = panelItemRepository.getPanelItemById(panelItem.getId());

        //父-->子
        if (oldPanelItem.getHasSubItem() == 1 && panelItem.getHasSubItem() == 0) {
            List<PanelItem> panelItemList = panelItemRepository.findRelatedOrParentItemList(panelItem.getId());
            if (!CollectionUtils.isEmpty(panelItemList)) {
                return Constant.BINED_PANEL_ITEM;
            }
            panelItemRepository.updatePanelItem(panelItem);
        }

        //父-->父
        if (oldPanelItem.getHasSubItem() == 1 && panelItem.getHasSubItem() == 1) {
            List<PanelItem> panelItemList = panelItemRepository.findRelatedOrParentItemList(panelItem.getId());
            if (!CollectionUtils.isEmpty(panelItemList)) {
                if (!StringUtils.equals(oldPanelItem.getContentType(), panelItem.getContentType())) {
                    return Constant.PANEL_ITEM_TYPE_CHANGE;
                }
            }
            if(oldPanelItem.getRefItemId() != null){
                panelItem.setRefItemId(oldPanelItem.getRefItemId());
            }
            panelItemRepository.updatePanelItem(panelItem);
        }

        //子-->子
        if (oldPanelItem.getHasSubItem() == 0 && panelItem.getHasSubItem() == 0) {

            //如果面板项已经有父面板或已经被关联，不允许修改类型
            if(oldPanelItem.getRefItemId() != null || oldPanelItem.getPanelItemParentId() != null) {
                if(!StringUtils.equals(panelItem.getContentType(),oldPanelItem.getContentType())) {
                    return Constant.PANEL_ITEM_TYPE_CHANGE;
                }
            }

            /*if (StringUtils.equals(panelItem.getContentType(), PanelItemContentType.REF.getValue()) &&
                    !StringUtils.equals(oldPanelItem.getContentType(), PanelItemContentType.REF.getValue())) {
                if (oldPanelItem.getRefItemId() != null) {
                    PanelItem relatedItem = panelItemRepository.getPanelItemById(oldPanelItem.getRefItemId());
                    if (relatedItem != null) {
                        relatedItem.setRefItemId(null);
                        relatedItem.setOprUserId(oprUserId);
                        relatedItem.setCreateTime(DateUtils.getCurrentDate());
                        relatedItem.setUpdateTime(DateUtils.getCurrentDate());
                        panelItemRepository.updatePanelItem(relatedItem);
                    }
                }
            }*/

            if (panelItem.getRefItemId() != null) {
                PanelItem relatedItem = panelItemRepository.getPanelItemById(panelItem.getRefItemId());
                relatedItem.setOprUserId(oprUserId);
                relatedItem.setCreateTime(DateUtils.getCurrentDate());
                relatedItem.setUpdateTime(DateUtils.getCurrentDate());
                //原先的面板项关联item为空，直接把关联的item的refItemId赋值为当前item的ID
                if (oldPanelItem.getRefItemId() == null) {
                    relatedItem.setRefItemId(panelItem.getId());
                    panelItemRepository.updatePanelItem(relatedItem);
                } else {
                    //原来的面板项关联item不为空，并且新关联的item和原来关联的item不等，需要把原来关联的item的refItemId致空，把新关联的item的refItemId赋值为当前item的ID
                    if (!panelItem.getRefItemId().equals(oldPanelItem.getRefItemId())) {
                        relatedItem.setRefItemId(null);
                        panelItemRepository.updatePanelItem(relatedItem);
                        PanelItem newRelatedItem = panelItemRepository.getPanelItemById(panelItem.getRefItemId());
                        newRelatedItem.setRefItemId(panelItem.getId());
                        newRelatedItem.setOprUserId(oprUserId);
                        newRelatedItem.setCreateTime(DateUtils.getCurrentDate());
                        newRelatedItem.setUpdateTime(DateUtils.getCurrentDate());
                        panelItemRepository.updatePanelItem(newRelatedItem);
                    }
                }
            } else {
                //如果关联面板项为空，需要把原先被关联的面板项refItemId致空
                if (oldPanelItem.getRefItemId() != null) {
                    PanelItem relatedItem = panelItemRepository.getPanelItemById(oldPanelItem.getRefItemId());
                    relatedItem.setRefItemId(null);
                    relatedItem.setOprUserId(oprUserId);
                    relatedItem.setCreateTime(DateUtils.getCurrentDate());
                    relatedItem.setUpdateTime(DateUtils.getCurrentDate());
                    panelItemRepository.updatePanelItem(relatedItem);
                }
            }
            panelItemRepository.updatePanelItem(panelItem);
        }
        //子-->父
        if (oldPanelItem.getHasSubItem() == 0 && panelItem.getHasSubItem() == 1) {
            if (oldPanelItem.getRefItemId() != null) {
                PanelItem relatedItem = panelItemRepository.getPanelItemById(oldPanelItem.getRefItemId());
                relatedItem.setRefItemId(null);
                relatedItem.setOprUserId(oprUserId);
                relatedItem.setCreateTime(DateUtils.getCurrentDate());
                relatedItem.setUpdateTime(DateUtils.getCurrentDate());
                panelItemRepository.updatePanelItem(relatedItem);
            }
            panelItemRepository.updatePanelItem(panelItem);
        }

        return Constant.SUCCESS;
    }

    @Override
    public void savePanelItem(PanelItem panelItem) {
        panelItemRepository.savePanelItem(panelItem);
    }

    @Override
    public void deletePanelItemByIds(List<Long> ids) {

        if (CollectionUtils.isEmpty(ids)) return;
        //删除面板项面板关联数据
        for (Long panelItemId : ids) {
            panelPanelItemMapRepository.deleteByPanelItemId(panelItemId);
            PanelItem panelItem = panelItemRepository.getPanelItemById(panelItemId);
            panelItemRepository.deletePanelItem(panelItem);
        }
    }

    @Override
    public void updatePanelItem(PanelItem panelItem) {
        panelItemRepository.updatePanelItem(panelItem);// not usage
    }

    @Override
    public PanelItem getPanelItemById(Long id) {
        return panelItemRepository.getPanelItemById(id);
    }

    @Override
    public PanelItem getPanelItemDetail(Long id) {

        PanelItem panelItem = panelItemRepository.getPanelItemById(id);
        if (panelItem == null) return null;

        //如果是引用类型的话，取引用类型的显示图片
        /*if (StringUtils.isNotBlank(panelItem.getContentType()) && StringUtils.equals(PanelItemContentType.REF.getValue(), panelItem.getContentType())
                && panelItem.getRefItemId() != null) {
            panelItem = panelItemRepository.getPanelItemById(panelItem.getRefItemId());
        }*/

        //如果是父面板项，关联出子面板项
        if (panelItem.getHasSubItem() != null && panelItem.getHasSubItem().equals(YesOrNo.YES.getValue())) {
            List<PanelItem> childrenList = panelItemRepository.findSublItemListByPanelItemParentId(panelItem.getId());
            if (!CollectionUtils.isEmpty(childrenList)) {
                panelItem.setChildrenList(childrenList);
            }
        }
        return panelItem;
    }

    @Override
    public List<PanelItem> selectParentItemList(Map<String, Object> map) {
        return panelItemRepository.findParentItemList(map);
    }

    @Override
    public List<PanelItem> selectRelatedItemList(Map<String, Object> map) {
        return panelItemRepository.findRelatedItemList(map);
    }

    @Override
    public List<PanelItem> getAllTargetList() {
        return panelItemRepository.findAllPanelItemList();
    }

    @Override
    public List<PanelItem> findPanelItemListByDpi(String dpi) {
        return panelItemRepository.findPanelItemListByDpi(dpi);
    }

    @Override
    public List<PanelItem> findSublItemListByPanelItemParentId(Long panelItemParentId) {
        return panelItemRepository.findSublItemListByPanelItemParentId(panelItemParentId);
    }

    /*@Override
    public List<PanelItem> getNotRefPanelItemList() {
        return panelItemRepository.findNotRefPanelItemList(YesOrNo.NO.getValue(), PanelItemContentType.REF.getValue());
    }*/

    /*@Override
    public List<PanelItem> getNotRefPanelItemListExcludeSelf(Long editPanelItemId) {
        return panelItemRepository.findNotRefPanelItemListExcludeSelf(YesOrNo.NO.getValue(), PanelItemContentType.REF.getValue(), editPanelItemId);
    }*/

    @Override
    public PanelItem getPanelItemByName(String name) {
        return this.panelItemRepository.getPanelItemByName(name);
    }
}
