package com.nagarro.unittesting.eBroker.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtils {
	
	public static Integer getCurrentHour() {
		Integer currentHour = LocalDateTime.now().getHour();
		return currentHour;
	}

	public static DayOfWeek getCurrentDOW() {
		DayOfWeek currentDOW = LocalDate.now().getDayOfWeek();
		return currentDOW;
	}

}
