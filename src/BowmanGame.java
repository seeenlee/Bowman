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
	//public static final int OFFSET_X = 40, OFFSET_Y = 20; 
	private final static int LENGTH = 2500;
	private final static int HEIGHT = 600;
	private final Dimension DIM = new Dimension(800,HEIGHT);
	public BowmanWorld world = new BowmanWorld();
	private final int FPS = 10;
	public Timer timer = new Timer(1000 / FPS, this);
	public static int countDown;
	public boolean firstTime = true;

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
		world.setUpGame();
		timer.start();
		panel = new JPanel() {
			@Override 
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//world.saveGraphic(g);
				if(firstTime) {
					world.drawSetUpScreen(g);
				}
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
		});
		panel.setPreferredSize(DIM);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		panel.repaint();
	}

	/**
	 * This method checks if the FPS time limit has passed and then repaints the screen
	 */
	public void actionPerformed(ActionEvent ev){
		int count = 0;
		if(ev.getSource()==timer){
			
			if(countDown > 0) {
				countDown--;
				if(countDown == 0) {
					world.resetArrow();
					world.updateGrid();
				}
				return;
			}
			if(world.getArrowHasBeenShot() == true) {
				world.increaseGravityCounter();
				if(world.getPlayTurn()) {
					world.moveRocket();
				}
				else {
					if(world.getHumanHuman()) {
						world.moveRocket2();
					}
//					else {
//						count++;
//						if(count == 30) {
//							world.moveRocket2AI();
//							count = 0;
//						}
//					}
				}
				world.updateGrid();
			}
			panel.repaint();
		}
	}

	protected void clickedAt(MouseEvent me) {
		if(firstTime) {
			firstTime = false;
		}
		world.justClicked(me);
	}

	protected void releasedAt(MouseEvent me) {
		world.releasedAt(me);
	}

}
