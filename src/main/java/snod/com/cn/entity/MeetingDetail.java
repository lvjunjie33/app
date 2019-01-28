package snod.com.cn.entity;

import java.io.Serializable;
/**
 * 会议记录表实体
 * @author lvjj
 * */
public class MeetingDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;//主键
	private int meetingId;//会议ID
	private String deviceNum;//设备编号
	private String meetingContent;//会议内容
	private String meetingTranslate;//会议翻译
	private String meetingContentFile;//会议内容文件
	private String meetingTranslateFile;//会议翻译文件

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}
	public String getDeviceNum() {
		return deviceNum;
	}
	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}
	public String getMeetingContent() {
		return meetingContent;
	}
	public void setMeetingContent(String meetingContent) {
		this.meetingContent = meetingContent;
	}
	public String getMeetingTranslate() {
		return meetingTranslate;
	}
	public void setMeetingTranslate(String meetingTranslate) {
		this.meetingTranslate = meetingTranslate;
	}
	public String getMeetingContentFile() {
		return meetingContentFile;
	}
	public void setMeetingContentFile(String meetingContentFile) {
		this.meetingContentFile = meetingContentFile;
	}
	public String getMeetingTranslateFile() {
		return meetingTranslateFile;
	}
	public void setMeetingTranslateFile(String meetingTranslateFile) {
		this.meetingTranslateFile = meetingTranslateFile;
	}

	
	

}
