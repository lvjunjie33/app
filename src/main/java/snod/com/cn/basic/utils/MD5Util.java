package snod.com.cn.basic.utils;


import org.apache.commons.codec.digest.DigestUtils;  
/** 
 * MD5加密组件 
 *  
 * @author lvjj
 */  
public class MD5Util {
	 
    /** 
     * MD5加密 
     *  
     * @param data 
     *            待加密数据 
     * @return byte[] 消息摘要 
     *  
     * @throws Exception 
     */  
    public static byte[] encodeMD5(String data) throws Exception {  
  
        // 执行消息摘要  
        return DigestUtils.md5(data);  
    }  
  
    /** 
     * MD5加密 
     *  
     * @param data 
     *            待加密数据 
     * @return byte[] 消息摘要 
     *  
     * @throws Exception 
     */  
    public static String encodeMD5Hex(String data) {  
        // 执行消息摘要  
        return DigestUtils.md5Hex(data);  
    }  
    
    public static void main(String[] args) throws Exception {
		System.out.println(MD5Util.encodeMD5("123456"));
		System.out.println(MD5Util.encodeMD5Hex("654321"));
		
	}
}
