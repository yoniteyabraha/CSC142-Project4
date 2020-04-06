import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.util.*;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import uwcse.graphics.*;

/**
 * Construct a window to display the graphics elements received from
 * GraphicsElements. This class is complete. Don't modify it. It is also not
 * necessary for you to understand this code.<br>
 * To test your program, either select main by right clicking on the class icon,
 * or instantiate a ViewWindow object.
 */

public class ViewWindow extends JPanel implements MouseListener, ActionListener {

	/** Width of the inner panel of this ViewWindow */
	public static final int WINDOW_WIDTH = 400;

	/** Height of the inner panel of this ViewWindow */
	public static final int WINDOW_HEIGHT = 300;

	/** What is currently painted */
	public static final int DISK_PILE = 0;

	public static final int CHECKERED_BOARD = 1;

	public static final int SIERPINSKI = 2;

	private int which;

	// The frame this ViewWindow is in
	private JFrame frame;

	// Other elements in the window
	// Radio buttons (with their title)
	private String[] titles;

	private JRadioButton[] radioButtons;

	// The button to rotate the colors on a GraphicsElements
	private JButton rotateColors;

	// The list of the graphics elements to display
	ArrayList graphicsList;

	// The object that generates the graphics elements to display
	GraphicsElements graphicsElements = new GraphicsElements();

	// Use a popup menu to indicate the color of a point when
	// right clicked by the mouse.
	private JPopupMenu popup;

	private JLabel popupLabel;

	/** Construct a ViewWindow */
	public ViewWindow() {
		// Use a windows look and feel (if available)
		try {
			UIManager.LookAndFeelInfo[] lfinfo = UIManager
					.getInstalledLookAndFeels();
			UIManager.setLookAndFeel(lfinfo[2].getClassName());
		} catch (Exception e) {/* ignore any problem */
		}

		// The components making up the window
		// Radio buttons
		this.titles = new String[] { "Disk pile", "Checkered board",
				"Sierpinski Triangle" };
		this.radioButtons = new JRadioButton[this.titles.length];
		// Only one radio button can be selected at a time
		ButtonGroup buttonGroup = new ButtonGroup();
		for (int i = 0; i < this.radioButtons.length; i++) {
			this.radioButtons[i] = new JRadioButton(this.titles[i]);
			buttonGroup.add(this.radioButtons[i]);
			this.radioButtons[i].addActionListener(this);
		}

		// Button to rotate the colors
		this.rotateColors = new JButton("Change colors");
		this.rotateColors.addActionListener(this);

		// Place the components in this WindowView
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		// at the bottom (SOUTH)
		JPanel southPanel = new JPanel(new GridLayout(2, 1));
		JPanel southPanelFirstRow = new JPanel();
		for (int i = 0; i < this.radioButtons.length; i++)
			southPanelFirstRow.add(this.radioButtons[i]);
		southPanel.add(southPanelFirstRow);
		JPanel southPanelSecondRow = new JPanel();
		southPanelSecondRow.add(this.rotateColors);
		southPanel.add(southPanelSecondRow);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		// Background color of this WindowView
		this.setBackground(Color.white);
		contentPane.add(this, BorderLayout.CENTER);
		// Send all mouse events to this WindowView
		this.addMouseListener(this);
		// Put everything in a frame
		this.frame = new JFrame("Doing graphics with loops");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setContentPane(contentPane);
		// Show it
		this.frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.frame.setLocation(100, 100);
		this.frame.setVisible(true);
		// Resize it with the actual size
		Insets insets = this.frame.getInsets();
		int width = WINDOW_WIDTH + insets.left + insets.right;
		int height = WINDOW_HEIGHT + insets.top + insets.bottom
				+ (int) (southPanel.getPreferredSize().getHeight());
		this.frame.setSize(width, height);
		this.frame.setResizable(false);
		this.frame.setVisible(true);

		// popup menu
		this.popup = new JPopupMenu();
		this.popupLabel = new JLabel("", SwingConstants.CENTER);
		popup.add(popupLabel);
	}

