import java.awt.Color;
import java.util.*;
import uwcse.io.*;
import uwcse.graphics.*;
import javax.swing.*;

/**
 * A class to create and manipulate graphics elements stored in an ArrayList
 */

public class GraphicsElements {

	/** Maximum number of disks in a pile of disks */
	public static final int MAXIMUM_NUMBER_OF_DISKS = 100;

	/** Maximum number of rows (or columns) in a square checkered board */
	public static final int MAXIMUM_NUMBER_OF_ROWS = 50;

	/** Maximum number of points in a Sierpinski triangle */
	public static final int MAXIMUM_NUMBER_OF_POINTS = 10000;

	/** Width of the window (from ViewWindow) */
	public static final int WIDTH = ViewWindow.WINDOW_WIDTH;

	/** Height of the window (from ViewWindow) */
	public static final int HEIGHT = ViewWindow.WINDOW_HEIGHT;

	// Put your other instance fields here (if you need any)

	ArrayList<Oval> listOfDisks = new ArrayList<Oval>();
	ArrayList<Rectangle> squares = new ArrayList<Rectangle>();
	ArrayList<Oval> points = new ArrayList<Oval>();

	/**
	 * Create a top view of a pile of disks of decreasing diameters (from bottom to
	 * top). Use filled circles. The color of each disk is random. The pile should
	 * fill the window. <br>
	 * Store the circles in an ArrayList and return that ArrayList (the disk at the
	 * bottom should be the first element of the ArrayList)<br>
	 * The number of disks is given by the user (use a dialog box). If that number
	 * is less than or equal to 0 or greater than MAXIMUM_NUMBER_OF_DISKS, display
	 * an error message (use JOptionPane.showMessageDialog)and ask for it again.
	 */
	public ArrayList<Oval> createAPileOfDisks() {
		// get the number of disks from the user
		// input can't be more than maximum number of disks.
		int n; // number of disks
		Input in = new Input();
		do {
			n = in.readIntDialog("Enter the number of disks (between 1 and " + MAXIMUM_NUMBER_OF_DISKS + ")");

			// If the input is outside of the range, display an error message
			if (n < 1 || n > MAXIMUM_NUMBER_OF_DISKS) {
				JOptionPane.showMessageDialog(null, "Wrong input, please try again!", "Display of disks",
						JOptionPane.ERROR_MESSAGE);
			}
		} while (n < 1 || n > MAXIMUM_NUMBER_OF_DISKS);

		// create the disks
		for (int i = 0; i < n; i++) {
			Oval disk = new Oval((WIDTH - HEIGHT) / 2 + (i * (HEIGHT / 2) / n), (i * (HEIGHT / 2) / n),
					HEIGHT - (i * HEIGHT / n), HEIGHT - (i * HEIGHT / n), diskColors(), true);
			listOfDisks.add(disk);
		}
		return listOfDisks;
	}

	// randomly selected colors
	public static Color diskColors() {
		Random rand = new Random();
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		Color diskColor = new Color(r, g, b);
		return diskColor;
	}

	/**
	 * Create a square checkered board. Create a Rectangle for each square on the
	 * board. Store the Rectangles in an ArrayList and return that ArrayList. Use
	 * two colors only to paint the squares.<br>
	 * The board should cover most of the window. The number of rows (=number of
	 * columns) is given by the user (use a dialog box). If that number is less than
	 * or equal to 0 or greater than MAXIMUM_NUMBER_OF_ROWS, display an error
	 * message (use JOptionPane.showMessageDialog)and ask for it again.
	 */

