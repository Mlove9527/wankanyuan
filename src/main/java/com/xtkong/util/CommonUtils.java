package com.xtkong.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
	/****
	 * 日期格式校验
	 * 假设传入的日期格式是yyyy-MM-dd HH:mm:ss, 也可以传入yyyy-MM-dd，如2018-1-1或者2018-01-01格式***/
    public static boolean isValidDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2018-02-29会被接受，并转换成2018-03-01 
            format.setLenient(false);
            Date date = format.parse(strDate);
            
            //判断传入的yyyy年-MM月-dd日 字符串是否为数字
            String[] sArray = strDate.split("-");
            for (String s : sArray) {
                boolean isNum = s.matches("[0-9]+");
                //+表示1个或多个（如"3"或"225"），*表示0个或多个（[0-9]*）（如""或"1"或"22"），?表示0个或1个([0-9]?)(如""或"7")
                if (!isNum) {
                    return false;
                }
             }
         } catch (Exception e) {
             // e.printStackTrace();
             // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
             return false;
         }
  
         return true;
     }
    
    /**判断String是否是数值***/
    public static boolean isValidNum(String strNum) {
        boolean flag =true;
        try {
            new BigDecimal(strNum);
        } catch (Exception e) {
            flag=false;
        }
        return flag;
    }
}
