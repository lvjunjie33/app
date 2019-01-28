package snod.com.cn.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import snod.com.cn.entity.AccountInfo;
import snod.com.cn.entity.UserInfo;

public interface LoginDao {
	
	public UserInfo loginEmail(@Param("email") String email,@Param("passwd") String passwd);

   	public int queryAccountInfo(String email);
   

	public int emailRegister(AccountInfo ac);

	public int updatePasswd(@Param("email")String email, @Param("passwdNew")String passwdNew, @Param("passwdOld")String passwdOld);

	public AccountInfo queryAccount(String email);

	public int addUserInfo(UserInfo userInfo);
	













	




	




	




	
}
