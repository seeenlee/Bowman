import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class BowmanWorld {
	private static Square[][] grid;
	private final static int groundLevel = 550;
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
		createTank(100);
		createTank(1100);
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
		for(int r = groundLevel - 50; r < groundLevel; r++) {
			for(int c = first; c < first + 200; c++) {
				grid[r][c] = new Tank(r,c);
			}
		}
		for(int r = groundLevel - 100; r < groundLevel - 50; r++) {
			for(int c = first + 60; c < first + 140; c++) {
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
			for( int c = 0; c < grid[r].length; c++) {
				if(grid[r][c] == null) {
					System.out.println(r + " " + c);
				}
				else{
					grid[r][c].draw(g);
				}
			}
		}
	}


	public void justClicked(MouseEvent me) {
	}
	

	public void draggedAt(MouseEvent me) {
		System.out.println(me);
	}

	public void releasedAt(MouseEvent me) {
	}
}
