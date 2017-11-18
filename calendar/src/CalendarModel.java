
import java.io.*;
import java.util.*;
import javax.swing.event.*;


/**
 * Creates a calendar that holds scheduled events.
 * Model aspect of MVC.
 */
public class CalendarModel 
{
	private DailyEvent selectedDay;
	private TreeMap<GregorianCalendar, TreeSet<Event>> events;
    private ArrayList<ChangeListener> listeners;
	
    /**
     * Creates a calendar with events. This 
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public CalendarModel() throws ClassNotFoundException, IOException
    {
    	GregorianCalendar calendar = new GregorianCalendar();
    	listeners = new ArrayList<ChangeListener>();
    	load();
    	GregorianCalendar day = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
    	if (events.get(day) == null)
    		events.put(day, new TreeSet<Event>());
    	
    	selectedDay = new DailyEvent(day, events.get(day));
    }
    
    /**
     * Attaches a listener to this calendar
     * @param l the ChangeListener to be attached
     */
    public void attach(ChangeListener l)
    {
    	listeners.add(l);
    }
    
    /**
     * Notifies all listeners that a change has been made
     */
    private void notifyListeners()
    {
    	for (ChangeListener c : listeners)
    		c.stateChanged(new ChangeEvent(this));
    }
    
    /**
     * Returns the selected day
     * @return the selected day
     */
    public DailyEvent getSelectedDay()
    {
    	return selectedDay;
    }
    
    /**
     * Returns the events loaded in the calendar
     * @return the events loaded in the calendar
     */
    public TreeMap<GregorianCalendar, TreeSet<Event>> getEvents()
    {
    	return events;
    }
    
	
	/**
	 * Adds the specified event to the calendar if no conflict exists
	 * @return true if there are no conflicting events and the event is added to the calendar
	 */
	public boolean createEvent(Event e)
	{
		GregorianCalendar cal = e.getDay();
		if (events.keySet().contains(cal))
		{
			TreeSet<Event> tree = events.get(cal);
			if (!conflictExists(e, tree))
				tree.add(e);
			else
				return false;
		}
		else
		{
			events.put(cal, new TreeSet<Event>());
			events.get(cal).add(e);
		}
		if (selectedDay.getDay().equals(cal))
		{
			this.selectedDay = new DailyEvent(cal, events.get(cal));
			notifyListeners();
		}
		return true;
	}

	/**
	 * Checks if two events have conflicting times.
	 * @param newEvent The event to be checked
	 * @param scheduledEvents The events already scheduled for that day
	 * @return True if a conflict exists and false otherwise
	 */
	private boolean conflictExists(Event newEvent, TreeSet<Event> scheduledEvents)
	{
		for (Event scheduledEvent : scheduledEvents)
		{
			if (newEvent.getStartTimeCal().compareTo(scheduledEvent.getStartTimeCal()) < 0 && newEvent.getEndTimeCal().compareTo(scheduledEvent.getStartTimeCal()) > 0)
				return true;
			else if (newEvent.getStartTimeCal().compareTo(scheduledEvent.getEndTimeCal()) < 0 && newEvent.getEndTimeCal().compareTo(scheduledEvent.getStartTimeCal()) > 0)
				return true;
		}
		return false;
	}
	
	/**
	 * Goes to a specified day on the Gregorian calendar
	 * @param cal the day to go to
	 */
	public void goTo(GregorianCalendar cal)
	{
		selectedDay = new DailyEvent(cal, events.get(cal));
		notifyListeners();
	}
	
	/**
	 * Goes to the next day on the calendar
	 */
	public void nextDay()
	{
		GregorianCalendar nextDay = selectedDay.getDay();
		nextDay.add(Calendar.DATE, 1);
		selectedDay = new DailyEvent(nextDay, events.get(nextDay));
		notifyListeners();
	}
	
	/**
	 * Goes to the previous day on the calendar
	 */
	public void prevDay()
	{
		GregorianCalendar prevDay = selectedDay.getDay();
		prevDay.add(Calendar.DATE, -1);
		selectedDay = new DailyEvent(prevDay, events.get(prevDay));
		notifyListeners();
	}

	/**
	 * Loads a text file events.txt with events from previous uses of MyCalendar.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void load() throws IOException, ClassNotFoundException
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("events.txt"));
			this.events = (TreeMap<GregorianCalendar, TreeSet<Event>>) in.readObject();
			in.close();
		}
		catch (FileNotFoundException e)
		{
	    	events = new TreeMap<GregorianCalendar, TreeSet<Event>>();
		}
	}
	
	/**
	 * Saves the events on the calendar to a file called events.txt. If such a file does not exist within the directory, it is created.
	 * @throws IOException
	 */
	public void save() throws IOException
	{
		File theFile = new File("events.txt");
		theFile.createNewFile();
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("events.txt", false));
		out.writeObject(this.events);
		out.close();
	}
	
	
	
	
	


	/**
	 * A class that holds a day (represented as a GregorianCalendar) and the events scheduled for that day
	 */
	static class DailyEvent 
	{
		private GregorianCalendar day;
		private TreeSet<Event> events;

		/**
		 * Creates a day with events
		 * @param day the day, represented by a GregorianCalendar
		 * @param events the events scheduled for the day
		 */
		public DailyEvent(GregorianCalendar day, TreeSet<Event> events)
		{
			this.day = day;
			this.events = events;
		}

		/**
		 * Returns the day for the DailyEvent
		 * @return the day for the DailyEvent
		 */
		public GregorianCalendar getDay()
		{
			return day;
		}

		/**
		 * Returns the events for this day
		 * @return the events for this day
		 */
		public TreeSet<Event> getEvents()
		{
			return events;
		}

		/**
		 * Returns the date as a String
		 * @return the date as a String
		 */
		public String getDate()
		{
			String year = ((Integer)day.get(Calendar.YEAR)).toString();
			String month = ((Integer)(day.get(Calendar.MONTH) + 1)).toString();
			String date = ((Integer)day.get(Calendar.DATE)).toString();
			return month + '/' + date + '/' + year;
		}
	}

}
