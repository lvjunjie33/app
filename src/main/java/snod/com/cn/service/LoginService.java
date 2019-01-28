package snod.com.cn.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import snod.com.cn.basic.redis.RedisService;
import snod.com.cn.basic.utils.MD5Util;
import snod.com.cn.dao.LoginDao;
import snod.com.cn.entity.AccountInfo;
import snod.com.cn.entity.UserInfo;
/**
 * 用户相关数据库操作实现类
 * @author lvjj
 * @since 2018年12月5日
 */
@Service
public class LoginService {
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private RedisService redisService;
	
	public UserInfo loginEmail(String email, String passwd) {
		passwd=MD5Util.encodeMD5Hex(passwd);
		UserInfo result=loginDao.loginEmail(email,passwd);
		return result;
	}
	public AccountInfo queryAccount(String email) {
		return loginDao.queryAccount(email);
	}
	public void loginPhone(String phoneNum, String passwd) {

	}
	@Transactional
	public Map<String,Object> emailRegister(String email, String passwd) {
		Map<String,Object>result=new HashMap<String,Object>();
		UserInfo userInfo=new UserInfo();
		loginDao.addUserInfo(userInfo);
		AccountInfo ac=new AccountInfo();
		ac.setAccountNum(email);
		ac.setAccountType(1);
		ac.setCreateTime(new Date());
		ac.setPasswd(MD5Util.encodeMD5Hex(passwd));
		ac.setUserId(userInfo.getId());
		int len=loginDao.emailRegister(ac);
		result.put("userInfo", userInfo);
		result.put("len", len);
		return result;
	}
	public int queryAccountInfo(String email) throws ParseException {

		return loginDao.queryAccountInfo(email);
	}
	public boolean testRedis(String messageSendChanel, String passwd) {
		return redisService.sendMessage(messageSendChanel, passwd);
		
	}
	public boolean testRedisEmail(String messageSendChanel, String passwd) {
		// TODO Auto-generated method stub
		return redisService.sendMessage(messageSendChanel, passwd);
	}
	public int updatePasswd(String email, String passwdNew, String passwdOld) {
		// TODO Auto-generated method stub
		return loginDao.updatePasswd(email, passwdNew,passwdOld);
	}
	public void setCache(String key, UserInfo result) {
		// TODO Auto-generated method stub
		redisService.set(key, result);
	}
	
	


	

}
