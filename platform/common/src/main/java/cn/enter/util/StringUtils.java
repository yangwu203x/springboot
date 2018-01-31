package cn.enter.util;


import cn.enter.util.dates.FormatVerify;
import org.apache.commons.io.FilenameUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具类
 *
 */
public class StringUtils {

	public static String getRandom(int length){
			String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
			Random rand = new Random();
			StringBuffer flag = new StringBuffer();
			for (int j = 0; j < length; j++)
			{
				flag.append(sources.charAt(rand.nextInt(9)) + "");
			}
			System.out.println(flag.toString());
			return flag.toString();
	}

	/**
	 * 判断是否是数值
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		if(isNull(str)){
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}

	/**
	 * 手机号或姓名一部用*号代替
	 * @param str
	 * @return
	 */
	public static String hideMsg(String str){
		if(isNull(str)){
			return null;
		}
		if(FormatVerify.isPhone(str.trim())){
			str = str.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		}else{
			str= str.replaceAll("(.{1}).+", "$1**");
		}
		return str;
	}
	
	/**
	 * 不为空，为空返回false，否则返回true
	 * @param str 字符串
	 * @return
	 */
	public static boolean isNotNull(String str){
		if(str == null || str.trim().equals("") || str.trim().equals("null")){
			return false;
		}
		return true;
	}
	
	/**
	 * 为空，为空返回true，否则返回false
	 * @param str 字符串
	 * @return
	 */
	public static boolean isNull(String str){
		if(str == null || str.trim().equals("") || str.trim().equals("null")){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 乱码转换
	 * @param str 乱码串
	 * @param pattern1 编码方式
	 * @param pattern2 编码方式2
	 * @return 转换后的字符串
	 * @throws Exception
	 */
	public static String strDecode(String str, String pattern1, String pattern2) throws Exception{
		if(!isNotNull(str)){
			return "";
		}
		
		return new String(str.getBytes(pattern1), pattern2);
	}
	


	/**
	 * 将传入的字符串加密成32位的字符串进行返回
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return 32位密文
	 */
	public static String MD5(String str) {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}

			return buf.toString().toUpperCase();

		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		
	}

	public static void mapDelNullValue(List<Map> list){
		if(list!=null&&!list.isEmpty()){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				Set keySet = map.keySet();
				Iterator iterator2 = keySet.iterator();
				while(iterator2.hasNext()){
					Object object = iterator2.next();
					Object object2 = map.get(object);
					if(object2==null){
						iterator2.remove();
					}
				}
			}
		}
	}
	
	public static String replaceJsonEncode(String str){
		if(isNotNull(str)){
			str = str.replaceAll("\\+", "-");
			str = str.replaceAll("/", "_");
		}
		return str;
	}
	
	public static String replaceJsonDecode(String str){
		if(isNotNull(str)){
			str = str.replaceAll("-", "+");
			str = str.replaceAll("_", "/");
		}
		return str;
	}
	
	public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!notisEmojiCharacter(codePoint)) {
            //判断确认有表情字符
           return true;
            }
        }
        return false;
    }


    /**
     * 非emoji表情字符判断
    * @param codePoint
     * @return
     */
    private static boolean notisEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || 
                (codePoint == 0x9) ||                            
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
    
    /**
     * 过滤emoji 或者 其他非文字类型的字符
    * @param source  需要过滤的字符串
    * @return
     */
    public static String filterEmoji(String source) {
        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
       }
        
        StringBuilder buf = null;//该buf保存非emoji的字符
       int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (notisEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            } 
        }
        
        if (buf == null) {         
            return "";//如果没有找到非emoji的字符，则返回无内容的字符串
       } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }
    
    public static boolean listIsNull(List list){
    	if(list==null||list.isEmpty()){
    		return true;
    	}
    	return false;
    }
	
    public static String subFileSuffix(String fileName){
    	 String extension = FilenameUtils.getExtension(fileName);
        return extension;
    }
    
    
    public static boolean isNullLong(Long l){
    	if(l==null||l==0){
    		return true;
    	}
    	return false;
    }
    
    public static boolean isNulInteger(Integer value){
    	if(value==null||value==0){
    		return true;
    	}
    	return false;
    }
    

}
