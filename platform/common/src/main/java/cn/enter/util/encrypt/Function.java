package cn.enter.util.encrypt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


public class Function {
	
	private static Log log = LogFactory.getLog(Function.class);
	protected static char hexDigits[] = { '0', '1','2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f'};
	public Function() {
	}
    public static int ad=0;
	// 测试
//	public static void main(String arg[]) {
//	}

	/***************************************************************************
	 * 将长消息截断， 
	 * 
	 * 
	 */
	public static String cutStr(String content, int len) {
		if (content == null) {
			return "";
		} else if (content.length() <= len) {
			return content;
		} else {
			return content.substring(0, len) + "...";
		}

	}

	/***************************************************************************
	 * 截断空格符合
	 * 
	 * 
	 */
	public static String trim(String content) {
		if (content == null || content == "") {
			return "";
		} else if (content.length() > 0) {
			content = content.trim();
		}
		return content;
	}

	/***************************************************************************
	 * 将日期长度控制
	 * 
	 * 
	 */
	public static String cutDateStr(String dateTime, int len) {
		if (dateTime == null) {
			return "";
		} else if (dateTime.length() <= len) {
			return dateTime;
		} else {
			return dateTime.substring(0, len);
		}

	}

 
	public static int getInt(String str, int value) {
		int result = value;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return result;
	}
 

	public static BigDecimal getBigDecimal(String str) {
		BigDecimal result=null;
		try {
			if(null!=str && str.trim().length()>0){
				str=str.replace(",", "");
			}
			result = new BigDecimal(str).setScale(2,RoundingMode.HALF_UP);
		} catch (NumberFormatException e) {
			log.error(e.toString());
			return null;
		}
		return result;
	}

	public static String getCurrentTime() {
		String theFormatString = "yyyy-MM-dd HH:mm:ss";
		GregorianCalendar todaysdate = new GregorianCalendar();
		Date theDate = todaysdate.getTime();
		if (theDate == null || theFormatString == null) {
			return "";
		}
		String theDateString = "";
		try {
			SimpleDateFormat theDateFormater = new SimpleDateFormat(
					theFormatString);
			theDateString = theDateFormater.format(theDate);
		} catch (IllegalArgumentException theException) {
			//
			log.error(theException.toString());
		}
		return theDateString;
	}

