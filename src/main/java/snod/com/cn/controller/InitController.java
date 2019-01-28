package snod.com.cn.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import snod.com.cn.service.InitService;

@Api(tags= {"初始化接口"})
@RestController
@RequestMapping("/init")
public class InitController {
	private final static Logger logger = LoggerFactory.getLogger(InitController.class);
	
//	@Autowired
//	private UserService userService;
	@Autowired
	private InitService initService;
    /**
     * app 初始化信息
     */
	@RequestMapping("/base")
	@ApiOperation(value="初始化接口",notes="初始化接口",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户ID",paramType="query",dataType="int")
	})
    public ResultInfo init(HttpServletRequest request, int userId) throws CloneNotSupportedException {
		ResultInfo resultInfo=fitMixInit(request,userId);
		return resultInfo;
//        String product = request.getParameter("_key");
//        if (UserDevice.KEY.equals(product)) {
//        	return fitMixInit(request, model, lan);
//        } else {
//        	return ResultTools.result(ResultTools.ERROR_CODE_4006,null,null);
//        }
         
    }
	public ResultInfo fitMixInit(HttpServletRequest request, int userId) {
		try {
			Map<String,Object> result=new HashMap<String,Object>();
			//获取token并设置失效时间
			Map<String,Object> map=initService.generateToken();
			//获取系统信息
			Map<String,Object> mapSysparam=initService.findSysparam();
			result.put("token", map);
			result.put("mapSysparam", mapSysparam);
//			Map<String,Object> userinfo=(Map<String, Object>) redisService.get(userId+"");
//			map.put("user_name", userinfo.get("user_name"));
//			logger.debug(null,new Exception());
			return ResultTools.result(ResultTools.ERROR_CODE_0, null, result);
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
		}
		
		
	}
	
	
	 /**
     * 刷新缓存
     */
	@RequestMapping("/refresh")
	@ApiOperation(notes="刷新缓存",value="刷新缓存",httpMethod="POST")
    public ResultInfo refresh(HttpServletRequest request) throws CloneNotSupportedException {
		try {
			initService.refreshCache();
			return ResultTools.result(ResultTools.ERROR_CODE_0, null, null);
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
		}
         
    }
}
