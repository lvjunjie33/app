package snod.com.cn.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jpush.api.push.PushResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import snod.com.cn.EcpStackTrace;
import snod.com.cn.ResultInfo;
import snod.com.cn.ResultTools;
import snod.com.cn.basic.utils.MD5Util;
import snod.com.cn.entity.UserInfo;
import snod.com.cn.service.JpushService;
import snod.com.cn.service.LoginService;

/**
 * @author lvjj
 * 
 * */

@Api(tags= {"登录接口"})
@RestController
@RequestMapping("/login")
public class LoginController {
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private LoginService loginService;

	@Autowired
	JpushService jpushService;
	
	/**
	 * 邮箱登录
	 * @param email 邮箱
	 * @param passwd 密码
	 */
	@RequestMapping("/loginEmail")
	@ApiOperation(notes="邮箱登录",value="邮箱登录",httpMethod="POST")
	 
	@ApiImplicitParams({
		@ApiImplicitParam(name="email",paramType="query",dataType="string"),
		@ApiImplicitParam(name="passwd",paramType="query",dataType="string")
	})
	public ResultInfo loginEmail(String email,String passwd){
		try {
		UserInfo userInfo=loginService.loginEmail(email,passwd);
		if(userInfo==null) {
			return ResultTools.result(ResultTools.ERROR_CODE_2003,null,null);
		}else {
			loginService.setCache(userInfo.getId()+"", userInfo);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("userInfo", userInfo);
			return ResultTools.result(ResultTools.ERROR_CODE_0,null,map);
		}
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, e.getMessage(), null);
		}
		
	}
	

	
	/**
	 * 邮箱注册
	 * @param email 用户对象
	 * @param passwd 密码
	 */
	@RequestMapping("/emailRegister")
	@ApiOperation(notes="邮箱注册",value="邮箱注册",httpMethod="POST")
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="email",paramType="query",dataType="string"),
		@ApiImplicitParam(name="passwd",paramType="query",dataType="string")
	})
	public ResultInfo emailRegister(String email,String passwd){
		try {
			Map<String,Object> result=new HashMap<String,Object>();
			//查询该邮箱是否存在
			int lens=loginService.queryAccountInfo(email);
			if(lens>0) {
				return ResultTools.result(ResultTools.ERROR_CODE_2002, null, null);
			}
			//注册
			Map<String,Object> resultData=loginService.emailRegister(email,passwd);
			result.put("userId", ((UserInfo)resultData.get("userInfo")).getId());
			if((int)resultData.get("len")>0) {
				return ResultTools.result(ResultTools.ERROR_CODE_0, null, result);
			}else {
				return ResultTools.result(ResultTools.ERROR_CODE_2007, null, null);
			}
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, e.getMessage(), null);
		}
		
		
	}
	
//	/**
//	 * 测试redismq
//	 * @param email 用户对象
//	 * @param passwd 密码
//	 */
//	@RequestMapping("/testRedis")
//	@ApiOperation(notes="测试redismq",value="测试redismq",httpMethod="POST")
//	
//	@ApiImplicitParams({
//		@ApiImplicitParam(name="email",paramType="query",dataType="string"),
//		@ApiImplicitParam(name="passwd",paramType="query",dataType="string")
//	})
//	public ResultInfo testRedis(String email,String passwd){
//		if(loginService.testRedis(RedisChanel.MESSAGE_SEND_CHANEL,passwd)) {
//			System.out.println("消息发布成功");
//		}
//		return null;		
//	}
	
//	/**
//	 * 测试发送邮件
//	 * @param email 用户对象
//	 * @param passwd 密码
//	 */
//	@RequestMapping("/testRedisEmail")
//	@ApiOperation(notes="测试发送邮件",value="测试发送邮件",httpMethod="POST")
//	
//	@ApiImplicitParams({
//		@ApiImplicitParam(name="email",paramType="query",dataType="string"),
//		@ApiImplicitParam(name="passwd",paramType="query",dataType="string")
//	})
//	public ResultInfo testRedisEmail(String email,String passwd){
//		if(loginService.testRedisEmail(RedisChanel.EMAIL_SEND_CHANEL,passwd)) {
//			System.out.println("邮件发送成功");
//		}
//		return null;		
//	}
	
	
	/**
	 * 修改密码
	 * @param email 用户对象
	 * @param email 密码
	 * @param passwdNew 新密码
	 * @param passwdOld 旧密码
	 */
	@RequestMapping("/updatePasswd")
	@ApiOperation(notes="修改密码",value="修改密码",httpMethod="POST")
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="email",paramType="query",dataType="string"),
		@ApiImplicitParam(name="passwdNew",paramType="query",dataType="string"),
		@ApiImplicitParam(name="passwdOld",paramType="query",dataType="string")
		
	})
	public ResultInfo updatePasswd(String email,String passwdNew,String passwdOld){
		try {
			passwdNew=MD5Util.encodeMD5Hex(passwdNew);
			passwdOld=MD5Util.encodeMD5Hex(passwdOld);
			int len=loginService.updatePasswd(email,passwdNew,passwdOld);
			if(len>0) {
				return ResultTools.result(ResultTools.ERROR_CODE_0, null, null);
			}else {
				return ResultTools.result(ResultTools.ERROR_CODE_2010, null, null);
			}
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, e.getMessage(), null);
		}
		
				
	}
	
	
	/**
	 * 极光推送测试
	 * @param title 推送标题
	 * @param content  推送内容
	 */
	@RequestMapping("/jiguangTest")
	@ApiOperation(notes="修改密码",value="修改密码",httpMethod="POST")
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="title",paramType="query",dataType="string"),
		@ApiImplicitParam(name="content",paramType="query",dataType="string"),	
		@ApiImplicitParam(name="email",paramType="query",dataType="string"),
	})
	public ResultInfo jiguangTest(String title,String content){
		
		Map<String, String> extrasMap = new HashMap<String, String>();
		PushResult len=jpushService.sendCustomPush(title, content, extrasMap, "Abc_123");
		if(len.isResultOK()) {
			return ResultTools.result(ResultTools.ERROR_CODE_0, null, null);
		}else {
			return ResultTools.result(ResultTools.ERROR_CODE_2010, null, null);
		}
				
	}
	

}
