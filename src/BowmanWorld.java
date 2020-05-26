import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class BowmanWorld {
	private static Square[][] bigGrid;
	private static Square[][] smallGrid;
	private final static int groundLevel = 550;
	private boolean p1 = true; //this tells you whether it is player one's turn or player 2
	public static int arrowXLocation = 325;
	public static int arrowYLocation = groundLevel;
	public static boolean arrowShot = false;
	public static int gravityCounter = 0;
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
		updateGrid();
		for(int r = 0; r < smallGrid.length; r++) {
			for( int c = arrowXLocation - 325; c < arrowXLocation +475; c++) {
				if(c < smallGrid[r].length) {
					smallGrid[r][c].draw(g);
				}
			}
		}
	}

	private void updateGrid() {
		/*
		for(int r = 0; r < smallGrid.length; r++) {
			for( int c = arrowXLocation - 325; c < arrowXLocation + 475; c++) {
					smallGrid[r][arrowXLocation - c] = bigGrid[r][c];
			}
		}
		*/
	}


	private static int clickX;
	private static int clickY;

	public void justClicked(MouseEvent me) {
		clickX = me.getX();
		clickY = me.getY();
	}


	public void draggedAt(MouseEvent me) {
		System.out.println(me);
	}


	private static int releaseX;
	private static int releaseY;

	public void releasedAt(MouseEvent me) {
		releaseX = me.getX();
		releaseY = me.getY();
		arrowShot = true;
	}

	private static int height;
	private static int  distance;
	public static double getAngle() {
		distance  = clickX - releaseX;
		height = releaseY - clickY;
		return Math.atan(height + 0.0/distance);
	}

	public static double getSpeed() {
		return Math.sqrt(height^2+distance^2);
	}

	public static void moveRocket() {
		arrowXLocation += Math.cos(getAngle()) * getSpeed() * (1.0/15) * 1000;
		arrowYLocation += Math.sin(getAngle()) * getSpeed() * (-1.0/15) - 9.8 * (gravityCounter/15.0);
		drawRocket();
	}

	private static void drawRocket() {
		for(int r = arrowYLocation - 5; r < arrowYLocation; r++) {
			for(int c = arrowXLocation -5; c < arrowXLocation; c++) {
				bigGrid[r][c] = new Rocket(r,c);
			}
		}
	}
}
