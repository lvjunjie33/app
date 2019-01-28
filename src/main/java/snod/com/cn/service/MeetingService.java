package snod.com.cn.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import snod.com.cn.basic.redis.RedisService;
import snod.com.cn.dao.MeetingDao;
import snod.com.cn.entity.MeetingDetail;
import snod.com.cn.entity.MeetingInfo;
import snod.com.cn.entity.MeetingUser;
import snod.com.cn.entity.UserInfo;

@Service
public class MeetingService {
	@Autowired
	private MeetingDao meetingDao;
	@Autowired
	private RedisService redisService;
	@Transactional
	public Map<String,Object> createMeeting(int userId, String meetingName, String meetingAddress, int printLan, String meetingPasswd, List<Integer> meetingUsers, long meetingpreStartTime) {
		MeetingInfo meetingInfo=new MeetingInfo();
		Map<String,Object> resultData=new HashMap<String,Object>();
		UserInfo userInfo=(UserInfo)redisService.get(userId+"");
		if(userInfo==null) {
			resultData.put("code",2);
			return resultData;
		}
		meetingInfo.setMeetingAddress(meetingAddress);
		meetingInfo.setMeetingName(meetingName);
		meetingInfo.setMeetingPasswd(meetingPasswd);
		meetingInfo.setPrintLan(printLan);
		meetingInfo.setStatus(1);
		String datestr="";
		//缓存中取号，30天有效期，到期从1开始
		if(redisService.get("code")==null) {
			datestr=new SimpleDateFormat("MM-dd").format(new Date()).replace("-", "")+"0001";
			redisService.set("code", datestr);
		}else {
			String code=(String) redisService.get("code");
			int intCode=Integer.parseInt(code);
			intCode++;
			//等于最大值重置
			if(intCode==99999999) {
				datestr=new SimpleDateFormat("MM-dd").format(new Date()).replace("-", "")+"0001";
			}else {
				datestr=intCode+"";
				if(datestr.length()<8) {
					datestr="0"+datestr;
				}
			}
			redisService.set("code", datestr);
		}
		meetingInfo.setMeetingCode(datestr);
		meetingInfo.setCreateUserId(userId);
		meetingInfo.setCreateUserName(userInfo.getUserName());
		meetingInfo.setCreateTime(new Date());
		meetingInfo.setMeetingpreStartTime(meetingpreStartTime);
		meetingDao.createMeeting(meetingInfo);
		MeetingDetail meetingDetail=new MeetingDetail();
		meetingDetail.setMeetingId(meetingInfo.getId());
		meetingDao.createMeetingDetail(meetingDetail);
		MeetingUser meetingUser=null;
		for(int i=0;i<meetingUsers.size();i++) {
			meetingUser=new MeetingUser();
			meetingUser.setMeetingId(meetingInfo.getId());
			meetingUser.setUserId(meetingUsers.get(i));
			meetingDao.createMeetingUser(meetingUser);
		}
		resultData.put("code", 1);
		resultData.put("meetingInfo",meetingInfo);
		return resultData;
	}
	public Map<String, Object> cancelMeeting(String meetingCode, int userId) {
		//1.根据会议ID查询会议是否存在
		//2.判断用户id是否是会议创建人
		//3.状态是否为已创建，其他状态不能取消会议
		Map<String,Object> result=new HashMap<String,Object>();
		MeetingInfo meetingInfo=meetingDao.querymeetingStatus(meetingCode);
		if(meetingInfo==null) {
			result.put("code", 1);
			return result;
		}else if(meetingInfo.getCreateUserId()!=userId){
			result.put("code", 2);
			return result;
		}else if(meetingInfo.getStatus()!=1) {
			result.put("code", 3);
			return result;
		}
		Map<String,Object> parameter=new HashMap<String,Object>();
		parameter.put("meetingCode", meetingCode);
		parameter.put("userId", userId);
		meetingDao.cancelMeeting(parameter);
		return result;
	}
	public List<MeetingInfo> queryMeetingList(int userId) {
		return meetingDao.queryMeetingList(userId);
	}
	public List<MeetingUser> meetingQueryUsers(String meetingCode) {
		// TODO Auto-generated method stub
		return meetingDao.meetingQueryUsers(meetingCode);
	}


}
