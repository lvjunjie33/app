package snod.com.cn.dao;

import java.util.List;
import java.util.Map;

import snod.com.cn.entity.MeetingDetail;
import snod.com.cn.entity.MeetingInfo;
import snod.com.cn.entity.MeetingUser;

public interface MeetingDao {

	public void createMeeting(MeetingInfo meetingInfo);

	public void createMeetingDetail(MeetingDetail meetingDetail);

	public void createMeetingUser(MeetingUser meetingUser);

	public void cancelMeeting(Map<String, Object> parameter);
	
	public MeetingInfo querymeetingStatus(String meetingCode);

	public List<MeetingInfo> queryMeetingList(int userId);

	public List<MeetingUser> meetingQueryUsers(String meetingCode);




}