	public static String getCurrentTime(String theFormatString) {
		return getCurrentTime(theFormatString, 0);
	}
	public static Timestamp getCurrentTimestamp() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}
	public static String getCurrentTime(String theFormatString, int days) {
		// String theFormatString = "yyyy-MM-dd HH:mm:ss";
		GregorianCalendar todaysdate = new GregorianCalendar();
		todaysdate.add(Calendar.DAY_OF_MONTH, days); // 移动相应的天数。
		Date theDate = todaysdate.getTime();
		if (theDate == null || theFormatString == null) {
			return "";
		}
		String theDateString = "";
		try {
			SimpleDateFormat theDateFormater = new SimpleDateFormat(
					theFormatString);
			theDateString = theDateFormater.format(theDate);
		} catch (IllegalArgumentException theException) {
			//
			log.error(theException.toString());
		}
		return theDateString;
	}
 
 
 
  
	/***************************************************************************
	 * 
	 * 获取一个没有重复的随机数组
	 * 
	 * size:对象的大小 len:多少个数据数
	 * 
	 */
	public Vector getRandom(Vector size, int num) {
		if (size.size() == 0) {
			return new Vector();
		}
		Vector result = new Vector();
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			if (size.size() == 0) {
				break;
			}
			int index = r.nextInt(size.size());
			// System.out.println("size="+size.size()+" random="+index);
			result.add(size.get(index));
			size.remove(index);
			if (size.size() == 1 && i < num - 1) {
				result.add(size.get(0));
				break;
			}
		}
		return result;
	}

	 

	/***************************************************************************
	 * 
	 * 获取一个随机数的字符串
	 * 
	 **************************************************************************/
	public static String getRandom(int len) {
		String max = "";
		Random r = new Random();
		String result = "";
		for (int i = 0; i < len; i++) {
			result += r.nextInt(9);
		}
		return result;
	}
	// 将字符串由StandardFrom编码格式转换为StandardTo编码的格式
	public static String codeConver(String Input, String StandardFrom,
			String StandardTo) {
		byte[] bytes;
		String conResult = new String();
		try {
			bytes = Input.getBytes(StandardFrom);
			conResult = new String(bytes, StandardTo);
		}

		catch (Exception e) {
			log.error(e.toString());
			// debugging begins here
			System.out.println(e);
			// debugging ends here
			return ("null");
		}
		return conResult;
	}

	 
	  /**  
	   * 复制单个文件  
	   * @param oldPath String 原文件路径 如：c:/fqf.txt  
	   * @param newPath String 复制后路径 如：f:/fqf.txt  
	   * @return boolean  
	   
	   */  
	  public static long copyFile(String oldPath, String newPath)  
	  {   
		long size=0;
	    String result = "";   
	    try {   
	        //log.debug("************************oldpath="+oldPath);   
	        //log.debug("************************newpath="+newPath);   
	            File newFile = new File(newPath);   
	            
	            if(!newFile.exists()) 
	            {   
	                newFile.createNewFile();      
	            }   
	            size = newFile.length();
	            int bytesum = 0;   
	            int byteread = 0;   
	            File oldfile = new File(oldPath);   
	            if(oldfile.exists())  
	            { //文件存在时   
	                InputStream inStream = new FileInputStream(oldPath); //读入原文件   
	                FileOutputStream fs = new FileOutputStream(newFile);   
	                byte[] buffer = new byte[1444];   
	                while ( (byteread = inStream.read(buffer)) != -1)  
	                {   
	                    bytesum += byteread; //字节数 文件大小   
	                    System.out.println(bytesum);   
	                    fs.write(buffer, 0, byteread);   
	                }   
	                inStream.close();   
	                result = "ok";   
	            }   
	        }   
	        catch (Exception e)  
	        {   
	            //log.debug("复制单个文件操作出错");  
	        	log.error(e.toString());
	        	System.out.println(e);
	            e.printStackTrace();   
	            result = "no";   
	        }   
	        return size;   
	    }  
	  public static String toUtf8String(String s) {
		  StringBuffer sb = new StringBuffer(); 
		  for (int i=0;i<s.length();i++) { 
		  char c = s.charAt(i); 
		  if (c >= 0 && c <= 255) { 
		  sb.append(c); 
		  } else { 
		  byte[] b; 
		  try { 
		  b = Character.toString(c).getBytes("utf-8"); 
		  } catch (Exception ex) { 
			  log.error(ex.toString());
		  System.out.println(ex); 
		  b = new byte[0]; 
		  } 
		  for (int j = 0; j < b.length; j++) { 
		  int k = b[j]; 
		  if (k < 0) k += 256; 
		  sb.append("%" + Integer.toHexString(k). 
		  toUpperCase()); 
		  } 
		  } 
		  } 
		  return sb.toString(); 
		  } 
	//----------------------------------------------------------
	  /** *//**利用MD5进行加密
	   * @param str  待加密的字符串
	   * @return  加密后的字符串
	   * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
	   * @throws UnsupportedEncodingException  
	   */
	  public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
	      //确定计算方法
	      MessageDigest md5=MessageDigest.getInstance("md5");
	      //BASE64Encoder base64en = new BASE64Encoder();
	      //加密后的字符串
	      //String newstr=base64en.encode(md5.digest(str.getBytes("ISO8859_15")));
	      byte byteArray[]= md5.digest(str.getBytes("ISO8859_15"));
	      StringBuffer md5StrBuff = new StringBuffer();  
	      
	      for (int i = 0; i < byteArray.length; i++) 
	      {              
	          if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
	                  md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
	               else  
	                 md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
	      }  

	      return md5StrBuff.toString();
	  }
	  
	  public static String EncoderByMd5UTF8(String str) {
	      //确定计算方法
		  MessageDigest md5= null;
		  StringBuffer md5StrBuff = null;
		  try {
			  md5 = MessageDigest.getInstance("md5");
			  byte byteArray[]= md5.digest(str.getBytes("utf-8"));
			  md5StrBuff = new StringBuffer();

			  for (int i = 0; i < byteArray.length; i++)
			  {
				  if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					  md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
				  else
					  md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			  }
		  } catch (NoSuchAlgorithmException e) {
			  e.printStackTrace();
		  } catch (UnsupportedEncodingException e) {
			  e.printStackTrace();
		  }
		  return md5StrBuff.toString();
	  }
	  
	  public static String EncoderByMd5UTF8(String key, String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		  
		  str = key + str;
		  
	      //确定计算方法
	      MessageDigest md5=MessageDigest.getInstance("md5");
	      //BASE64Encoder base64en = new BASE64Encoder();
	      //加密后的字符串
	      //String newstr=base64en.encode(md5.digest(str.getBytes("ISO8859_15")));
	      byte byteArray[]= md5.digest(str.getBytes("utf-8"));
	      StringBuffer md5StrBuff = new StringBuffer();  
	      
	      for (int i = 0; i < byteArray.length; i++) 
	      {              
	          if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
	                  md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
	               else  
	                 md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
	      }  

	      return md5StrBuff.toString();
	  }
	  
	  private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		   char c0 = hexDigits[(bt& 0xf0) >> 4];
		   char c1 = hexDigits[bt& 0xf];
		   stringbuffer.append(c0);
		   stringbuffer.append(c1);
	  }
	  
	  private static String bufferToHex(byte bytes[], int m, int n){
		   StringBuffer stringbuffer =new StringBuffer(2 * n);
		   int k = m + n;
		   for (int l = m; l< k; l++) {
		   appendHexPair(bytes[l], stringbuffer);
		   }
		   return stringbuffer.toString();
		}
	  
	  public static String getFileMD5String(File file) throws IOException,NoSuchAlgorithmException, UnsupportedEncodingException{
		  //确定计算方法
	       MessageDigest md5 = MessageDigest.getInstance("md5");
		
		   FileInputStream in = new FileInputStream(file);
		   FileChannel ch =in.getChannel();
		   MappedByteBuffer byteBuffer =ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		   md5.update(byteBuffer);
		   
		   return bufferToHex(md5.digest(),0,md5.digest().length);
	  }
	  /**返回大小写字母及数字混合的字符串*/  
	public static String getRandomStringA(int length){   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();          
	    for(int i = 0; i < length; ++i){   
	        int number = random.nextInt(3);   
	        long result = 0;   
	        switch(number){   
	        case 0:   
	            result = Math.round(Math.random() * 25 + 64);   
	            sb.append(String.valueOf((char)result));   
	            break;   
	        case 1:   
	            result = Math.round(Math.random() * 25 + 97);   
	            sb.append(String.valueOf((char)result));   
	            break;   
	        case 2:   
	            sb.append(String.valueOf(new Random().nextInt(10)));   
	            break;                 
	        }   
	    }      
	    return sb.toString();   
	}   
}