package pizzaProgram.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class is used to make strings that represent dates, used in the gui
 */
public class DateFormatter {

	/**
	 * Formats the stringrepresentation of a date If the date is today, it will
	 * return only the time, else it will return the full date
	 * 
	 * @param databaseDateTimeResultString
	 *            the time/date that should be formatted
	 * @return the formatted date as a string
	 */
	public static String formatDateString(String databaseDateTimeResultString) {
		String[] separatedDayAndTimeStrings = databaseDateTimeResultString.split(" ");
		String date = formatDate(separatedDayAndTimeStrings[0]);
		String time = removeMilliseconds(separatedDayAndTimeStrings[1]);
		time = stripSeconds(time);
		return date + time;
	}

	/**
	 * Strips away the seconds in the timestring
	 * 
	 * @param time
	 *            A time string formatted like "hh:mm:ss"
	 * @return time formatted like "hh:mm"
	 */
	private static String stripSeconds(String time) {
		String[] timeParts = time.split(":");
		return timeParts[0] + ":" + timeParts[1];
	}

	/**
	 * Strips away the milliseconds in a timestring
	 * 
	 * @param time
	 *            A time string formatted like "hh:mm:ss.mmm"
	 * @return time without milliseconds, formatted like "hh:mm:ss"
	 */
	private static String removeMilliseconds(String time) {
		int index = time.indexOf('.');
		if (index > -1) {
			String output = time.substring(0, index);
			return output;
		}
		return time;
	}

	/**
	 * Formats a date string
	 * 
	 * @param date
	 *            Any date string formatted like "yyyy-mm-dd"
	 * @return A date string formatted like "dd-mm" if the inserted date is
	 *         'yesterday' or earlier according to the system time, or "" is the
	 *         inseted date string IS today
	 */
	private static String formatDate(String date) {
		if (dateIsNotToday(date)) {
			return formatDateOnlyString(date) + " ";
		} else {
			return "";
		}
	}

	/**
	 * Formats the inserted date string
	 * 
	 * @param date
	 *            A date string, formatted like "yyyy-mm-dd"
	 * @return A formatted date string, formatted like "dd-mm"
	 */
	private static String formatDateOnlyString(String date) {
		String[] dateParts = date.split("-");
		return dateParts[2] + "-" + dateParts[1];
	}

	/**
	 * determines if a date is today or not
	 * 
	 * @param date
	 *            The date to determine whether it is today, formatted like
	 *            "yyyy-mm-dd"
	 * @return returns true if the inserted date is today, and false otherwise
	 */
	private static boolean dateIsNotToday(String date) {
		int[] dateParts = convertDateStringToComponentValues(date);
		int[] todayDateParts = getCurrentDate();
		if (((dateParts[0] <= todayDateParts[0]) && (dateParts[1] == todayDateParts[1]) && (dateParts[2] < todayDateParts[2]))
				|| ((dateParts[0] <= todayDateParts[0]) && (dateParts[1] < todayDateParts[1]))
				|| ((dateParts[0] < todayDateParts[0]))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the current date, according to the system time
	 * 
	 * @return an integer string formatted like new int[]{years, months, days}
	 */
	private static int[] getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(cal.getTime());
		int[] currentDateParts = convertDateStringToComponentValues(currentDate);
		return currentDateParts;
	}

	/**
	 * Converts the date to an array containing years, months and days
	 * 
	 * @param currentDate
	 *            A string representing the current date, formatted as
	 *            "yyyy-mm-dd"
	 * @return an integer array formatted like new int[]{years, months, days}
	 */
	private static int[] convertDateStringToComponentValues(String currentDate) {
		String[] dateParts = currentDate.split("-");
		int years = Integer.parseInt(dateParts[0]);
		int months = Integer.parseInt(dateParts[1]);
		int days = Integer.parseInt(dateParts[2]);
		int[] datePartsIntValues = new int[] { years, months, days };
		return datePartsIntValues;
	}
}
