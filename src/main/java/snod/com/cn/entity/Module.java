package snod.com.cn.entity;

import java.io.Serializable;
import java.util.Set;
/**
 * 功能菜单实体
 * @author lvjj
 */
public class Module implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;//主键
	private String moduleName;//菜单名称
	private int moduleParent;//菜单父ID
	private String moduleUrl;//树的连接路径
	private int status;//状态（0，禁用，1，可用）
	private Set<Role> roles;
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public int getModuleParent() {
		return moduleParent;
	}
	public void setModuleParent(int moduleParent) {
		this.moduleParent = moduleParent;
	}
	public String getModuleUrl() {
		return moduleUrl;
	}
	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
	
	
}
