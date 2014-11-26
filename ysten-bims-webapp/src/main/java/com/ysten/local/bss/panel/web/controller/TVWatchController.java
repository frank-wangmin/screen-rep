package com.ysten.local.bss.panel.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.bsstv.domain.RecCategory;
import com.ysten.local.bss.bsstv.domain.RecProgramContentOperate;
import com.ysten.local.bss.bsstv.service.IProgramContentService;
import com.ysten.utils.page.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yaoqy on 14-10-15.
 */
/*@Controller
public class TVWatchController {
    private static Logger logger = LoggerFactory.getLogger(PanelPackageController.class);
    @Autowired
    private IProgramContentService programContentService;

    @ResponseBody
    @RequestMapping(value = "/TV_program_list.json")
    public Pageable<RecProgramContentOperate> getProgramContentList(@RequestParam(value="programId", defaultValue="")Long programId,
                                                                    @RequestParam(value="programName", defaultValue="")String programName,
                                                                    @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
                                                                    @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize) {
        return programContentService.getProgramContentList(programId, programName,Integer.valueOf(pageNo),Integer.valueOf(pageSize));
    }

    @ResponseBody
    @RequestMapping(value = "/TV_category_list.json")
    public Pageable<RecCategory> getcategoryList(@RequestParam(value="cateId", defaultValue="")Long cateId,
                                                                    @RequestParam(value="cateName", defaultValue="")String cateName,
                                                                    @RequestParam(value = Constants.START, defaultValue = Constants.PAGE_NO_DEFAULT_VALUE) String pageNo,
                                                                    @RequestParam(value = Constants.LIMIT, defaultValue = Constants.PAGE_NUMBER) String pageSize) {
        return programContentService.findCategoryList(cateId, cateName,Integer.valueOf(pageNo),Integer.valueOf(pageSize));
    }
}*/
