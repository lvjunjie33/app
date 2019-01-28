package snod.com.cn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import snod.com.cn.EcpStackTrace;
import snod.com.cn.ResultInfo;
import snod.com.cn.ResultTools;
import snod.com.cn.entity.MeetingInfo;
import snod.com.cn.entity.MeetingUser;
import snod.com.cn.service.MeetingService;

@Api(tags= {"会议相关接口"})
@RestController
@RequestMapping("/business")
public class MeetingController {
	private final static Logger logger = LoggerFactory.getLogger(MeetingController.class);
	
	
	@Autowired
	private MeetingService meetingService;

	
	
	/**
	 * 创建会议
	 * @param user 用户对象
	 */
	@RequestMapping("/meeting/create")
	@ApiOperation(notes="创建会议",value="创建会议",httpMethod="POST")
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户ID",paramType="query",dataType="int",required=true),
		@ApiImplicitParam(name="meetingName",value="会议主题",paramType="query",dataType="string"),
		@ApiImplicitParam(name="meetingAddress",value="会议地点",paramType="query",dataType="string"),
		@ApiImplicitParam(name="printLan",value="输入语言（语言切换1，中文；2，英文；）",paramType="query",dataType="int"),
		@ApiImplicitParam(name="meetingPasswd",value="会议密码",paramType="query",dataType="string"),
		@ApiImplicitParam(name="meetingpreStartTime",value="预计开始时间",paramType="query",dataType="long"),
	})
	public ResultInfo createMeeting(HttpServletRequest request, int userId,String meetingName,
			String meetingAddress,int printLan,String meetingPasswd,
			@RequestParam(value = "meetingUsers")@ApiParam(value = "参会人员id集合") List<Integer> meetingUsers,long meetingpreStartTime){
		try {
			Map<String,Object>result=new HashMap<String,Object>();
			Map<String,Object> resultData=meetingService.createMeeting(userId,meetingName,meetingAddress,printLan,meetingPasswd,meetingUsers,meetingpreStartTime);
			if((int)resultData.get("code")==2) {
				return ResultTools.result(ResultTools.ERROR_CODE_2011, null,null);
			}
			if((int)resultData.get("code")==1) {
				MeetingInfo meetingInfo=(MeetingInfo)resultData.get("meetingInfo");
				result.put("meetingInfo", meetingInfo);
			}
			return ResultTools.result(ResultTools.ERROR_CODE_0, null,result);
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
		}
	}

	/**
	 * 取消会议
	 * @param userId 用户ID
	 */
	@RequestMapping("/meeting/cancel")
	@ApiOperation(notes="取消会议",value="取消会议",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="meetingCode",value="会议ID",paramType="query",dataType="string",required=true),
		@ApiImplicitParam(name="userId",value="会议创建人ID",paramType="query",dataType="int",required=true),
	})
	public ResultInfo cancelMeeting(HttpServletRequest request, String meetingCode,int userId){
		try {
			Map<String,Object> resultData=meetingService.cancelMeeting(meetingCode,userId);
			if(resultData.get("code")!=null) {
				//会议ID不存在
				if((int)resultData.get("code")==1) {
					return ResultTools.result(ResultTools.ERROR_CODE_5001, null,null);
				}
				//该用户不是会议创建者，没权限取消会议
				if((int)resultData.get("code")==2) {
					return ResultTools.result(ResultTools.ERROR_CODE_5002, null,null);
				}
				//取消会议失败，只能取消状态为“已创建”的会议
				if((int)resultData.get("code")==3) {
					return ResultTools.result(ResultTools.ERROR_CODE_5003, null,null);
				}
			}
			return ResultTools.result(ResultTools.ERROR_CODE_0, null,null);
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
		}
		
	}
	
	
	/**
	 * 获取会议列表
	 * @param userId 用户ID
	 */
	@RequestMapping("/meeting/query")
	@ApiOperation(notes="获取会议列表",value="获取会议列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户ID",paramType="query",dataType="int")
	})
	public ResultInfo queryMeetingList(HttpServletRequest request, int userId){
		try {
			Map<String,Object>result=new HashMap<String,Object>();
			//1.获取该用户所创建的会议
			//2.状态为“已创建”，“已开始”，“已暂停”，“已结束”
			List<MeetingInfo> meetingInfos=meetingService.queryMeetingList(userId);
			result.put("meetingInfoList", meetingInfos);
			return ResultTools.result(ResultTools.ERROR_CODE_0, null,result);
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
		}
	}
	
	/**
	 * 开始会议
	 * @param userId 用户ID
	 */
	@RequestMapping("/meeting/start")
	@ApiOperation(notes="开始会议",value="开始会议",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="meetingCode",value="会议ID",paramType="query",dataType="string"),
		@ApiImplicitParam(name="userId",value="会议创建人ID",paramType="query",dataType="int"),
	})
	public String startMeeting(HttpServletRequest request, int userId){
		return "hello ";
	}
	
	
	/**
	 * 结束会议
	 * @param userId 用户ID
	 */
	@RequestMapping("/meeting/end")
	@ApiOperation(notes="结束会议",value="结束会议",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="meetingCode",value="会议ID",paramType="query",dataType="string"),
		@ApiImplicitParam(name="userId",value="会议创建人ID",paramType="query",dataType="int"),
	})
	public String endMeeting(HttpServletRequest request, int userId){
		return "hello ";
	}
	
	/**
	 * 邀请联系人
	 * @param userId 用户ID
	 */
	@RequestMapping("/meeting/invitation")
	@ApiOperation(notes="邀请联系人",value="邀请联系人",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="meetingCode",value="会议ID",paramType="query",dataType="string"),
		@ApiImplicitParam(name="userIdArray",value="用户ID集合",paramType="query",dataType="int")
	})
	public String meetingInvitation(HttpServletRequest request, String meetingCode){
		return "hello ";
	}
	
	
	
	/**
	 * 获取参会人员列表
	 * @param userId 用户ID
	 */
	@RequestMapping("/meeting/query/users")
	@ApiOperation(notes="获取参会人员列表",value="获取参会人员列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="meetingCode",value="会议ID",paramType="query",dataType="string"),
	})
	public ResultInfo meetingQueryUsers(HttpServletRequest request, String meetingCode){
		try {
			Map<String,Object>result=new HashMap<String,Object>();
			List<MeetingUser> meetingUserList=meetingService.meetingQueryUsers(meetingCode);
			result.put("meetingUserList", meetingUserList);
			return ResultTools.result(ResultTools.ERROR_CODE_0, null,result);
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
		}
	}
	
	/**
	 * 会议内容和翻译
	 * @param userId 用户ID
	 */
	@RequestMapping("/meeting/contentTranslate")
	@ApiOperation(notes="会议内容和翻译",value="获取参会人员列表",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="meetingCode",value="会议ID",paramType="query",dataType="string"),
		@ApiImplicitParam(name="meetingContent",value="会议内容",paramType="query",dataType="string"),
		@ApiImplicitParam(name="meetingTranslate",value="会议翻译",paramType="query",dataType="string"),
	})
	public ResultInfo meetingContentTranslate(HttpServletRequest request, String meetingCode,String meetingContent,String meetingTranslate){
		try {
				
			return ResultTools.result(ResultTools.ERROR_CODE_0, null,null);
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
		}
	}
}
