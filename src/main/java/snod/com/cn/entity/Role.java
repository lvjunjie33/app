package snod.com.cn.entity;

import java.io.Serializable;
import java.util.Set;
/**
 *  角色实体
 * @author lvjj
 */
public class Role implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;//主键
	private String roleName;//角色名称
	private int status;//状态（0，禁用；1，可用）
	private Set<Module> module;
	private Set<UserInfo> userInfos;
	
	
	public Set<UserInfo> getUserInfos() {
		return userInfos;
	}
	public void setUserInfos(Set<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}
	public Set<Module> getModule() {
		return module;
	}
	public void setModule(Set<Module> module) {
		this.module = module;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
	
	
}
