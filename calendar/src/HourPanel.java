import javax.swing.*;
import java.awt.*;

/**
 * A class that draws boxes for the hours of the day
 */
public class HourPanel extends JPanel
{
	private final int BOX_WIDTH;
	private final int BOX_HEIGHT;
	private final int BORDER_SIZE;

	/**
	 * Creates a panel to show the hours of the day
	 * @param boxWidth the width of the boxes
	 * @param boxHeight the height of the boxes
	 */
	public HourPanel(int boxWidth, int boxHeight, int borderSize)
	{
		BOX_WIDTH = boxWidth;
		BOX_HEIGHT = boxHeight;
		BORDER_SIZE = borderSize;

		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(BOX_WIDTH + BORDER_SIZE, BOX_HEIGHT * 24 + BORDER_SIZE));
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
			g2.draw(new Rectangle(BORDER_SIZE, i * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT));			

			// Formats the hour into 12 hour format and displays it on the panel
			Integer temp = i % 12;
			if (temp == 0)
				temp = 12;
			String s = temp.toString();
			if (i < 12)
				s += " am";
			else
				s += " pm";

			g2.drawString(s, BORDER_SIZE + 3, g2.getFont().getSize() + i * BOX_HEIGHT);
		}
	}


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		drawHours(g2);
	}
}
