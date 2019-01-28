package snod.com.cn.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息实体
 * @author lvjj
 */
public class UserInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;//主键
	
    private String userName;//用户名称

    private String userNameEn;//用户英文名

    private Integer sex;//性别

    private String nickname;//昵称

    private String headPortrait;//头像
    
    private String companyName;//公司
    
    private String department;//部门
    
    private String phoneNum;//手机号
    
    private String positionName;//职称
    
    private String voiceprint;//声纹
    
    
    private List<AccountInfo> accountInfo;//账号信息

    
	
    
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getVoiceprint() {
		return voiceprint;
	}

	public void setVoiceprint(String voiceprint) {
		this.voiceprint = voiceprint;
	}

	public List<AccountInfo> getAccountInfo() {
		return accountInfo;
	}


	public void setAccountInfo(List<AccountInfo> accountInfo) {
		this.accountInfo = accountInfo;
	}

	public Integer getId() {
        return id;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNameEn() {
		return userNameEn;
	}

	public void setUserNameEn(String userNameEn) {
		this.userNameEn = userNameEn;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public void setId(Integer id) {
		this.id = id;
	}


    
}