package com.ysten.local.bss.common.web.controller;

import com.ysten.local.bss.bean.Constants;
import com.ysten.local.bss.device.domain.City;
import com.ysten.local.bss.system.vo.Tree;
import com.ysten.local.bss.utils.ControllerUtil;
import com.ysten.local.bss.web.service.ICityWebService;
import com.ysten.local.bss.web.service.ILoggerWebService;
import com.ysten.utils.http.RenderUtils;
import com.ysten.utils.json.EnumDisplayUtil;
import com.ysten.utils.json.JsonUtils;
import com.ysten.utils.json.TextValue;
import com.ysten.utils.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CityController {

    @Autowired
    private ICityWebService cityWebService;
    @Autowired
    private ILoggerWebService loggerWebService;

    @RequestMapping(value = "/to_city_list")
    public String toCityList(ModelMap model) {
        return "/common/city_list";
    }

    @RequestMapping(value = "city_add")
    public void addCity(City city, HttpServletRequest request, HttpServletResponse response) {
        city.setCreateDate(new Date());
        boolean bool = this.cityWebService.saveCity(city);
        // 记录系统操作日志
        String logDescription = ((bool == true) ? "添加地市信息成功" : "添加地市信息失败") + "。地市信息：" + JsonUtils.toJson(city);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        loggerWebService.saveOperateLog(Constants.CITY_INFO_MAINTAIN, Constants.ADD, city.getId() + "", logDescription,
                request);

    }

//    public void addCity(City city, @RequestParam("leaderDistCode") String leaderDistCode, HttpServletRequest request, HttpServletResponse response) {
//        Long leaderId = this.cityWebService.getCityByDistCode(leaderDistCode).getLeaderId();
//        city.setLeaderId(leaderId);
//        city.setCreateDate(new Date());
//        boolean bool = this.cityWebService.saveCity(city);
//        // 记录系统操作日志
//        String logDescription = ((bool == true) ? "添加地市信息成功" : "添加地市信息失败") + "。地市信息：" + JsonUtils.toJson(city);
//        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
//        loggerWebService.saveOperateLog(Constants.CITY_INFO_MAINTAIN, Constants.ADD, city.getId() + "", logDescription,
//                request);
//
//    }

//    @RequestMapping(value = "to_city_update.json")
//    public void toUpdateCity(@RequestParam("distCode") String distCode, HttpServletResponse response) {
//        if (StringUtils.isNotBlank(distCode)) {
//            City city = this.cityWebService.getCityByDistCode(distCode);
//            RenderUtils.renderJson(JsonUtils.toJson(city), response);
//        }
//    }

    @RequestMapping(value = "to_city_update.json")
    public void toUpdateCity(@RequestParam("id") Long id, HttpServletResponse response) {
        if (id != null && !"".equals(id.toString())) {
            City city = this.cityWebService.getCityById(id);
            RenderUtils.renderJson(JsonUtils.toJson(city), response);
        }
    }

    @RequestMapping(value = "city_update")
    public void updateCity(City city, HttpServletRequest request, HttpServletResponse response) {
        City cityAdd = this.cityWebService.getCityById(city.getId());
        cityAdd.setCode(city.getCode());
        cityAdd.setName(city.getName());
        boolean bool = this.cityWebService.updateCity(cityAdd);
        // 记录系统操作日志
        String logDescription = ((bool == true) ? "修改地市信息成功" : "修改地市信息失败") + "。地市信息：" + JsonUtils.toJson(city);
        RenderUtils.renderText(ControllerUtil.returnString(bool), response);
        loggerWebService.saveOperateLog(Constants.CITY_INFO_MAINTAIN, Constants.MODIFY, city.getId() + "",
                logDescription, request);

    }

    /*
     * @RequestMapping(value = "city_list.json") public void findAllCity(
     * 
     * @RequestParam(value = "name", defaultValue = "") String name,
     * 
     * @RequestParam(value = "code", defaultValue = "") String code,
     * 
     * @RequestParam(value = "start", defaultValue = "1") Integer start,
     * 
     * @RequestParam(value = "limit", defaultValue =
     * Constants.PAGE_NUMBER)Integer limit, HttpServletResponse response){
     * Pageable<City> cityPage =
     * this.cityWebService.findCityList(name,code,start, limit);
     * RenderUtils.renderJson(EnumDisplayUtil.toJson(cityPage), response); }
     */
    /**
     * 获取地市信息树形列表
     */
    @RequestMapping(value = "city_list.json")
    public void findAllCityTree(HttpServletResponse response) {
        List<Tree> cityList = this.cityWebService.getCityTree();
        RenderUtils.renderJson(EnumDisplayUtil.toJson(cityList), response);
    }

    @RequestMapping(value = "/city_delete.json")
    public void deleteCity(@RequestParam(Constants.IDS) String ids, HttpServletRequest request,
            HttpServletResponse response) {
        boolean bool = false;
        if (StringUtils.isNotBlank(ids)) {
            List<Long> idsList = StringUtil.splitToLong(ids);
            bool = this.cityWebService.deleteCity(idsList);
            RenderUtils.renderText(ControllerUtil.returnString(true), response);
        }
        String logDescription = (bool == true) ? "删除地市信息成功" : "删除地市信息失败";
        loggerWebService.saveOperateLog(Constants.CITY_INFO_MAINTAIN, Constants.MODIFY, ids + "", logDescription,
                request);
    }

    @RequestMapping(value = "/city_province.json")
    public void getProvince(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<City> cityList = this.cityWebService.findAllProvince();
       /* if (par != null && Constants.ZERO==(Integer.parseInt(par))) {
            tv.add(new TextValue("", "请选择"));
        }*/
        for (City city : cityList) {
            tv.add(new TextValue(city.getDistCode().trim(), city.getName().trim()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }

    @RequestMapping(value = "/city.json")
    public void getCity(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<City> cityList = this.cityWebService.findAllCity();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("0", "所有地市"));
        }
        for (City city : cityList) {
            tv.add(new TextValue(city.getId().toString(), city.getName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }
    @RequestMapping(value = "/cityList_distCode.json")
    public void getAllCity(@RequestParam(value = Constants.PAR, defaultValue = "") String par, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        List<City> cityList = this.cityWebService.findAllCity();
        if (par != null && !par.isEmpty()) {
            tv.add(new TextValue("", "所有地市"));
        }
        for (City city : cityList) {
            tv.add(new TextValue(city.getDistCode(), city.getName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }
    
    @RequestMapping(value = "/city_by_area.json")
    public void getCityByArea(@RequestParam(value = Constants.PAR, defaultValue = "") String par,@RequestParam(value = "areaId", defaultValue = "") Long areaId, HttpServletResponse response) {
        List<TextValue> tv = new ArrayList<TextValue>();
        if(!par.equals("")){
        	City city = this.cityWebService.getCityById(Long.parseLong(par));
        	if(areaId.intValue() != city.getLeaderId().intValue()){
        		tv.add(new TextValue(city.getId().toString(), city.getName()));
        	}
        }
        List<City> cityList = this.cityWebService.findCityListByLeader(areaId);
        for (City city : cityList) {
            tv.add(new TextValue(city.getId().toString(), city.getName()));
        }
        RenderUtils.renderJson(JsonUtils.toJson(tv), response);
    }
}
