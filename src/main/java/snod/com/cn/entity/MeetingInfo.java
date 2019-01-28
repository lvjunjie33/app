package snod.com.cn.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 会议信息实体
 * @author lvjj
 * */
public class MeetingInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;//主键
	private String meetingCode;//会议Id
	private String meetingName;//会议主题
	private Date meetingStartDate;//会议开始时间
	private Date meetingEndDate;//会议结束时间
	private String meetingAddress;//会议地点
	private int printLan;//输入语言（语言切换1，中文；2，英文；）
	private String meetingPasswd;//会议密码
	private int status;//状态（1，已创建；2，已开始；3，已暂停；4，已结束；5，已取消；）
	private Date createTime;//创建时间
	private int createUserId;//创建人id
	private String createUserName;//创建人名称
	private long meetingpreStartTime;//预计开始时间
	private List<MeetingDetail> meetingDetail;//会议记录信息

	public List<MeetingDetail> getMeetingDetail() {
		return meetingDetail;
	}
	public void setMeetingDetail(List<MeetingDetail> meetingDetail) {
		this.meetingDetail = meetingDetail;
	}
	
	public long getMeetingpreStartTime() {
		return meetingpreStartTime;
	}
	public void setMeetingpreStartTime(long meetingpreStartTime) {
		this.meetingpreStartTime = meetingpreStartTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMeetingCode() {
		return meetingCode;
	}
	public void setMeetingCode(String meetingCode) {
		this.meetingCode = meetingCode;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	public Date getMeetingStartDate() {
		return meetingStartDate;
	}
	public void setMeetingStartDate(Date meetingStartDate) {
		this.meetingStartDate = meetingStartDate;
	}
	public Date getMeetingEndDate() {
		return meetingEndDate;
	}
	public void setMeetingEndDate(Date meetingEndDate) {
		this.meetingEndDate = meetingEndDate;
	}
	public String getMeetingAddress() {
		return meetingAddress;
	}
	public void setMeetingAddress(String meetingAddress) {
		this.meetingAddress = meetingAddress;
	}
	public int getPrintLan() {
		return printLan;
	}
	public void setPrintLan(int printLan) {
		this.printLan = printLan;
	}
	public String getMeetingPasswd() {
		return meetingPasswd;
	}
	public void setMeetingPasswd(String meetingPasswd) {
		this.meetingPasswd = meetingPasswd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	
}
