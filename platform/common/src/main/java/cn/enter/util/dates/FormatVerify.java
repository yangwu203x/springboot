package cn.enter.util.dates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatVerify {
	 public static boolean isPhone(String phone){     
	        Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");     
	        Matcher m = p.matcher(phone);     
	        System.out.println(m.matches()+"---");     
	        return m.matches();     
	    } 
	   
	    public static boolean isEmail(String email){     
	     String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
	        Pattern p = Pattern.compile(str);     
	        Matcher m = p.matcher(email);     
	        System.out.println(m.matches()+"---");     
	        return m.matches();     
	    } 
}
