package snod.com.cn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import snod.com.cn.EcpStackTrace;
import snod.com.cn.ResultInfo;
import snod.com.cn.ResultTools;
import snod.com.cn.entity.ContactsInfo;
import snod.com.cn.service.ContactsService;

@Api(tags= {"联系人相关接口"})
@RestController
@RequestMapping("/business")
public class ContactsController {
	private final static Logger logger = LoggerFactory.getLogger(ContactsController.class);
	
	
	@Autowired
	private ContactsService contactsService;

	
	
	/**
	 * 添加联系人
	 * @param userId 用户对象
	 * @param contactsUsers 联系人id集合
	 */
	@RequestMapping("/contacts/add")
	@ApiOperation(notes="添加联系人",value="添加联系人",httpMethod="POST")
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户ID",paramType="query",dataType="int",required=true),
	})
	public ResultInfo addContacts(HttpServletRequest request, int userId,
			@RequestParam(value = "contactsUsers")@ApiParam(value = "联系人id集合") List<Integer> contactsUsers){
		try {
			Map<String, Object> rsultData=contactsService.addContacts(userId,contactsUsers);
			if((int)rsultData.get("code")==2) {
				return ResultTools.result(ResultTools.ERROR_CODE_2011, null,null);
			}else {
				return ResultTools.result(ResultTools.ERROR_CODE_0, null,null);
			}
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
		}
	}

	/**
	 * 获取联系人列表
	 * @param userId 用户ID
	 */
	@RequestMapping("/contacts/query")
	@ApiOperation(notes="获取联系人列表",value="获取联系人列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户ID",paramType="query",dataType="int",required=true),
	})
	public ResultInfo queryContacts(HttpServletRequest request,int userId){
		try {
			Map<String,Object>result=new HashMap<String,Object>();
			List<ContactsInfo> contactsInfos=contactsService.queryContacts(userId);
			result.put("contactsInfos", contactsInfos);
			return ResultTools.result(ResultTools.ERROR_CODE_0, null,result);
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
