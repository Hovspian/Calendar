import java.awt.*;
import java.awt.event.*;
import java.util.GregorianCalendar;
import javax.swing.*;

/**
 * A class that creates a window to schedule events on the calendar
 */
public class EventCreator 
{
	/**
	 * Creates a frame to schedule events on the given calendar
	 * @param calendar the calendar to schedule events on
	 */
	public EventCreator(CalendarModel calendar)
	{
		JFrame frame = new JFrame();

		JTextField eventNameField = new JTextField("Untitled Event");
		frame.add(eventNameField, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.setBackground(Color.WHITE);

		JTextField dateField = new JTextField(calendar.getSelectedDay().getDate());
		JTextField startTimeField = new JTextField("00:00");
		JTextArea to = new JTextArea("to");
		to.setEditable(false);
		JTextField endTimeField = new JTextField("23:59");
		JButton saveButton = new JButton("Save");

		centerPanel.add(dateField);
		centerPanel.add(startTimeField);
		centerPanel.add(to);
		centerPanel.add(endTimeField);
		centerPanel.add(saveButton);

		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				String[] dateArr = dateField.getText().split("/");
				int month = Integer.parseInt(dateArr[0]) - 1;
				int day = Integer.parseInt(dateArr[1]);
				int year = Integer.parseInt(dateArr[2]);

				String[] startArr = startTimeField.getText().split(":");
				int startHour = Integer.parseInt(startArr[0]);
				int startMinute = Integer.parseInt(startArr[1]);

				String[] endArr = endTimeField.getText().split(":");
				int endHour = Integer.parseInt(endArr[0]);
				int endMinute = Integer.parseInt(endArr[1]);

				// Checks if end time comes before start time
				if (startHour > endHour || (startHour == endHour && startMinute > endMinute))
				{
					frame.add(new JTextArea("Event times input incorrectly"), BorderLayout.SOUTH);
					frame.pack();
					return;
				}

				Event event = new Event(eventNameField.getText(), new GregorianCalendar(year, month, day, startHour, startMinute), new GregorianCalendar(year, month, day, endHour, endMinute));
				boolean eventCreated = calendar.createEvent(event);
				// Close the window if the event is created
				if (eventCreated)
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				// Give an error message if there is a conflict with the event
				else
				{
					frame.add(new JTextArea("Event conflicts with existing event"), BorderLayout.SOUTH);
					frame.pack();
				}
			}
		});

		frame.add(centerPanel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
