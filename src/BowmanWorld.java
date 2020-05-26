import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class BowmanWorld {
	private static Square[][] bigGrid;
	private static Square[][] smallGrid;
	private final static int groundLevel = 550;
	private boolean p1 = true; //this tells you whether it is player one's turn or player 2
	public static int arrowXLocation = 325;
	public static boolean arrowShot = false;;
	public BowmanWorld() {
		bigGrid = new Square[BowmanGame.getHeight()][BowmanGame.getLength()];
		smallGrid = new Square[BowmanGame.getHeight()][BowmanGame.getLength() - 1200];
	}

	/**
	 * Fills in the grid with all the possible blocks
	 * Currently it only fills with edgelimits, air, ground, and tanks
	 * I need to work on filling with barrel
	 */
	public static void setUpGame() {
		createEdgeLimitsAndFillWithAir();
		createGround(groundLevel);
		createTank(arrowXLocation);
		createTank(1775);
		fillSmallGrid();
	}

	private static void fillSmallGrid() {
		for(int r = 0; r < smallGrid.length; r++) {
			for(int c = 0; c < smallGrid[r].length; c++) {
				smallGrid[r][c] = bigGrid[r][c];
			}
		}
	}

	private static void createEdgeLimitsAndFillWithAir() {
		for(int r = 0; r < bigGrid.length; r++) {
			for(int c = 0; c < bigGrid[r].length; c++) {
				if(r == 0 || c == 0 || r == bigGrid.length - 1|| c == bigGrid[r].length - 1) {
					bigGrid[r][c] = new EdgeLimit(r,c);
				}
				else {
					bigGrid[r][c] = new Air(r,c);
				}
			}
		}
	}
	private static void createTank(int first) {
		for(int r = groundLevel - 20; r < groundLevel; r++) {
			for(int c = first; c < first + 100; c++) {
				bigGrid[r][c] = new Tank(r,c);
			}
		}
		for(int r = groundLevel - 40; r < groundLevel - 20; r++) {
			for(int c = first + 30; c < first + 70; c++) {
				bigGrid[r][c] = new Tank(r,c);
			}
		}
	}

	private static void createGround(int gL) {
		for(int r = gL; r < gL + 10; r++) {
			for(int c = 0; c < bigGrid[0].length; c++) {
				bigGrid[r][c] = new Ground(r,c);
			}
		}
	}

	public void draw(Graphics g) {
<<<<<<< HEAD
		for(int r = 0; r < grid.length; r++) {
			for( int c = arrowXLocation - 325; c < arrowXLocation + 475; c++) {
				grid[r][c].draw(g);
=======
		updateGrid();
		for(int r = 0; r < smallGrid.length; r++) {
			for( int c = left; c < left + 800; c++) {
				smallGrid[r][c].draw(g);
			}
		}
	}

	private void updateGrid() {
		for(int r = 0; r < smallGrid.length; r++) {
			for( int c = left; c < left + 800; c++) {
				smallGrid[r][c] = bigGrid[r][c];
>>>>>>> 8e580ff579c93d989c33a06a2f72ba9024f8f767
			}
		}
	}

<<<<<<< HEAD
	private static int clickX;
	private static int clickY;
=======
	public int clickX;
	public int clickY;
>>>>>>> 8e580ff579c93d989c33a06a2f72ba9024f8f767
	public void justClicked(MouseEvent me) {
		clickX = me.getX();
		clickY = me.getY();
	}


	public void draggedAt(MouseEvent me) {
		System.out.println(me);
	}

<<<<<<< HEAD
	private static int releaseX;
	private static int releaseY;
=======
	public int releaseX;
	public int releaseY;
>>>>>>> 8e580ff579c93d989c33a06a2f72ba9024f8f767
	public void releasedAt(MouseEvent me) {
		releaseX = me.getX();
		releaseY = me.getY();
	}

	private static int height = Math.abs(releaseY-clickY);
	private static int  distance = Math.abs(releaseY-clickY);
	public static int getAngle() {

		return (int)(Math.atan(height/distance));
	}

	public static int getSpeed() {
		return (int)(Math.sqrt(height^2+distance^2));
	}

	public static void moveRocket() {
		
	}
}
