package snod.com.cn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import snod.com.cn.EcpStackTrace;
import snod.com.cn.ResultInfo;
import snod.com.cn.ResultTools;
import snod.com.cn.entity.UserInfo;
import snod.com.cn.service.FileService;

@Api(tags= {"文件相关接口"})
@RestController
@RequestMapping("/business")
public class FileController {
	private final static Logger logger = LoggerFactory.getLogger(InitController.class);

	@Autowired
	private FileService fileService;
	 /**
     * 上传头像
     * @param file
     * @return
     * @throws IOException
     */
	@RequestMapping(value="/file/upload/headPortrait",method= RequestMethod.POST,headers ="content-type=multipart/form-data")
	@ApiOperation(notes="上传头像",value="上传头像",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户ID",paramType="query",dataType="int",required=true),
	})
    public ResultInfo fileUploadHeadPortrait(@ApiParam(value ="上传头像",required=true)MultipartFile file,HttpServletRequest request,int userId) throws IOException {
		try {
			String folder = request.getServletContext().getRealPath("/WEB-INF/file/HeadPortrait");
			//文件夹为空，创建文件夹
			if(!new File(folder).exists()){       
				new File(folder).mkdirs();    
	        }
	        /**
	         * 注意：file名字要和参入的name一致
	         */
	
	        System.out.println("filename=" + file.getName());
	        System.out.println("origin filename=" + file.getOriginalFilename());
	        System.out.println("filesize=" + file.getSize());
	    
	        String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
	        String filename= new Date().getTime() +"."+prefix;
	        Map<String,Object> result=fileService.saveFile(filename,userId);
	        if((int)result.get("code")==0) {
	        	return ResultTools.result(ResultTools.ERROR_CODE_2011, null,null);
	        }
	        
	        /**
	         * 这里是写到本地
	     * 还可以用file.getInputStrem()
	         * 获取输入流，然后存到阿里oss。。或七牛。。
	         */
	        File localFile = new File(folder,filename);
	        
	        //把传入的文件写到本地文件
	        file.transferTo(localFile);
	        return ResultTools.result(ResultTools.ERROR_CODE_0, null,null);
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
		}
    }

	
	 /**
     * 头像下载
	 * @throws UnsupportedEncodingException 
   */
	@RequestMapping("/file/download/headPortrait")
	@ApiOperation(notes="头像下载",value="头像下载",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户ID",paramType="query",dataType="int",required=true),
	})
    public ResultInfo downloadHeadPortrait(HttpServletRequest request, HttpServletResponse response,int userId) throws UnsupportedEncodingException {
		String folder = request.
                getServletContext().getRealPath("/WEB-INF/file/HeadPortrait");
		UserInfo userInfo=fileService.queryUserInfo(userId);
        try (
         //jdk7新特性，可以直接写到try()括号里面，java会自动关闭
         InputStream inputStream = new FileInputStream(new File(folder,userInfo.getHeadPortrait()));
         OutputStream outputStream = response.getOutputStream()
        ) {
            //指明为下载
            response.setContentType("application/x-download");
            String fileName =userInfo.getHeadPortrait();
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);   // 设置文件名
            //把输入流copy到输出流
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
            return ResultTools.result(ResultTools.ERROR_CODE_0, null,null);
        } catch (FileNotFoundException e) {
        	logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
        } catch (IOException e) {
        	logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
        }
	}
	
	 /**
     * 上传会议文件
     * @param file
     * @return
     * @throws IOException
     */
	@RequestMapping(value="/file/upload/meeting",method= RequestMethod.POST,headers = "content-type=multipart/form-data")
	@ApiOperation(notes="上传会议文件",value="上传会议文件",httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name="meetingId",paramType="query",dataType="int"),
	})
//	public ResultInfo fileUploadMeeting(@ApiParam(value ="会议内容文件",required=true)MultipartFile fileContent,@ApiParam(value ="会议翻译文件",required=true)MultipartFile fileTranslation,HttpServletRequest request,int meetingId) throws IOException {
	public ResultInfo fileUploadMeeting(HttpServletRequest request,int meetingId) throws IOException {
		try {
			String folder = request.getServletContext().getRealPath("/WEB-INF/file/meeting");
			if(!new File(folder).exists()){       
				new File(folder).mkdirs();    
	        }
	        /**
	         * 注意：file名字要和参入的name一致
	         */
			 Map<String, MultipartFile> fileMap = new LinkedHashMap<>(0);
		     if (request instanceof MultipartHttpServletRequest) {
		          fileMap = ((MultipartHttpServletRequest) request).getFileMap();
		      }
		    MultipartFile fileConten=fileMap.get("content");
		    MultipartFile fileTranslatio=fileMap.get("translation");

	        String prefixContent = fileConten.getOriginalFilename().substring(fileConten.getOriginalFilename().lastIndexOf('.') + 1);
	        String filenameContent= new Date().getTime() +"."+prefixContent;
	        String prefixTranslatio = fileTranslatio.getOriginalFilename().substring(fileTranslatio.getOriginalFilename().lastIndexOf('.') + 1);
	        String filenameTranslatio= new Date().getTime() +"."+prefixTranslatio;
	//        fileService.saveFile(file,uid);
	
	        /**
	         * 这里是写到本地
	     * 还可以用file.getInputStrem()
	         * 获取输入流，然后存到阿里oss。。或七牛。。
	         */
	        File localFileContent = new File(folder, filenameContent);
	        File localFileTranslation = new File(folder, filenameTranslatio);
	        //把传入的文件写到本地文件
	        fileConten.transferTo(localFileContent);
	        fileTranslatio.transferTo(localFileTranslation);
	        return ResultTools.result(ResultTools.ERROR_CODE_0, null,null);
		}catch(Exception e) {
			logger.error(EcpStackTrace.getExceptionStackTrace(e));
			return ResultTools.result(ResultTools.ERROR_CODE_404, EcpStackTrace.getExceptionStackTrace(e), null);
		}
    }
}
