package com.ysten.local.bss.device.domain;

import java.io.Serializable;
import java.util.Date;

import com.ysten.utils.bean.IEnumDisplay;

/**
 *    
 * 项目名称：yst-lbss
 * 类名称：     SpDefine   
 * 类描述：    运营商
 * 创建人：       
*/
public class SpDefine implements Serializable {
	
	
	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     *运营商id
     */
    private Integer id;

    /**
     * 运营商代码
     */
    private String code;

    /**
     * 运营商名称
     */
    private String name;
    
    /**
     * 运营商描述
     */
    private String description;

    /**
     * 状态
     */
    private State state;
    
    /**
     * 创建时间
     */
    private Date createDate;
    
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public State getState() {
        return state;
    }


    public void setState(State state) {
        this.state = state;
    }


    public Date getCreateDate() {
        return createDate;
    }


    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString(){
    	return this.name;
    }

    public enum State implements IEnumDisplay{
        USABLE("可用"),UNUSABLE("不可用");
        private String msg;
        private State(String msg){
            this.msg = msg;
        }
        @Override
        public String getDisplayName() {
            return this.msg;
        }
    }
}