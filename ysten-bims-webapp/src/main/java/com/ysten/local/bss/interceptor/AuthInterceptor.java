//package com.ysten.boss.oms.interceptor;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.exception.ExceptionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import com.ysten.boss.system.domain.SysMenu;
//import com.ysten.boss.util.Constants;
//
//public class AuthInterceptor extends HandlerInterceptorAdapter {
//
//	private static Map<String, String> passDos = new HashMap<String, String>();
//	
//	private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);
//	
//	static{
//		passDos.put("check_login.do","check_login.do");
//    	passDos.put("login_in.do","login_in.do");
//    	passDos.put("top.do","top.do");
//    	passDos.put("middle.do","middle.do");
//    	passDos.put("bottom.do","bottom.do");
//    	passDos.put("tree.do","tree.do");
//    	passDos.put("login_out.do","login_out.do");
//    	passDos.put("getData.do","getData.do");
//    	passDos.put("getSpDefine.do","getSpDefine.do");
//    	passDos.put("offline.do","offline.do");
//    	passDos.put("updateCisData.do","updateCisData.do");
//    	passDos.put("updateprice.do","updateprice.do");
//    	
//	}
//    
//    /*
//     * 请求do是否是允许访问的do
//     */
//    private boolean isPass(Map<String, String> passDo, String requestDo){
//    	
//   		if( passDo.get(requestDo) != null){
//   			return true;
//   		}
//    	return false;
//    }
//    
//	@SuppressWarnings("unchecked")
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//	    
//	    boolean resultBool = false;
//        
//		String path = request.getContextPath();
//		String reqPath = request.getRequestURI();
//    	String [] spStr = reqPath.split("/");
//    	String eqPath = spStr[spStr.length - 1];
//    	
//		if (!isPass(passDos, eqPath)) {
//			
//			if (request.getSession().getAttribute(Constants.LOGIN_SESSION_OPERATOR) == null) {
//                try {
//                    response.sendRedirect(path);
//                } catch (IOException e) {
//                    LOGGER.error(ExceptionUtils.getFullStackTrace(e));
//                }
//                return false;
//            }
//			
//			Object obj = request.getSession().getAttribute(Constants.LOGIN_SESSION_OPERATOR_MENU);
//			
//			if(obj != null){
//	        	Map<String, SysMenu> operatorSysMenu = (Map<String, SysMenu>)obj;
//	        	
//	        	if(operatorSysMenu.get(eqPath) != null){
//	        		SysMenu sm = (SysMenu)operatorSysMenu.get(eqPath) ;
//	        	
//	        	    request.getSession().setAttribute(Constants.LOGIN_SESSION_TOP_ID, sm.getParentId());
//	        	    request.getSession().setAttribute(Constants.LOGIN_SESSION_SUCCESS_URL, eqPath);
//	        	}
//        	}
//        	
//        }
//        try {
//            resultBool = super.preHandle(request, response, handler);
//        } catch (Exception e) {
//            LOGGER.error(ExceptionUtils.getFullStackTrace(e));
//        }
//    	return resultBool;
//    }
//    
//}
