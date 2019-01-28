package snod.com.cn.entity;

import java.io.Serializable;
/**
 * 用户角色关联关系实体
 * @author lvjj
 */
public class RoleUser implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;//主键
	private int userId;//用户id
	private int roleId;//角色id
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
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	
}
