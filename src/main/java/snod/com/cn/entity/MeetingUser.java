package snod.com.cn.entity;

import java.io.Serializable;

/**
 * 参会人员实体
 * @author lvjj
 * */
public class MeetingUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;//主键
	private int userId;//用户ID
	private int meetingId;//会议ID
	private UserInfo userInfo;//用户信息
	private MeetingInfo meetingInfo;//会议信息
	
	
	public MeetingInfo getMeetingInfo() {
		return meetingInfo;
	}
	public void setMeetingInfo(MeetingInfo meetingInfo) {
		this.meetingInfo = meetingInfo;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}
	
}
