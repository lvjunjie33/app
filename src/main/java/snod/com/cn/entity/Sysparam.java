package snod.com.cn.entity;

import java.io.Serializable;
/**
 * 系统信息实体
 * @author lvjj
 */
public class Sysparam implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;//主键
	private int app_ios_upgrade_version;//IOS 升级版本
	private int app_android_upgrade_version;//Android 升级版本
	private int app_ios_upgrade_version_view;//IOS 升级版本（显示给用户看的版本）
	private int app_android_upgrade_version_view;//Android 升级版本（显示给用户看的版本）
	private String app_ios_upgrade_version_introduction;//IOS 升级版本介绍
	private String app_android_upgrade_version_introduction;//Android 升级版本介绍
	private String upgrade_content;//app升级提示的内容
	private int is_new;//是否最新版本（0，否；1，是）
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApp_ios_upgrade_version() {
		return app_ios_upgrade_version;
	}
	public void setApp_ios_upgrade_version(int app_ios_upgrade_version) {
		this.app_ios_upgrade_version = app_ios_upgrade_version;
	}
	public int getApp_android_upgrade_version() {
		return app_android_upgrade_version;
	}
	public void setApp_android_upgrade_version(int app_android_upgrade_version) {
		this.app_android_upgrade_version = app_android_upgrade_version;
	}
	public int getApp_ios_upgrade_version_view() {
		return app_ios_upgrade_version_view;
	}
	public void setApp_ios_upgrade_version_view(int app_ios_upgrade_version_view) {
		this.app_ios_upgrade_version_view = app_ios_upgrade_version_view;
	}
	public int getApp_android_upgrade_version_view() {
		return app_android_upgrade_version_view;
	}
	public void setApp_android_upgrade_version_view(int app_android_upgrade_version_view) {
		this.app_android_upgrade_version_view = app_android_upgrade_version_view;
	}
	public String getApp_ios_upgrade_version_introduction() {
		return app_ios_upgrade_version_introduction;
	}
	public void setApp_ios_upgrade_version_introduction(String app_ios_upgrade_version_introduction) {
		this.app_ios_upgrade_version_introduction = app_ios_upgrade_version_introduction;
	}
	public String getApp_android_upgrade_version_introduction() {
		return app_android_upgrade_version_introduction;
	}
	public void setApp_android_upgrade_version_introduction(String app_android_upgrade_version_introduction) {
		this.app_android_upgrade_version_introduction = app_android_upgrade_version_introduction;
	}
	public String getUpgrade_content() {
		return upgrade_content;
	}
	public void setUpgrade_content(String upgrade_content) {
		this.upgrade_content = upgrade_content;
	}
	public int getIs_new() {
		return is_new;
	}
	public void setIs_new(int is_new) {
		this.is_new = is_new;
	}
	
	
	
}
