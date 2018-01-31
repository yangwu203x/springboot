package cn.enter.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * 工具类
 *
 */
public class Tools {
	/**
	 * 产生一个新的文件名
	 * @return
	 * @throws Exception
	 */
	public static String fileRename(String fileName) throws Exception{
		UUID uuid=UUID.randomUUID();
		//获取uuid
		String uuidString=uuid.toString();
		//去掉所有的-
		uuidString=uuidString.replaceAll("-","");
		//去一半长度
		uuidString=	uuidString.substring(uuidString.length()/2, uuidString.length());

		return uuidString+"."+obtainFileSuffix(fileName);
	}

	/**
	 * 根据所给的文件名获取文件的后缀
	 * @param fileName
	 * @return .* 如.png
	 * @throws Exception
	 */
	public static String obtainFileSuffix(String fileName) throws Exception{
		//找寻最后一个.所在的位置
		int startIndex=fileName.lastIndexOf(".");
		String fileSuffix=fileName.substring(startIndex+1,fileName.length());
		return fileSuffix;
	}


	/**
	 * 属性文件解析,根据属性名在属性文件中获取属性值
	 * @param propertyName
	 * proLocation .properties所在的文件位置
	 * @return
	 * @throws Exception
	 */
	public static String propertiesFileResolve(String propertyName) {
		//实例化属性文件对象
		Properties properties=new Properties();
		//获取流
		InputStream stream=Tools.class.getResourceAsStream(Constant.PROPERTIESFILENAME);
		//加载流
		try {
			properties.load(stream);
			//根据所给的属性名获取值
			return properties.getProperty(propertyName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据所给的文件名获取文件的名称不包含后缀
	 * @param fileName
	 * @return xx.* 返回xx
	 * @throws Exception
	 */
	public static String obtainFileName(String fileName) throws Exception{
		//找寻最后一个.所在的位置
		int startIndex=fileName.lastIndexOf(".");
		return fileName.substring(0,startIndex);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param date
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = sdf.parse(sdf.format(date));
		Date bdate = new Date(System.currentTimeMillis());
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 判断是否是int
	 */
	public static boolean isInteger(String num) {
		try {
			Integer.parseInt(num);
		} catch (Exception e) {
			return false;
		}
		return true;
	}





	/**
	 * 判断字符串是否是英文
	 * 
	 * @param word
	 * @return
	 */
	public static boolean strIsEnglish(String word) {
		return word.matches("[a-zA-Z]+");
	}

	public static String getUTF8StringFromGBKString(String gbkStr) {
		try {
			return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new InternalError();
		}
	}

	public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
		int n = gbkStr.length();
		byte[] utfBytes = new byte[3 * n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			int m = gbkStr.charAt(i);
			if (m < 128 && m >= 0) {
				utfBytes[k++] = (byte) m;
				continue;
			}
			utfBytes[k++] = (byte) (0xe0 | (m >> 12));
			utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
			utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
		}
		if (k < utfBytes.length) {
			byte[] tmp = new byte[k];
			System.arraycopy(utfBytes, 0, tmp, 0, k);
			return tmp;
		}
		return utfBytes;
	}

}
