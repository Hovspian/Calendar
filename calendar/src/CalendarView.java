import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

enum DAYS
{
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
}

/**
 * The view aspect of the calendar
 */
public class CalendarView extends JPanel implements ChangeListener
{
	private CalendarModel cal;	
	private static final DAYS[] daysOfWeek = DAYS.values();

	/**
	 * Creates the view portion of the calendar
	 * @param cal the model being used
	 */
	public CalendarView(CalendarModel cal)
	{
		this.cal = cal;
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		draw();
	}
	
	/**
	 * Redraws the panel according to the changes in MyCalendar
	 */
	public void stateChanged(ChangeEvent e) 
	{
		draw();
	}
	
	/**
	 * Draws the panel
	 */
	private void draw()
	{
		this.removeAll(); // Clears the panel before drawing on it
		
		CalendarModel.DailyEvent dailyEvent = cal.getSelectedDay();
		GregorianCalendar day = dailyEvent.getDay();
		
		// Draws a header with the day of week and date
		JTextArea date = new JTextArea(daysOfWeek[day.get(Calendar.DAY_OF_WEEK) - 1].toString() + " " + ((Integer)(day.get(Calendar.MONTH) + 1)).toString() + "/" + ((Integer)(day.get(Calendar.DATE))).toString());		
		add(date, BorderLayout.NORTH);

		add(new HourPanel(60, 35), BorderLayout.WEST); // Draws boxes for every hour
		add(new EventPanel(dailyEvent.getEvents(), 400, 35), BorderLayout.EAST); // Draws boxes and fills in sections where events are taking place
		
		revalidate();
		repaint();
	}
}