	public ArrayList<Rectangle> createACheckeredBoard() {
		int n;
		Input in = new Input();
		do {
			n = in.readIntDialog("Enter the number of rows (between 1 and " + MAXIMUM_NUMBER_OF_ROWS + ")");
			// If the input is outside of the range, display an error message
			if (n < 1 || n > MAXIMUM_NUMBER_OF_ROWS) {
				JOptionPane.showMessageDialog(null, "Wrong input", "Display of Board", JOptionPane.ERROR_MESSAGE);
			}
		} while (n < 1 || n > MAXIMUM_NUMBER_OF_ROWS);

		// create the list of squares
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < n; b++) {
				if (a % 2 == 0 && b % 2 > 0) {
					Rectangle checkBoard = new Rectangle((WIDTH - HEIGHT) / 2 + (a * HEIGHT / n), (b * HEIGHT / n),
							HEIGHT / n, HEIGHT / n, Color.RED, true);
					squares.add(checkBoard);
				} else if (a % 2 == 0 && b % 2 == 0) {
					Rectangle checkBoard = new Rectangle((WIDTH - HEIGHT) / 2 + (a * HEIGHT / n), (b * HEIGHT / n),
							HEIGHT / n, HEIGHT / n, Color.BLACK, true);
					squares.add(checkBoard);
				} else if (a % 2 > 0 && b % 2 > 0) {
					Rectangle checkBoard = new Rectangle((WIDTH - HEIGHT) / 2 + (a * HEIGHT / n), (b * HEIGHT / n),
							HEIGHT / n, HEIGHT / n, Color.BLACK, true);
					squares.add(checkBoard);
				} else {
					Rectangle checkBoard = new Rectangle((WIDTH - HEIGHT) / 2 + (a * HEIGHT / n), (b * HEIGHT / n),
							HEIGHT / n, HEIGHT / n, Color.RED, true);
					squares.add(checkBoard);
				}
			}
		}
		return squares;
	}

	/**
	 * Create a Sierpinski triangle. Create a filled Oval (circle of radius 1) for
	 * each point of the triangle. Store the Ovals in an ArrayList and return that
	 * ArrayList. Use one color only to paint the Ovals.<br>
	 * The triangle should cover most of the window.<br>
	 * The number of points is given by the user (use a dialog box). If that number
	 * is less than or equal to 0 or greater than MAXIMUM_NUMBER_OF_POINTS, display
	 * an error message (use JOptionPane.showMessageDialog)and ask for it again.
	 */

	public ArrayList<Oval> createASierpinskiTriangle() {
		int n;
		Input in = new Input();
		do {
			n = in.readIntDialog("Enter the number of points (between 1 and " + MAXIMUM_NUMBER_OF_POINTS + ")");
			// If the input is outside of the range, display an error message
			if (n < 1 || n > MAXIMUM_NUMBER_OF_POINTS) {
				JOptionPane.showMessageDialog(null, "Wrong input", "Display of Points", JOptionPane.ERROR_MESSAGE);
			}
		} while (n < 1 || n > MAXIMUM_NUMBER_OF_POINTS);

		// create the SierpinskiTriangle

		Oval p1 = new Oval(WIDTH / 2, 0, 2, 2, Color.BLUE, true);
		Oval p2 = new Oval(0, HEIGHT - 1, 2, 2, Color.BLUE, true);
		Oval p3 = new Oval(WIDTH - 1, HEIGHT - 1, 2, 2, Color.BLUE, true);
		Random rand = new Random();
		Oval p = p1;
		for (int i = 0; i < n; i++) {
			int choice = rand.nextInt(3) + 1;
			int x = 0;
			int y = 0;
			if (choice == 1) {
				x = p1.getX();
				y = p1.getY();
			} else if (choice == 2) {
				x = p2.getX();
				y = p2.getY();
			} else if (choice == 3) {
				x = p3.getX();
				y = p3.getY();
			}
			int midX = (p.getX() + x) / 2;
			int midY = (p.getY() + y) / 2;
			Oval q = new Oval(midX - 1, midY - 1, 2, 2, Color.BLUE, true);
			points.add(q);
			p = q;
		}
		return points;
	}

	/**
	 * Rotate the colors in the pile of disks. Set the color of each disk to the
	 * color of the disk just above it. For the top disk, set its color to the color
	 * of the bottom disk (e.g. with 3 disks, if the colors are from bottom to top,
	 * red, blue, yellow, the new colors of the disks are from bottom to top, blue,
	 * yellow, red).<br>
	 * Precondition: graphicsList describes a pile of disks
	 */

	public ArrayList<Oval> rotateColorsInPileOfDisks(ArrayList<Oval> graphicsList) {
		// store first disk's color
		Color disk1Color = graphicsList.get(0).getColor();
		for (int i = 0; i < graphicsList.size(); i++) {
			// current disk in loop
			Oval disk = graphicsList.get(i);
			// use the next disk's color, if last disk use first one's color
			if (i < graphicsList.size() - 1) {
				// replace color with next disk
				disk.setColor(graphicsList.get(i + 1).getColor());
			} else {
				disk.setColor(disk1Color);
			}
		}
		return graphicsList;
	}

	/**
	 * Flip the 2 colors of the checkboard<br>
	 * Precondition: graphicsList describes a checkered board
	 */

	public ArrayList<Rectangle> flipColorsInCheckeredBoard(ArrayList<Rectangle> graphicsList) {
		for (int i = 0; i < graphicsList.size(); i++) {
			if ((graphicsList.get(i)).getColor() == Color.RED) {
				graphicsList.get(i).setColor(Color.BLACK);
			} else {
				graphicsList.get(i).setColor(Color.RED);
			}
		}
		return graphicsList;
	}

	/**
	 * Change the color of the Sierpinski triangle (all circles should change to the
	 * same color). Switch between 3 colors (e.g. blue->red->green, if the triangle
	 * was blue, make it red, if it was red, make it green, if it was green make it
	 * blue).<br>
	 * Precondition: graphicsList describes a Sierpinski triangle
	 */

	public ArrayList<Oval> changeColorsInSierpinskiTriangle(ArrayList<Oval> graphicsList) {
		Color newColor = Color.BLUE;
		if (!graphicsList.isEmpty()) {
			Color oldColor = graphicsList.get(0).getColor();
			if (oldColor == Color.BLUE) {
				newColor = Color.RED;
			} else if (oldColor == Color.RED) {
				newColor = Color.GREEN;
			} else if (oldColor == Color.GREEN) {
				newColor = Color.BLUE;
			}
		}
		for (int i = 0; i < graphicsList.size(); i++) {
			Oval o = graphicsList.get(i);
			o.setColor(newColor);
		}
		return graphicsList;
	}

	/**
	 * Return the color at location (x,y) in the pile of disks. If (x,y) is not part
	 * of the pile of disks, return null.<br>
	 * Precondition: graphicsList describes a pile of disks
	 */

	public Color getColorInPileOfDisks(int x, int y, ArrayList<Oval> graphicsList) {
		for (int i = graphicsList.size() - 1; i >= 0; i--) {
			Oval o = graphicsList.get(i);
			int radius = (o.getHeight() / 2);
			int dx = x - o.getCenterX();
			int dy = y - o.getCenterY();
			// boundaries
			int distance = dx * dx + dy * dy;
			radius *= radius;
			if (distance <= radius) {
				return o.getColor();
			}
		}
		return null;
	}

	/**
	 * Return the color at location (x,y) in the checkered board. If (x,y) is not
	 * part of the board, return null.<br>
	 * Precondition: graphicsList describes a checkered board
	 */

	public Color getColorInCheckeredBoard(int x, int y, ArrayList<Rectangle> graphicsList) {
		for (int i = 0; i < graphicsList.size(); i++) {
			Rectangle square = graphicsList.get(i);
			// if (x,y) are inside the boundaries, return color.
			if ((x > square.getX() && x < square.getX() + square.getWidth())
					&& (y > square.getY() && y < square.getY() + square.getHeight())) {
				return square.getColor();
			}
		}
		return null;
	}

	/**
	 * Return the color at location (x,y) in the Sierpinski triangle. If (x,y) is
	 * not part of the pile of disks, return null.<br>
	 * Precondition: graphicsList describes a Sierpinski triangle
	 */

	public Color getColorInSierpinskiTriangle(int x, int y, ArrayList<Oval> graphicsList) {
		for (Oval o : graphicsList) {
			double dx = x - o.getCenterX();
			double dy = y - o.getCenterY();
			double distance = (dx*dx) + (dy*dy);
			if (distance <= 1.0) {
				return o.getColor();
			}
		}
		return null;

	}
}