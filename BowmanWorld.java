import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class BowmanWorld {
	private  Square[][] bigGrid; //this is the 2d array that keeps track of all the squares in the game
	private  Square[][] smallGrid; //this is the 2d array that is displayed, it will take values from the bigGrid to display
	private final  int groundLevel = 550; //this is the height where the ground is drawn
	private  int arrowXLocation = 325; //the x value of the location of the arrow
	private  int arrowYLocation = groundLevel - 10; //the y value of the location of the arrow
	
	private int arrowYLocation2 = groundLevel - 10;
	
	private int arrowXLocation2 = 1775;
	private  boolean arrowHasBeenShot = false; //this boolean will show if the arrow has been shot, after it makes contact with ground or edgelimit it needs to be changed back to false
	private  double gravityCounter = 0; //I made this variable to count how much time has passed to calculate the variable of gravity but i think it might end up being useless
	private  int clickX;
	private  int clickY;
	private  int releaseX;
	private  int releaseY;
	
	private int clickX2;
	private int clickY2;
	private int releaseX2;
	private int releaseY2;
	private boolean arrow2HasBeenShot = false;
	//private int diffInY; //this is the difference between clickY and releaseY
	//private int  diffinX; //this is the difference between clickx and release X
	private double count = 0;
	private int stopper =1;
	//private int finalXLoc;


	public BowmanWorld() {
		bigGrid = new Square[BowmanGame.getHeight()][BowmanGame.getLength()];
		smallGrid = new Square[BowmanGame.getHeight()][BowmanGame.getLength() - 1675];
	}

	public int getArrowXLocation() {
		return arrowXLocation;
	}

	public int getArrowYLocation() {
		return arrowYLocation;
	}

	public boolean getArrowHasBeenShot() {
		return arrowHasBeenShot;
	}

	public double getGravityCounter() {
		return gravityCounter;
	}

	public void increaseGravityCounter() {
		gravityCounter+= .25;
	}
	/**
	 * Fills in the grid with all the possible blocks
	 * Currently it only fills with edgelimits, air, ground, and tanks
	 * I need to work on filling with barrel
	 */
	public void setUpGame() {
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
	private void fillSmallGrid() {
		for(int r = 0; r < smallGrid.length; r++) {
			for(int c = 0; c < smallGrid[r].length; c++) {
				smallGrid[r][c] = bigGrid[r][c];
			}
		}
	}

	private void createEdgeLimitsAndFillWithAir() {
		for(int r = 0; r < bigGrid.length; r++) {
			for(int c = 0; c < bigGrid[r].length; c++) {
				if(r == 0 || c == 0 || r == bigGrid.length - 1) {
					bigGrid[r][c] = new EdgeLimit(r,c);
				}
				else if(c > 2000 && c < 2005 && r < groundLevel){
					bigGrid[r][c] = new EdgeLimit(r,c);
				}
				else if(r > 0 && c > 100 && r < groundLevel&& c < 2000){
					bigGrid[r][c] = new Air(r,c);
				}
				else {
					bigGrid[r][c] = new TheBigBlackSpace(r,c);
				}
			}
		}
	}

	private void createTank(int first) {
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

	private void createGround(int gL) {
		for(int r = gL; r < gL + 10; r++) {
			for(int c = 0; c < bigGrid[0].length; c++) {
				bigGrid[r][c] = new Ground(r,c);
			}
		}
	}

	public void draw(Graphics g) {
		//
		//updateGrid();
		for(int r = 0; r < smallGrid.length; r++) {
			for( int c = 0; c < smallGrid[r].length; c++) {
				if(c < smallGrid[r].length) {
					smallGrid[r][c].draw(g);

				}
			}
		}

		//	g.drawLine(clickX, clickY, releaseX, releaseY);
	}

	public void updateGrid() {
		//int row = 0;
		//int col = 0;

		//		for(int r = 0; r < smallGrid.length; r++) {
		//			for( int c = arrowXLocation- 325; c < arrowXLocation + 475; c++) {
		//				smallGrid[row][col] = bigGrid[r][c];
		//				if(col < 799) {
		//					col++;
		//				}
		//				else {
		//				}
		//			}
		//			row++;
		//		}
		//		for(int r = 0; r < smallGrid.length; r++) {
		//			for( int c = 0; c < smallGrid[r].length; c++) {
		//					smallGrid[r][c] = bigGrid[r][c + (arrowXLocation - c)];
		//			}
		//		}
		//int x = arrowXLocation -325;
		for(int r = 0; r<smallGrid.length;r++) {
			//for(int c=arrowXLocation-325; c<arrowXLocation+475; c++) {
			for(int c=0; c < smallGrid[r].length; c++) {

				//	smallGrid[r][c-arrowXLocation+324] = smallGrid[r][c-arrowXLocation+325];

				try {
					smallGrid[r][c] = bigGrid[r][arrowXLocation -325 + c];
					smallGrid[r][c].setCol(c);
					//System.out.println("c:" + c);
				}
				catch(Exception e) {
					e.printStackTrace();	
					return;
				}

			}
		}

		/*	for(int r = 0; r < smallGrid.length; r++) {
			for( int c = arrowXLocation - 325; c < arrowXLocation + 475; c++) {
					smallGrid[r][c -arrowXLocation+325] = bigGrid[r][c];

			}
		}*/
		drawRocket();
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
	/*public void paintLine(Graphics g) {
	g.drawLine(clickX, clickY, releaseX, releaseY);
}
	 */
//	private double diffinX  = clickX - releaseX;
//	private double diffInY = releaseY - clickY;
	public double findAngle() {
		double diffinX  = clickX - releaseX;
		double diffInY = releaseY - clickY;
		if(diffinX!=0) {

			return Math.atan((diffInY/diffinX));

		}
		return -1;
	}

	public double findSpeed() {

		int diffX = clickX - releaseX;
		int diffY = releaseY - clickY;
		//return  Math.sqrt(diffY^2+diffX^2)*1.5;
		//return  Math.sqrt((diffY * diffY) +(diffX * diffX))*1.5;
		
		
		//System.out.println("speed:"+Math.sqrt(Math.pow(diffinX, 2.0) + Math.pow(diffInY, 2.0)) * .05);
		String s = String.format("%.2f", Math.pow(diffX, 2.0) + Math.pow(diffY, 2.0) * .05);
		return Math.sqrt(Math.pow(diffX, 2.0) + Math.pow(diffY, 2.0)) * .05;
		//return Math.sqrt(Math.pow(diffinX, 2.0) + Math.pow(diffInY, 2.0)) * .05;

	}


	public void moveRocket() {

		count+= .25;
		//increaseGravityCounter();

		arrowYLocation += -(Math.sin(findAngle()) * findSpeed() -gravityCounter);
		String s = String.format("%.2f",  -(Math.sin(findAngle()) * findSpeed() -gravityCounter));
		System.out.println("adding:" + s);
		arrowXLocation += (Math.cos(findAngle()) * findSpeed());
		System.out.println(arrowXLocation + "," + arrowYLocation);
		//arrowXLocation++;
		//System.out.println("x:" +arrowXLocation);
		//System.out.println("y:"+ arrowYLocation);
				if(arrowXLocation > 1999) {
					arrowXLocation = 1999;
					arrowHasBeenShot = false;
				}
		if(arrowYLocation > groundLevel) {
			arrowYLocation = groundLevel;
			arrowHasBeenShot = false;
			//finalXLoc = arrowXLocation;
		}





		//drawRocket();



	}
	
	
	public void justClicked2(MouseEvent me) {
		clickX2 = me.getX();
		clickY2 = me.getY();
	}

	public void releasedAt2(MouseEvent me) {
		releaseX2 = me.getX();
		releaseY2 = me.getY();

		arrow2HasBeenShot = true;


	}
	
	public double findAngle2() {
		double diffinX  = clickX2 - releaseX2;
		double diffInY = releaseY2 - clickY2;
		if(diffinX!=0) {

			return Math.atan((diffInY/diffinX));

		}
		return -1;
	}
	
	public double findSpeed2() {

		int diffX = clickX2 - releaseX2;
		int diffY = releaseY2 - clickY2;
		//return  Math.sqrt(diffY^2+diffX^2)*1.5;
		//return  Math.sqrt((diffY * diffY) +(diffX * diffX))*1.5;
		
		
		//System.out.println("speed:"+Math.sqrt(Math.pow(diffinX, 2.0) + Math.pow(diffInY, 2.0)) * .05);
		String s = String.format("%.2f", Math.pow(diffX, 2.0) + Math.pow(diffY, 2.0) * .05);
		return Math.sqrt(Math.pow(diffX, 2.0) + Math.pow(diffY, 2.0)) * .05;
		//return Math.sqrt(Math.pow(diffinX, 2.0) + Math.pow(diffInY, 2.0)) * .05;

	}
	
	
	public void moveRocket2() {
		count += .25;	
		
		arrowYLocation += -(Math.sin(/*player 2/ai .getangle*/) * /*player 2/ai .getstrength*/ -gravityCounter);
		String s = String.format("%.2f",  -(Math.sin(/*player 2/ai .getangle*/) * /*player 2/ai .getstrength*/ -gravityCounter));
		
		System.out.println("adding p2:" + s);
		
		
		arrowXLocation -= (Math.cos(/*player 2/ai .getangle*/) * /*player 2/ai .getstrength*/);
		
		System.out.println("p2 coords: " + arrowXLocation2 + "," + arrowYLocation);
		//arrowXLocation++;
		//System.out.println("x:" +arrowXLocation);
		//System.out.println("y:"+ arrowYLocation);
				if(arrowXLocation < 1) {
					arrowXLocation = 1;
					arrow2HasBeenShot = false;
				}
		if(arrowYLocation > groundLevel) {
			arrowYLocation = groundLevel;
			arrow2HasBeenShot = false;
			//finalXLoc = arrowXLocation;
		}
	}


	/**
	 *I made the rocket a square of 5 by 5 for now
	 */
	private void drawRocket() {
		for(int r = arrowYLocation - 5; r < arrowYLocation; r++) {
			for(int c = 320; c < 325; c++) {

				smallGrid[r][c] = new Rocket(r,c);
				//System.out.println("drawing:"+ r);
			}
		}
	}
}
