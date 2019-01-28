package snod.com.cn.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snod.com.cn.basic.redis.RedisService;
import snod.com.cn.dao.UserDao;
import snod.com.cn.entity.UserInfo;
/**
 * 用户相关数据库操作实现类
 * @author lvjj
 * @since 2018年11月30日
 */
@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RedisService redisService;
//	@Autowired
//	private UserInfoRepository userInfoDao;
	

	public UserInfo findAll(int userId) {
		return userDao.selectByExample(userId);
	}
	
	

	public UserInfo findUserInfo(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectByPrimaryKey(userId);
	}



	public Map<String, Object> editUserInfo(int userId, String userName, int sex, String companyName, String department,
			String positionName,String phoneNum,String voiceprint) {
		Map<String,Object> result=new HashMap<String,Object>();
		UserInfo userInfo=new UserInfo();
		userInfo.setCompanyName(companyName);
		userInfo.setDepartment(department);
		userInfo.setPhoneNum(phoneNum);
		userInfo.setId(userId);
		userInfo.setUserName(userName);
		userInfo.setSex(sex);
		userInfo.setPositionName(positionName);
		userInfo.setVoiceprint(voiceprint);
		// TODO Auto-generated method stub
		//修改用户信息
		int code=userDao.editUserInfo(userInfo);
		//更新缓存
		if(redisService.get(userInfo.getId()+"")!=null) {
			UserInfo CacheUserInfo=(UserInfo) redisService.get(userInfo.getId()+"");
			CacheUserInfo.setCompanyName(userInfo.getCompanyName());
			CacheUserInfo.setUserName(userInfo.getUserName());
			CacheUserInfo.setSex(userInfo.getSex());
			CacheUserInfo.setPositionName(userInfo.getPositionName());
			CacheUserInfo.setVoiceprint(userInfo.getVoiceprint());
			CacheUserInfo.setDepartment(userInfo.getDepartment());
			CacheUserInfo.setPhoneNum(userInfo.getPhoneNum());
			redisService.set(CacheUserInfo.getId()+"", CacheUserInfo);
		}
		result.put("code", code);
		return result;
	}

//	public List<UserInfo> userSearch(String name) {
//		UserInfo userInfo=userDao.selectByExample(1);
//		userInfoDao.save(userInfo);
//		QueryStringQueryBuilder builder = new QueryStringQueryBuilder(name);
//		Iterable<UserInfo> searchResult = userInfoDao.search(builder);
//		Iterator<UserInfo> iterator = searchResult.iterator();
//		List<UserInfo> list = new ArrayList<UserInfo>();
//		while (iterator.hasNext()) {
//			list.add(iterator.next());
//		}
//		return list;
//	}
	
	

}
