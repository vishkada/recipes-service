package com.task.favourite.recipes.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	  
	  public static Timestamp getCurrentDateTime() {
		  Date date = Calendar.getInstance().getTime();
		  DateFormat dateFormat = new SimpleDateFormat("dd‐MM‐yyyy HH:mm");
		  try {
		    	SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd‐MM‐yyyy HH:mm");
		        return new Timestamp(DATE_TIME_FORMAT.parse(dateFormat.format(date)).getTime());
		    } catch (ParseException e) {
		        throw new IllegalArgumentException(e);
		    }
	  }
}
