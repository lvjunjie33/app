package snod.com.cn.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import snod.com.cn.basic.redis.RedisService;
import snod.com.cn.dao.ContactsDao;
import snod.com.cn.dao.FileDao;
import snod.com.cn.entity.ContactsInfo;
import snod.com.cn.entity.UserInfo;

@Service
public class ContactsService {
	@Autowired
	private ContactsDao contactsDao;
	@Autowired
	private RedisService redisService;
	
	@Transactional
	public Map<String, Object> addContacts(int userId, List<Integer> contactsUsers) {
		Map<String,Object> resultData=new HashMap<String,Object>();
		UserInfo userInfo=(UserInfo)redisService.get(userId+"");
		if(userInfo==null) {
			resultData.put("code",2);
			return resultData;
		}
		ContactsInfo contactsInfo=null;
		for(int i=0;i<contactsUsers.size();i++) {
			contactsInfo=new ContactsInfo();
			contactsInfo.setUserId(userId);
			contactsInfo.setContactsId(contactsUsers.get(i));
			contactsDao.addContacts(contactsInfo);
		}
		resultData.put("code", 1);
		return resultData;
	}

	public List<ContactsInfo> queryContacts(int userId) {
		
		return contactsDao.queryContacts(userId);
	}
}
