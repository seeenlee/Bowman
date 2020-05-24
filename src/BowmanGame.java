import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BowmanGame {
	private JFrame frame = new JFrame("Bowman");
	private JPanel panel;
	public static final int OFFSET_X = 40, OFFSET_Y = 20; 
	private final static int LENGTH = 1500;
	private final static int HEIGHT = 600;
	private final Dimension DIM = new Dimension(LENGTH,HEIGHT);
	private BowmanWorld world = new BowmanWorld();
	
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
		});
		panel.setPreferredSize(DIM);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		panel.repaint();
	}
	protected void clickedAt(MouseEvent me) {
		System.out.println("You just clicked "+me);	
		world.justClicked(me);
		panel.repaint();
	}
}
