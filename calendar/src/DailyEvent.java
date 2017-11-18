//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.TreeSet;
//
///**
// * A class that holds a day (represented as a GregorianCalendar) and the events scheduled for that day
// */
//public class DailyEvent 
//{
//	private GregorianCalendar day;
//	private TreeSet<Event> events;
//	
//	/**
//	 * Creates a day with events
//	 * @param day the day, represented by a GregorianCalendar
//	 * @param events the events scheduled for the day
//	 */
//	public DailyEvent(GregorianCalendar day, TreeSet<Event> events)
//	{
//		this.day = day;
//		this.events = events;
//	}
//	
//	/**
//	 * Returns the day for the DailyEvent
//	 * @return the day for the DailyEvent
//	 */
//	public GregorianCalendar getDay()
//	{
//		return day;
//	}
//	
//	/**
//	 * Returns the events for this day
//	 * @return the events for this day
//	 */
//	public TreeSet<Event> getEvents()
//	{
//		return events;
//	}
//	
//	/**
//	 * Returns the date as a String
//	 * @return the date as a String
//	 */
//	public String getDate()
//	{
//		String year = ((Integer)day.get(Calendar.YEAR)).toString();
//		String month = ((Integer)(day.get(Calendar.MONTH) + 1)).toString();
//		String date = ((Integer)day.get(Calendar.DATE)).toString();
//		return month + '/' + date + '/' + year;
//	}
//}
