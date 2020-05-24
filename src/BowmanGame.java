import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BowmanGame implements ActionListener {
	private JFrame frame = new JFrame("Bowman");
	private JPanel panel;
	public static final int OFFSET_X = 40, OFFSET_Y = 20; 
	private final static int LENGTH = 2000;
	private final static int HEIGHT = 600;
	private final Dimension DIM = new Dimension(LENGTH - 1200,HEIGHT);
	private BowmanWorld world = new BowmanWorld();
	private final int FPS = 10;
	public Timer timer = new Timer(1000 / FPS, this);

	/**
	 * This method exists purely to call start
	 * @param args
	 */
	public static void main(String[] args) {
		new BowmanGame().start();
	}

	/**
	 * Getter for the horizontal length of the world
	 * @return
	 */
	public static int getLength() {
		return LENGTH;
	}

	/**
	 * getter for the vertical height of the world
	 * @return
	 */
	public static int getHeight() {
		return HEIGHT;
	}

	/**
	 * It first sets up the game for the user to begin playing and then records all further actions
	 * I don't actually know what all these commands do
	 */
	private void start() {
		BowmanWorld.setUpGame();
		timer.start();
		panel = new JPanel() {
			@Override 
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				world.draw(g);
			}
		};
		panel.setBackground(Color.WHITE);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				clickedAt(me);
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				releasedAt(me);
			}

			@Override
			public void mouseDragged(MouseEvent me) {
				draggedAt(me);
			}
		});
		panel.setPreferredSize(DIM);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		panel.repaint();
	}

	protected void draggedAt(MouseEvent me) {
		world.draggedAt(me);
		System.out.println("this happened");
	}

	protected void releasedAt(MouseEvent me) {
		world.releasedAt(me);
	}

	/**
	 * This method checks if the FPS time limit has passed and then repaints the screen
	 */
	public void actionPerformed(ActionEvent ev){
		if(ev.getSource()==timer){
			panel.repaint();
			if(BowmanWorld.arrowShot == true) {
				if(BowmanWorld.left == LENGTH - 800) {
				}
				else {
					BowmanWorld.left += 25;
				}
			}
		}
	}

	protected void clickedAt(MouseEvent me) {
		world.justClicked(me);
	}
}
