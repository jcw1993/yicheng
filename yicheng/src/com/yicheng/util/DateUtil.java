package com.yicheng.util;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtil {

	public static Date timestampToDate(Timestamp time) {
		if(null == time) {
			return null;
		}
		return new Date(time.getTime());
	}
	
	public static Timestamp dateToTimestamp(Date date) {
		if(null == date) {
			return null;
		}
		return new Timestamp(date.getTime());
 	}
}
