package snod.com.cn.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jpush.api.report.MessagesResult.Android;
import snod.com.cn.basic.redis.RedisService;
import snod.com.cn.dao.InitDao;
/**
 * 用户相关数据库操作实现类
 * @author lvjj
 * @since 2018年11月30日
 */
@Service
public class InitService {
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private InitDao initDao;

	public void setCache(Map<String, Object> userInfo) {
		// TODO Auto-generated method stub
		redisService.set("userInfo", userInfo);
	}

	public Map<String,Object> findUserInfo(int userId) {
		// TODO Auto-generated method stub
		Map<String,Object> userInfo=(Map<String,Object>) redisService.get(userId+"");
		return userInfo;
	}

	public Map<String,Object> generateToken() {
		Map<String,Object> map=new HashMap<String,Object>();
		//获取token
		String token=null;
		token=(String) redisService.get("token");
		if(token==null) {
			String uuid=UUID.randomUUID().toString().replace("-", "");
			//设置token失效时间（24小时）
			redisService.set("token", uuid,new Long(60 * 60 * 24));
			map.put("token",uuid);
		}else {
			map.put("token",token);
		}
		return map;
	}

	public Map<String, Object> findSysparam() {
		Map<String,Object> map=null;
		if(redisService.get("Sysparam")==null) {
			 map=initDao.findSysparam();
			redisService.set("Sysparam", map);
		}else {
			
			map=(Map<String, Object>) redisService.get("Sysparam");
//			//判断版本号与缓存版本号是否一致，不一致查询数据库更新缓存，一致则不更新
//			if(sdk.contains("iPhone") || sdk.contains("iOS") || sdk.contains("iPad")) {
//				if(!version.equals(map.get("app_ios_upgrade_version_view"))) {
//					map=initDao.findSysparam();
//					redisService.set("Sysparam", map);
//				}
//			}else {
//				if(!version.equals(map.get("app_android_upgrade_version"))) {
//					map=initDao.findSysparam();
//					redisService.set("Sysparam", map);
//				}
//			}
		}
		// TODO Auto-generated method stub
		return map;
	}

	public void refreshCache() {
		Map<String,Object> map=initDao.findSysparam();
		redisService.set("Sysparam", map);
	}
	
}
