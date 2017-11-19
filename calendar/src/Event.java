import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Holds information for a single event
 */
public class Event implements Comparable<Event>, Serializable
{
	private static final long serialVersionUID = 5468750280103867366L;
	private String name;
	private GregorianCalendar startTimeCal;
	private GregorianCalendar endTimeCal;
	private GregorianCalendar dayCal;

	/**
	 * Creates an event with the specified name, start time, and end time
	 * @param name The name of the event
	 * @param start The start time of the event
	 * @param end The end time of the event
	 */
	public Event(String name, GregorianCalendar start, GregorianCalendar end)
	{
		this.name = name;
		this.startTimeCal = start;
		this.endTimeCal = end;

		this.dayCal = new GregorianCalendar(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DATE));
	}


	/**
	 * Returns the name of the event.
	 * @return The event's name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Returns a GregorianCalendar for the event's date and start time
	 * @return The GregorianCalendar that has the event's date and start time
	 */
	public GregorianCalendar getStartTimeCal()
	{
		return this.startTimeCal;
	}

	/**
	 * Returns a GregorianCalendar for the event's date and end time
	 * @return The GregorianCalendar that has the event's date and end time
	 */
	public GregorianCalendar getEndTimeCal()
	{
		return this.endTimeCal;
	}

	/**
	 * Returns a GregorianCalendar for the event's date
	 * @return The GregorianCalendar that has the event's date
	 */
	public GregorianCalendar getDay()
	{
		return this.dayCal;
	}


	/**
	 * Compares two events first by start time then by end time.
	 */
	public int compareTo(Event that)
	{
		if (!this.getStartTimeCal().equals(that.getStartTimeCal()))
			return this.getStartTimeCal().compareTo(that.getStartTimeCal());
		else if (!this.getEndTimeCal().equals(that.getEndTimeCal()))
			return this.getEndTimeCal().compareTo(that.getEndTimeCal());
		else
			return 0;
	}

	/**
	 * Returns a string formatted with the start and end times of this event
	 * @return a string formatted with the start and end times of this event
	 */
	public String getStartAndEndTimes()
	{
		String start = startTimeCal.get(Calendar.HOUR) + ":";
		int startMin = startTimeCal.get(Calendar.MINUTE);
		if (startMin == 0)
			start += "00";
		else if (startMin < 10)
			start += "0" + startMin;
		else
			start += startMin;
		if (startTimeCal.get(Calendar.AM_PM) == Calendar.AM)
			start += " am";
		else
			start += " pm";

		String end = endTimeCal.get(Calendar.HOUR) + ":";
		int endMin = endTimeCal.get(Calendar.MINUTE);
		if (endMin == 0)
			end += "00";
		else if (endMin < 10)
			end += "0" + endMin;
		else
			end += endMin;
		if (endTimeCal.get(Calendar.AM_PM) == Calendar.AM)
			end += " am";
		else
			end += " pm";

		return start + " - " + end;
	}
}
