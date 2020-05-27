import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class BowmanWorld {
	private static Square[][] bigGrid; //this is the 2d array that keeps track of all the squares in the game
	private static Square[][] smallGrid; //this is the 2d array that is displayed, it will take values from the bigGrid to display
	private final static int groundLevel = 550; //this is the height where the ground is drawn
	private static int arrowXLocation = 325; //the x value of the location of the arrow
	private static int arrowYLocation = groundLevel; //the y value of the location of the arrow
	private static boolean arrowHasBeenShot = false; //this boolean will show if the arrow has been shot, after it makes contact with ground or edgelimit it needs to be changed back to false
	private static int gravityCounter = 0; //I made this variable to count how much time has passed to calculate the variable of gravity but i think it might end up being useless
	private static int clickX;
	private static int clickY;
	private static int releaseX;
	private static int releaseY;
	private static int diffInY; //this is the difference between clickY and releaseY
	private static int  diffinX; //this is the difference between clickx and release X
	
	
	public BowmanWorld() {
		bigGrid = new Square[BowmanGame.getHeight()][BowmanGame.getLength()];
		smallGrid = new Square[BowmanGame.getHeight()][BowmanGame.getLength() - 1200];
	}
	
	public int getArrowXLocation() {
		return arrowXLocation;
	}
	
	public int getArrowYLocation() {
		return arrowYLocation;
	}
	
	public static boolean getArrowHasBeenShot() {
		return arrowHasBeenShot;
	}
	
	public int getGravityCounter() {
		return gravityCounter;
	}
	
	public static void increaseGravityCounter() {
		gravityCounter++;
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
	
	/**
	 * This grid was supposed to fill the small grid at the beginning
	 * however i think this method is redundant because updategrid gets called the first time through as well
	 */
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
	
	/**
	 * I have no idea how to do this
	 * akshay its all yours
	 */
	private void updateGrid() {
		/*
		for(int r = 0; r < smallGrid.length; r++) {
			for( int c = arrowXLocation - 325; c < arrowXLocation + 475; c++) {
					smallGrid[r][arrowXLocation - c] = bigGrid[r][c];
			}
		}
		*/
	}

	public void justClicked(MouseEvent me) {
		clickX = me.getX();
		clickY = me.getY();
	}

	public void releasedAt(MouseEvent me) {
		releaseX = me.getX();
		releaseY = me.getY();
		arrowHasBeenShot = true;
	}

	public static double findAngle() {
		diffinX  = clickX - releaseX;
		diffInY = releaseY - clickY;
		return Math.atan(diffInY + 0.0/diffinX);
	}

	public static double findSpeed() {
		return Math.sqrt(diffInY^2+diffinX^2);
	}

	public static void moveRocket() {
		arrowXLocation += Math.cos(findAngle()) * findSpeed() * (1.0/15) * 1000;
		arrowYLocation += Math.sin(findAngle()) * findSpeed() * (-1.0/15) - 9.8 * (gravityCounter/15.0);//idk how to calculate the effect gravity this line needs to be changed
		drawRocket();
	}
	
	/**
	 *I made the rocket a square of 5 by 5 for now
	 */
	private static void drawRocket() {
		for(int r = arrowYLocation - 5; r < arrowYLocation; r++) {
			for(int c = arrowXLocation -5; c < arrowXLocation; c++) {
				bigGrid[r][c] = new Rocket(r,c);
			}
		}
	}
}
