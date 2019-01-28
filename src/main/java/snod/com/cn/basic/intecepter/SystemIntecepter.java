package snod.com.cn.basic.intecepter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import snod.com.cn.ResultInfo;
import snod.com.cn.ResultTools;
import snod.com.cn.basic.redis.RedisService;
import snod.com.cn.basic.systeam.Constants;

/**
 * @author lvjj
 * */
public class SystemIntecepter implements HandlerInterceptor{

	
	@Autowired
	private RedisService redisService;
	/*在执行Controller的任务之前判断是否有Session信息
	    如果有Session信息就往下执行，去调用Controller。
	    如果没有Session就跳转到登录页面
	*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		 String uri = request.getRequestURI();
		 ResultInfo result=new ResultInfo();
//		String ua = request.getParameter("ua");
	    String language = request.getHeader(Constants.PARAM_LANGUAGE);
	    String channel = request.getHeader(Constants.PARAM_CHANNEL);
	    String version = request.getHeader(Constants.PARAM_VERSION);
	    String sdk = request.getHeader(Constants.PARAM_SDK);
	    String network = request.getHeader(Constants.PARAM_NETWORK);
	    String key = request.getHeader(Constants.PARAM_DICK_VALUE_KEY);
		 //不拦截druid数据库监控界面和swagger ui界面
		 if(uri.contains("druid") || uri.contains("swagger") || uri.contains("v2/api-docs") || uri.contains("login/emailRegister")) {
			 return true;
		 }
		 //限制登录次数，同一个用户半小时内不能频繁调用登录接口5次
		 if(uri.contains("login")) {
			  if(checkLogin(request,response)) {
				  return true;
			  }else {
				  return false;
			  }
		 }
		 //初始化接口验证key，获取token
	    if(uri.contains("init")) {
	    	//key是否为空
	    	if (StringUtils.isEmpty(key)){
	    		response.setContentType("text/html;charset=UTF-8");
	    		response.getWriter().write(ResultTools.result(ResultTools.ERROR_CODE_4006, null, null).toString());
	    		return false;
	    	}else {
	    		//验证Key是否合法
	    		if(!Constants.KEY.equals(key)) {
	    			response.setContentType("text/html;charset=UTF-8");
		    		response.getWriter().write(ResultTools.result(ResultTools.ERROR_CODE_4007, null, null).toString());
		    		return false;
	    		}
	    	}
	    }else{
	    		//业务接口，验证token是否为空
		    	String token = request.getHeader(Constants.PARAM_TOKEN);
		    	if(StringUtils.isEmpty(token)) {
	    		response.setContentType("text/html;charset=UTF-8");
	    		response.getWriter().write(ResultTools.result(ResultTools.ERROR_CODE_4005, null, null).toString());
	    		return false;
	    	}else{
	    		//验证是否非法token
	    		String apptoken=(String) redisService.get("token");
		    	if(!token.equals(apptoken)) {
		    		response.setContentType("text/html;charset=UTF-8");
		    		response.getWriter().write(ResultTools.result(ResultTools.ERROR_CODE_4002, null, null).toString());
		    		return false;
		    	}
	    	}
	    }
		//验证其他参数是否为空
	      if (StringUtils.isEmpty(language) ||
	              StringUtils.isEmpty(channel) ||
	              StringUtils.isEmpty(version) ||
	              StringUtils.isEmpty(network) ||
	              StringUtils.isEmpty(sdk) ||
	              !version.matches("\\d+")) {
	  		response.setContentType("text/html;charset=UTF-8");
	  		response.getWriter().write(ResultTools.result(ResultTools.ERROR_CODE_4001, null, null).toString());
	        return false;
	      }
	return true;
	    
//		boolean check=false;
//
//        if (StringUtils.hasText(ua))
//        {
//        	return "ios".equals(ua) || "android".equals(ua);
//        }
//        else {
//        	String userAgent = request.getHeader("User-Agent");
//        	check=userAgent.matches(".*Android.*") || userAgent.matches(".*iPhone.*") || userAgent.matches(".*iPad.*");
//        }
//		return check;
	}
	/**
	 * 限制登录注册次数，同一个用户半小时内不能频繁调用登录注册接口5次
	 * */
	public boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 String email = request.getParameter("email");
		 Map<String,Object> mappa=new HashMap<String,Object>();
		 if(redisService.get(email)!=null) {
			 long timeStamp=(long) ((Map)redisService.get(email)).get("timeStamp");
			 int count=(int) ((Map)redisService.get(email)).get("count");
				if(System.currentTimeMillis()-timeStamp<1000 * 60 *30) {
					if(count<5000) {
						count++;
						mappa.put("email", email);
						mappa.put("count", count);
						mappa.put("timeStamp", timeStamp);
						redisService.set(email,mappa);
						return true;
					}if(count>=5000) {
						response.setContentType("text/html;charset=UTF-8");
						response.getWriter().write(ResultTools.result(ResultTools.ERROR_CODE_2008,null,null).toString());
						return false;
					}
						
				}else {
				    count=0;
					count++;
					mappa.put("email", email);
					mappa.put("count", count);
					mappa.put("timeStamp", System.currentTimeMillis());
					redisService.set(email,mappa);
					return true;
				}
			 }
			 else {
				int count=0;
				count++;
				mappa.put("email", email);
				mappa.put("count", count);
				mappa.put("timeStamp", System.currentTimeMillis());
				redisService.set(email,mappa);
				return true;
			 }
		return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
		
	}
	
}
