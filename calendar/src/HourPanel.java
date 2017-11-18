import javax.swing.*;

import java.awt.*;
import java.util.*;

/**
 * A class that draws boxes for the hours of the day
 */
public class HourPanel extends JPanel
{
	private final int BOX_WIDTH;
	private final int BOX_HEIGHT;

	/**
	 * Creates a panel to show the hours of the day
	 * @param boxWidth the width of the boxes
	 * @param boxHeight the height of the boxes
	 */
	public HourPanel(int boxWidth, int boxHeight)
	{
		BOX_WIDTH = boxWidth;
		BOX_HEIGHT = boxHeight;

		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(BOX_WIDTH + 1, BOX_HEIGHT * 24 + 1));
	}

	/**
	 * Creates rectangles and strings for each hour of the day
	 * @param g2 the Graphics2D being used to draw the boxes and strings
	 */
	private void drawHours(Graphics2D g2)
	{
		for (int i = 0; i < 24; i++)
		{
			// Creates a rectangle for each hour of the day
			g2.draw(new Rectangle(0, i * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT));			

			// Formats the hour into 12 hour format and displays it on the panel
			Integer temp = i % 12;
			if (temp == 0)
				temp = 12;
			String s = temp.toString();
			if (i < 12)
				s += " am";
			else
				s += " pm";

			g2.drawString(s, 2, 12 + i * BOX_HEIGHT);
		}
	}


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		drawHours(g2);
	}
}
