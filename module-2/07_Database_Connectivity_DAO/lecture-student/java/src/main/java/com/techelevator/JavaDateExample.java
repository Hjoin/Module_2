package com.techelevator;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class JavaDateExample {

	public static void main(String[] args) {
		doBirthdayCalculationsUsingLegacyDateClasses();
		doBirthdayCalculationsUsingJava8Classes();
	}

	private static void doBirthdayCalculationsUsingLegacyDateClasses() {
		Calendar birthdayCal = Calendar.getInstance(); // This instance now represents the instant (year, month, day, hour, etc) that it was instantiated
		birthdayCal.set(1995, Calendar.MAY, 23, 0, 0, 0);
		
		Date today = new Date(); // This instance now represents the instant (year, month, day, hour, etc) that it was instantiated
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(today);
		
		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int todayMonth = todayCal.get(Calendar.MONTH);
		int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
		if(birthdayMonth == todayMonth && birthdayDay == todayDay) {
			System.out.println("Happy Birthday Java!");
		} else {
			int birthdayDayOfYear = birthdayCal.get(Calendar.DAY_OF_YEAR);
			int todayDayOfYear = todayCal.get(Calendar.DAY_OF_YEAR);
			int daysUntilBirthday = birthdayDayOfYear - todayDayOfYear;
			if(daysUntilBirthday < 0) {
				daysUntilBirthday = 365 + daysUntilBirthday;
			}
			System.out.println(daysUntilBirthday+" days until Java's birthday!");
		}
	}
	
	private static void doBirthdayCalculationsUsingJava8Classes() {
		LocalDate javaBirthdate = LocalDate.of(1995, 5, 23);
		MonthDay javaBirthday = MonthDay.from(javaBirthdate);
		if(javaBirthday.equals(MonthDay.now())) {
			System.out.println("Happy Birthday Java!");
		} else {
			LocalDate today = LocalDate.now();
			long daysUntilBirthday = javaBirthdate.getDayOfYear() - today.getDayOfYear();
			if(daysUntilBirthday < 0) {
				daysUntilBirthday = 365 + daysUntilBirthday;
			}
			System.out.println(daysUntilBirthday+" days until Java's birthday!");
		}
	}
	
	
}
