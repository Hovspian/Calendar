import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

enum MONTHS
{
	January, February, March, April, May, June, July, August, September, October, November, December;
}

/**
 * The controller aspect of the calendar
 */
public class CalendarController extends JPanel implements ChangeListener
{
	private CalendarModel cal;
	private static final MONTHS[] monthsArr = MONTHS.values();

	/**
	 * Creates the controller portion of the calendar
	 * @param cal the model being used
	 */
	public CalendarController(CalendarModel cal)
	{
		this.cal = cal;
		this.setBackground(Color.WHITE);
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
		this.removeAll(); // Clear the panel before drawing on it again

		GregorianCalendar day = cal.getSelectedDay().getDay();
		JPanel monthPanel = new JPanel();
		monthPanel.setLayout(new BorderLayout());

		// Says the month and year of the Calendar
		JTextArea monthHeader = new JTextArea(monthsArr[day.get(Calendar.MONTH)] + " " + day.get(Calendar.YEAR));
		monthHeader.setEditable(false);
		monthPanel.add(monthHeader, BorderLayout.NORTH);


		JPanel daysOfMonthPanel = new JPanel();
		daysOfMonthPanel.setLayout(new GridLayout(day.getActualMaximum(Calendar.WEEK_OF_MONTH) + 1, 7));

		// Creates the header for each day of the week
		String[] daysArr = {"S    ", "M    ", "T    ", "W    ", "T    ", "F    ", "S    "};
		for (String s : daysArr)
		{
			JTextArea t = new JTextArea(s);
			t.setEditable(false);
			daysOfMonthPanel.add(t);
		}


		GregorianCalendar firstOfMonth = new GregorianCalendar(day.get(Calendar.YEAR), day.get(Calendar.MONTH), 1);

		// Creates the number portion of the calendar
		int whiteSpaces = firstOfMonth.get(Calendar.DAY_OF_WEEK);
		for (int i = 1; i < whiteSpaces; i++)
			daysOfMonthPanel.add(new JLabel());

		for (Integer i = 1; i <= firstOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
		{
			JLabel label = new JLabel(i.toString());
			label.setOpaque(true);

			if (i != day.get(Calendar.DATE))
				label.setBackground(Color.WHITE);
			if (i == day.get(Calendar.DATE))
				label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			label.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					cal.goTo(new GregorianCalendar(day.get(Calendar.YEAR), day.get(Calendar.MONTH), Integer.parseInt(label.getText())));
				}
			});

			daysOfMonthPanel.add(label);
		}		
		daysOfMonthPanel.setBackground(Color.WHITE);

		monthPanel.add(daysOfMonthPanel, BorderLayout.SOUTH);
		this.add(monthPanel);
		revalidate();
		repaint();
	}
}
