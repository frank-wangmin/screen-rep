package com.ysten.local.bss.system.domain;

import java.io.Serializable;

import com.ysten.utils.bean.IEnumDisplay;
import com.ysten.utils.code.MD5;

/**
 *    
 * 项目名称：yst-oms
 * 类名称：     Operator   
 * 类描述：     操作员信息
 * 创建人：     jiangzhengyi   
 * 创建时间：2011-08-09   
*/
public class Operator implements Serializable {
	private static final long serialVersionUID = -7059331243549997464L;

	/**
	 * 主键
	 */
    private Long id;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 登陆名称
     */
    private String loginName;

    /**
     * 用户名称
     */
    private String displayName;

   /**
    * 登陆密码
    */
    private String password;
    
   private State state;
   
   private String stateName;

	public Operator()
    {}
    
    public Operator(Long id,String email,String loginName,String displayName,String password,State state,Integer spId,String catgId,String cpId)
    {
    	this.id = id;
    	this.email = email;
    	this.loginName = loginName;
    	this.displayName = displayName;
    	this.password = password;
    	this.state = state;

    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        this.stateName = state.getDisplayName();
    }

    public String getStateName() {
        return stateName;
    }

    /**
     * 主键
     * @return
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取电子邮件
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮件
     * @param email
     */
    public void setEmail(String email) {
        this.email = email == null ? "" : email.trim();
    }

    /**
     * 获取登陆名称
     * @return
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登陆名称
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? "" : loginName.trim();
    }

    /**
     * 获取用户名称
     * @return
     */
    public String getDisplayName() {
        return displayName;
    }

   /**
    * 设置登陆名称
    * @param displayName
    */
    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? "" : displayName.trim();
    }

    /**
     * 获取密码
     * @return
     */
    public String getPassword() {
        return password;
    }

   /**
    * 设置密码
    * @param password
    */
    public void setPassword(String password) {
        this.password = password == null ? "" : password.trim();
    }

    /**
     * 增加一个设置密码密码
     * @param password
     */
    public void setNoEncryptPassword(String password) {
        this.password = MD5.encrypt(password == null ? "" : password.trim());
    }

    /**
     * 增加一个密码检查接口
     * @param password
     * @return
     */
    public boolean checkPassword(String password) {
        return MD5.encrypt(password.trim()).equals(this.password);
    }

    public enum State implements IEnumDisplay{
        NORMAL("正常"),LOCK("锁定"),CANCEL("注销");
        private String msg;
        private State(String msg){
            this.msg = msg;
        }
        @Override
        public String getDisplayName() {
            return this.msg;
        }
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Operator [id=").append(id).append(", email=").append(email).append(", loginName=").append(loginName).append(", displayName=")
				.append(displayName).append(", state=").append(state).append(", stateName=").append(stateName)
				.append("]");
		return builder.toString();
	}
}