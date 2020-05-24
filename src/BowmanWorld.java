import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class BowmanWorld {
	private static Square[][] grid;
	private final static int groundLevel = 550;
	private boolean p1 = true; //this tells you whether it is player one's turn or player 2
	public static int left = 0;
	public static boolean arrowShot = false;;
	public BowmanWorld() {
		grid = new Square[BowmanGame.getHeight()][BowmanGame.getLength()];
	}

	/**
	 * Fills in the grid with all the possible blocks
	 * Currently it only fills with edgelimits, air, ground, and tanks
	 * I need to work on filling with barrel
	 */
	public static void setUpGame() {
		createEdgeLimitsAndFillWithAir();
		createGround(groundLevel);
		createTank(325);
		createTank(1775);
	}

	private static void createEdgeLimitsAndFillWithAir() {
		for(int r = 0; r < grid.length; r++) {
			for(int c = 0; c < grid[r].length; c++) {
				if(r == 0 || c == 0 || r == grid.length - 1|| c == grid[r].length - 1) {
					grid[r][c] = new EdgeLimit(r,c);
				}
				else {
					grid[r][c] = new Air(r,c);
				}
			}
		}
	}
	private static void createTank(int first) {
		for(int r = groundLevel - 20; r < groundLevel; r++) {
			for(int c = first; c < first + 100; c++) {
				grid[r][c] = new Tank(r,c);
			}
		}
		for(int r = groundLevel - 40; r < groundLevel - 20; r++) {
			for(int c = first + 30; c < first + 70; c++) {
				grid[r][c] = new Tank(r,c);
			}
		}
	}

	private static void createGround(int gL) {
		for(int r = gL; r < gL + 10; r++) {
			for(int c = 0; c < grid[0].length; c++) {
				grid[r][c] = new Ground(r,c);
			}
		}
	}

	public void draw(Graphics g) {
		for(int r = 0; r < grid.length; r++) {
			for( int c = left; c < left + 800; c++) {
				grid[r][c].draw(g);
			}
		}
	}

	private int clickX;
	private int clickY;
	public void justClicked(MouseEvent me) {
		clickX = me.getX();
		clickY = me.getY();
	}


	public void draggedAt(MouseEvent me) {
		System.out.println(me);
	}

	public int releaseX;
	private int releaseY;
	public void releasedAt(MouseEvent me) {
		releaseX = me.getX();
		releaseY = me.getY();
		shootRocket();
	}

	private void shootRocket() {
		screenMove();
	}

	private void screenMove() {
		arrowShot = true;
	}
}
