package snod.com.cn.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * 极光推送配置信息
 *
 * @author lvjj 
 */
@Component("jpushConfig")
public class JpushConfig {
	 // 读取极光配置信息中的用户名密码
    @Value("${jpush.appKey}")
    private String appkey;
    @Value("${jpush.masterSecret}")
    private String masterSecret;
    @Value("${jpush.liveTime}")
    private String liveTime;

    public String getAppkey() {

        return appkey;
    }

    public String getMasterSecret() {

        return masterSecret;
    }

    public void setLiveTime(String liveTime) {

        this.liveTime = liveTime;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

	public String getLiveTime() {
		return liveTime;
	}
    
    
}
