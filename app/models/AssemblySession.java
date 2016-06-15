package models;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssemblySession {
	
	private static String time;
	private static String date;
	private static boolean isScheduled = false;

	public static String getTime() {
		return time;
	}

	public static String getDate() {
		return date;
	}

	public static boolean isScheduled() {
		return isScheduled;
	}

	public static boolean isRunning() {
		
		if (!isScheduled)
			return false;
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		Date session = null;
		Date now = new Date();
		
		try {
			session = dateFormat.parse(date+" "+time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return session.compareTo(now) < 0;
	}

	public static void scheduleSession(String time, String date) {
		AssemblySession.time = time;
		AssemblySession.date = date;
		AssemblySession.isScheduled = true;
	}

	public static void finishSession() {
		AssemblySession.isScheduled = false;
	}
	
	
}
