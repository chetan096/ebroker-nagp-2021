package com.nagarro.unittesting.eBroker.utils;

import java.time.DayOfWeek;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EquityUtililtyTest {
	
	@Test
	public void testCheckValidDayAndTime_DOWSaturday() {
		try (MockedStatic<DateUtils> utilites = Mockito.mockStatic(DateUtils.class)) {
			utilites.when(DateUtils::getCurrentDOW).thenReturn(DayOfWeek.SATURDAY);
			Assertions.assertEquals(Boolean.FALSE, EquityUtililty.checkForValidDayAndTime());
		}
	}

	@Test
	public void testCheckValidDayAndTime_DOWSunday() {
		try (MockedStatic<DateUtils> utilites = Mockito.mockStatic(DateUtils.class)) {
			utilites.when(DateUtils::getCurrentDOW).thenReturn(DayOfWeek.SUNDAY);
			Assertions.assertEquals(Boolean.FALSE, EquityUtililty.checkForValidDayAndTime());
		}
	}
	
	@Test
	public void testCheckValidDayAndTime_CurrentHour22() {
		try (MockedStatic<DateUtils> utilites = Mockito.mockStatic(DateUtils.class)) {
			utilites.when(DateUtils::getCurrentDOW).thenReturn(DayOfWeek.MONDAY);
			utilites.when(DateUtils::getCurrentHour).thenReturn(22);
			Assertions.assertEquals(Boolean.FALSE, EquityUtililty.checkForValidDayAndTime());
		}
	}
	
	@Test
	public void testCheckValidDayAndTime_CurrentHour2() {
		try (MockedStatic<DateUtils> utilites = Mockito.mockStatic(DateUtils.class)) {
			utilites.when(DateUtils::getCurrentDOW).thenReturn(DayOfWeek.MONDAY);
			utilites.when(DateUtils::getCurrentHour).thenReturn(2);
			Assertions.assertEquals(Boolean.FALSE, EquityUtililty.checkForValidDayAndTime());
		}
	}
	
	@Test
	public void testCheckValidDayAndTime_ValidDayAndTime() {
		try (MockedStatic<DateUtils> utilites = Mockito.mockStatic(DateUtils.class)) {
			utilites.when(DateUtils::getCurrentDOW).thenReturn(DayOfWeek.MONDAY);
			utilites.when(DateUtils::getCurrentHour).thenReturn(12);
			Assertions.assertEquals(Boolean.TRUE, EquityUtililty.checkForValidDayAndTime());
		}
	}
	
}
