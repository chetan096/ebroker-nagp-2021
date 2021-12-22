package com.nagarro.unittesting.eBroker.utils;

import java.time.DayOfWeek;

public class EquityUtililty {

	public static Boolean checkForValidDayAndTime() {
		Integer currentHour = DateUtils.getCurrentHour();
		DayOfWeek currentDOW = DateUtils.getCurrentDOW();
		if (currentDOW == DayOfWeek.SATURDAY || currentDOW == DayOfWeek.SUNDAY) {
			return false;
		} else if (currentHour < 8 || currentHour > 16) {
			return false;
		}
		return true;
	}

	

}
