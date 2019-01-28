package snod.com.cn.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import snod.com.cn.basic.redis.RedisService;
import snod.com.cn.dao.FileDao;
import snod.com.cn.dao.UserDao;
import snod.com.cn.entity.UserInfo;

@Service
public class FileService {
	@Autowired
	private FileDao fileDao;
	@Autowired
	private RedisService redisService;
	@Autowired
	private UserDao userDao;

	public Map<String,Object> saveFile(String filename, int uid) {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		UserInfo uinfo=(UserInfo)redisService.get(uid+"");	
		if(uinfo!=null) {
			uinfo.setHeadPortrait(filename);
			//更新用户信息头像
			userDao.updateUserInfo(uinfo);
			//更新缓存
			redisService.set(uinfo.getId()+"", uinfo);
			result.put("code", 1);
			return result;
		}else {
			result.put("code", 0);
			return result;
		}
	
	}

	public UserInfo queryUserInfo(int uid) {
		return (UserInfo)redisService.get(uid+"");
	}
}
