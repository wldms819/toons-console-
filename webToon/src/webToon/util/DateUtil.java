package webToon.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
	
	//문자를 ToDate로 바꾸는 함수
	public static Date convertStringToDate(String sdate) {
		Date d=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			java.util.Date d2 = sdf.parse(sdate);
			d = new Date(d2.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return d;
	}
}
