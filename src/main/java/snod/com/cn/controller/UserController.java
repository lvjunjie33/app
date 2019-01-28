package snod.com.cn.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import snod.com.cn.EcpStackTrace;
import snod.com.cn.ResultInfo;
import snod.com.cn.ResultTools;
import snod.com.cn.basic.redis.RedisService;
//import snod.com.cn.dao.es.UserInfoRepository;
import snod.com.cn.entity.UserInfo;
import snod.com.cn.service.UserService;

@Api(tags= {"用户相关的接口"})
@RestController
@RequestMapping("/business")
public class UserController {
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;

	/**
	 * 用户信息编辑
	 * @param user 用户对象
	 */
	@RequestMapping("/user/edit")
	@ApiOperation(notes="用户信息编辑",value="用户信息编辑",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户ID",paramType="query",dataType="int"),
		@ApiImplicitParam(name="userName",value="用户名称",paramType="query",dataType="string"),
		@ApiImplicitParam(name="sex",value="性别（1：男；2：女）",paramType="query",dataType="int"),
		@ApiImplicitParam(name="companyName",value="公司",paramType="query",dataType="string"),
		@ApiImplicitParam(name="department",value="部门",paramType="query",dataType="string"),
		@ApiImplicitParam(name="positionName",value="职称",paramType="query",dataType="string"),
		@ApiImplicitParam(name="phoneNum",value="手机号",paramType="query",dataType="string"),
		@ApiImplicitParam(name="voiceprint",value="声纹",paramType="query",dataType="string"),
		
	})
	public ResultInfo editUserInfo(int userId,String userName,int sex,String companyName,String department,
			String positionName,String phoneNum,String voiceprint){
		try {
			Map<String,Object> result=userService.editUserInfo(userId,userName,sex,companyName,department,
					positionName,phoneNum,voiceprint);
			if((int)result.get("code")>0) {
				return ResultTools.result(ResultTools.ERROR_CODE_0, null,null);
			}else {
				return ResultTools.result(ResultTools.ERROR_CODE_2011, null,null);
			}
		}catch(Exception e){
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e),null);
		}
	}
//	public String editUserInfo(@RequestBody @ApiParam(name="UserInfo",value="用户实体",required=true) UserInfo user,int userId){
//		return "hello ";
//	}
	
	/**
	 * 查询所有用户
	 * @return
	 */
	@RequestMapping("/user/all")
	@ApiOperation(notes="查询所有用户",value="查询所有，目前只返回数量",httpMethod="GET")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",paramType="query",dataType="int"),
	})
	public ResultInfo find(int userId){
		try {
		UserInfo listUser=userService.findAll(userId);
	
		redisService.set("test",listUser);
		UserInfo s=(UserInfo)redisService.get("test");
		System.out.println(s.getId()+""+s.getUserName());
		}catch(Exception e) {

			return ResultTools.result(ResultTools.ERROR_CODE_404, e.getMessage(),null);
		}
		
		return ResultTools.result(ResultTools.ERROR_CODE_0, "", null);
	}

//	/**
//	 * testes
//	 * @param user 用户对象
//	 */
//	@RequestMapping("/user/search")
//	@ApiOperation(notes="es测试",value="es测试",httpMethod="POST")
//	@ApiImplicitParams({
//		@ApiImplicitParam(name="name",paramType="query",dataType="string"),
//	})
//	public String userSearch(String name){
//		 List<UserInfo> list=userService.userSearch(name);
//		 System.out.println(list.size());
//		return "";
//	}
	

}
