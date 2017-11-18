import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 * A class that creates a calendar with events using the MVC pattern
 */
public class SimpleCalendar 
{
	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException
	{
		CalendarModel calendar = new CalendarModel();
		JFrame frame = new JFrame();
		
		// Adds the four buttons at the top of the calendar
		JPanel topPanel = new JPanel();
		JButton prevButton = new JButton("<"); // Controller
		JButton nextButton = new JButton(">"); // Controller
		JButton createButton = new JButton("Create"); // Controller
		JButton quitButton = new JButton("Quit");
		
		prevButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				calendar.prevDay();
			}
		});
		
		nextButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				calendar.nextDay();
			}
		});
		
		createButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				new EventCreator(calendar);
			}
		});
		createButton.setBackground(Color.red);
		createButton.setForeground(Color.white);
		
		quitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		topPanel.add(prevButton);
		topPanel.add(nextButton);
		topPanel.add(createButton);
		topPanel.add(quitButton);
		topPanel.setBackground(Color.WHITE);
		frame.add(topPanel, BorderLayout.NORTH);
		
		
		CalendarController calendarController = new CalendarController(calendar);
		calendar.attach(calendarController);
		frame.add(calendarController, BorderLayout.WEST);
		

		CalendarView viewer = new CalendarView(calendar);
		calendar.attach(viewer);
		frame.add(viewer, BorderLayout.EAST);
		
		
		// Saves the calendar before closing
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent event)
			{
				try {
					calendar.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
				frame.dispose();
				System.exit(0);
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
