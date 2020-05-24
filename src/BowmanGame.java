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
	private final static int LENGTH = 801;
	private final static int HEIGHT = 601;
	private final Dimension DIM = new Dimension(LENGTH + OFFSET_X,HEIGHT + OFFSET_Y);
	private BowmanWorld world = new BowmanWorld();
	
	public static void main(String[] args) {
		new BowmanGame().start();
	}
	
	public static int getLength() {
		return LENGTH;
	}
	
	public static int getHeight() {
		return HEIGHT;
	}

	private void start() {
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