	/** Handle the button clicks */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.radioButtons[0]) {
			// Pile of disks
			this.graphicsList = this.graphicsElements.createAPileOfDisks();
			this.which = ViewWindow.DISK_PILE;
		} else if (e.getSource() == this.radioButtons[1]) {
			// Checkered board
			
			this.graphicsList = this.graphicsElements.createACheckeredBoard();
			
			this.which = ViewWindow.CHECKERED_BOARD;
		} else if (e.getSource() == this.radioButtons[2]) {
			// Create a Sierpinski triangle
			this.graphicsList = this.graphicsElements
					.createASierpinskiTriangle();
			this.which = ViewWindow.SIERPINSKI;
		} else if (e.getSource() == this.rotateColors) {
			// Don't do anything if there is no display
			if (this.graphicsList == null)
				return;

			// Change the colors of the display
			switch (this.which) {
			case ViewWindow.DISK_PILE:
				this.graphicsList = this.graphicsElements
				.rotateColorsInPileOfDisks(this.graphicsList);
				break;
			case ViewWindow.CHECKERED_BOARD:
				this.graphicsList = this.graphicsElements
						.flipColorsInCheckeredBoard(this.graphicsList);
				break;
			case ViewWindow.SIERPINSKI:
				this.graphicsList = this.graphicsElements
						.changeColorsInSierpinskiTriangle(this.graphicsList);
				break;
			}
		} else
			// unknown source
			return;

		// display the new drawing
		this.repaint();
	}

	/** Display this WindowView */
	public void paintComponent(Graphics gfx) {
		super.paintComponent(gfx);
		// If there is nothing to display, stop here
		if (this.graphicsList == null)
			return;

		// Use some graphics2D features (smooth edges)
		Graphics2D g = (Graphics2D) gfx;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// Display the different graphics elements
		if (this.which == ViewWindow.DISK_PILE) {
			// Pile of disks
			Iterator it = this.graphicsList.iterator();
			while (it.hasNext()) {
				Oval circle = (Oval) (it.next());
				g.setColor(circle.getColor());
				g.fillOval(circle.getX(), circle.getY(), circle.getWidth(),
						circle.getHeight());
			}
		} else if (this.which == ViewWindow.CHECKERED_BOARD) {
			// Checkered board
			Iterator it = this.graphicsList.iterator();
			while (it.hasNext()) {
				Rectangle square = (Rectangle) (it.next());
				g.setColor(square.getColor());
				g.fillRect(square.getX(), square.getY(), square.getWidth(),
						square.getHeight());
			}
		} else if (this.which == ViewWindow.SIERPINSKI) {
			// Sierpinski triangle
			Iterator it = this.graphicsList.iterator();
			while (it.hasNext()) {
				Oval circle = (Oval) (it.next());
				g.setColor(circle.getColor());
				g.fillOval(circle.getX(), circle.getY(), circle.getWidth(),
						circle.getHeight());
			}
		}
	}

	/**
	 * Implement MouseListener Different platforms might have different ways to
	 * trigger a popup menu: check all possibilities
	 */
	public void mousePressed(MouseEvent e) {
		checkPopup(e);
	}

	public void mouseClicked(MouseEvent e) {
		checkPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		checkPopup(e);
	}

	/** Get the color at the click location */
	private void checkPopup(MouseEvent e) {
		// Do it only if we have a request for a pop up menu
		if (!e.isPopupTrigger())
			return;
		Color c = null;
		if (this.graphicsList != null) {
			switch (this.which) {
			case ViewWindow.DISK_PILE:
				c = this.graphicsElements.getColorInPileOfDisks(e.getX(), e
						.getY(), this.graphicsList);
				break;
			case ViewWindow.CHECKERED_BOARD:
				c = this.graphicsElements.getColorInCheckeredBoard(e.getX(), e
						.getY(), this.graphicsList);
				break;
			case ViewWindow.SIERPINSKI:
				c = this.graphicsElements.getColorInSierpinskiTriangle(
						e.getX(), e.getY(), this.graphicsList);
				break;
			}
		}

		// Display the color in the label for the popup
		if (c != null)
			this.popupLabel.setText(c.toString());
		else
			this.popupLabel.setText(this.getBackground().toString());
		// Show the label
		this.popup.show(e.getComponent(), e.getX(), e.getY());
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Starts the application
	 */
	public static void main(String[] args) {
		new ViewWindow();
	}
}