import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * A class that shows scheduled events on a particular day
 */
public class EventPanel extends JPanel
{
	private TreeSet<Event> events;
	private final int BOX_WIDTH;
	private final int HOUR_BOX_HEIGHT;
	private final int BORDER_SIZE;

	/**
	 * Creates a panel to show scheduled events on a particular day
	 * @param events the events scheduled
	 * @param boxWidth the width of the boxes being drawn
	 * @param hourBoxHeight the height of the boxes being drawn
	 */
	public EventPanel(TreeSet<Event> events, int boxWidth, int hourBoxHeight, int borderSize)
	{
		BOX_WIDTH = boxWidth;
		HOUR_BOX_HEIGHT = hourBoxHeight;
		BORDER_SIZE = borderSize;
		this.events = events;
		if (this.events == null)
			this.events = new TreeSet<Event>();

		this.setPreferredSize(new Dimension(BOX_WIDTH + BORDER_SIZE, HOUR_BOX_HEIGHT * 24 + BORDER_SIZE));
		this.setBackground(Color.WHITE);
	}

	/**
	 * Creates a background of white boxes
	 * @param g2 the Graphics2D being used to draw the boxes
	 */
	private void createBackground(Graphics2D g2)
	{
		ArrayList<Rectangle> backgroundRectangles = new ArrayList<Rectangle>();
		for (int i = 0; i < 24; i++)
		{
			Rectangle r = new Rectangle(0, i * HOUR_BOX_HEIGHT, BOX_WIDTH, HOUR_BOX_HEIGHT);			
			backgroundRectangles.add(r);
		}

		g2.setColor(Color.BLACK);
		for (Rectangle r : backgroundRectangles)
			g2.draw(r);
	}

	/**
	 * Creates boxes for all of the scheduled events
	 * @param g2 the Graphics2D being used to draw the boxes
	 */
	private void createEvents(Graphics2D g2)
	{
		ArrayList<Rectangle> eventRectangles = new ArrayList<Rectangle>();
		HashMap<String, ArrayList<Point>> eventDetails = new HashMap<String, ArrayList<Point>>();
		for (Event e : events)
		{
			GregorianCalendar startCal = e.getStartTimeCal();
			GregorianCalendar endCal = e.getEndTimeCal();

			long timeDifference = endCal.getTimeInMillis() - startCal.getTimeInMillis();
			double minutes = (double) (timeDifference / 60000);


			Rectangle r = new Rectangle(0, startCal.get(Calendar.HOUR_OF_DAY) * HOUR_BOX_HEIGHT + startCal.get(Calendar.MINUTE) / 60 * HOUR_BOX_HEIGHT, BOX_WIDTH, (int) (minutes / 60 * HOUR_BOX_HEIGHT));
			eventRectangles.add(r);
			
			String name = e.getName();
			if (eventDetails.containsKey(name))
				eventDetails.get(name).add(new Point(BORDER_SIZE, g2.getFont().getSize() + startCal.get(Calendar.HOUR_OF_DAY) * HOUR_BOX_HEIGHT + startCal.get(Calendar.MINUTE) / 60 * HOUR_BOX_HEIGHT));
			else
			{
				eventDetails.put(name, new ArrayList<Point>());
				eventDetails.get(name).add((new Point(BORDER_SIZE, g2.getFont().getSize() + startCal.get(Calendar.HOUR_OF_DAY) * HOUR_BOX_HEIGHT + startCal.get(Calendar.MINUTE) / 60 * HOUR_BOX_HEIGHT)));
			}


			String times = e.getStartAndEndTimes();
			if (eventDetails.containsKey(times))
				eventDetails.get(times).add(new Point(BORDER_SIZE, (2 * g2.getFont().getSize()) + startCal.get(Calendar.HOUR_OF_DAY) * HOUR_BOX_HEIGHT + startCal.get(Calendar.MINUTE) / 60 * HOUR_BOX_HEIGHT));
			else
			{
				eventDetails.put(times, new ArrayList<Point>());
				eventDetails.get(times).add((new Point(BORDER_SIZE, (2 * g2.getFont().getSize()) + startCal.get(Calendar.HOUR_OF_DAY) * HOUR_BOX_HEIGHT + startCal.get(Calendar.MINUTE) / 60 * HOUR_BOX_HEIGHT)));			
			}
		}

		g2.setColor(Color.CYAN);
		for (Rectangle r : eventRectangles)
			g2.fill(r);


		g2.setColor(Color.BLACK);
		for (Rectangle r : eventRectangles)
			g2.draw(r);

		for (String s : eventDetails.keySet())
		{
			for (Point p : eventDetails.get(s))
				g2.drawString(s, (int)p.getX(), (int)p.getY());
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		createBackground(g2);
		createEvents(g2);
	}
}
