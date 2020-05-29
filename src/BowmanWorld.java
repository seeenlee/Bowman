import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import java.awt.Point;
import java.lang.Thread;

public class BowmanWorld {
	private  Square[][] bigGrid; //this is the 2d array that keeps track of all the squares in the game
	private  Square[][] smallGrid; //this is the 2d array that is displayed, it will take values from the bigGrid to display
	private final  int groundLevel = 550; //this is the height where the ground is drawn
	private  int arrowXLocation = 600; //the x value of the location of the arrow
	private  int arrowYLocation = groundLevel - 10; //the y value of the location of the arrow
	private  boolean arrowHasBeenShot = false; //this boolean will show if the arrow has been shot, after it makes contact with ground or edgelimit it needs to be changed back to false
	private  double gravityCounter = 0; //I made this variable to count how much time has passed to calculate the variable of gravity but i think it might end up being useless
	private  int clickX;
	private  int clickY;
	private  int releaseX;
	private  int releaseY;
	//private int diffInY; //this is the difference between clickY and releaseY
	//private int  diffinX; //this is the difference between clickx and release X
	//private double count = 0;
	//private int stopper =1;
	//private int finalXLoc;
	private final int rightLimit = 2000;
	private final int leftLimit = 326;
	private boolean playTurn = true; //true is player one, false is player 2
	private boolean humanhuman = true; //true is 2 player, false is bot
	private ArrayList<Point> tankLocations = new ArrayList<Point>();
	private int tankOneLocation = 500;
	private int tankTwoLocation = 1775;
	private int lastShot;
	private boolean firstStrength = true;
	private boolean firstAngle = true;
	private double currStrength = 0;
	private double currAngle = 0;

	public BowmanWorld() {
		bigGrid = new Square[BowmanGame.getHeight()][BowmanGame.getLength()];
		smallGrid = new Square[BowmanGame.getHeight()][BowmanGame.getLength() - 1675];
	}

	public int getLastShot() {
		return lastShot;
	}

	public boolean getHumanHuman() {
		return humanhuman;
	}

	public boolean getPlayTurn() {
		return playTurn;
	}

	public int getArrowXLocation() {
		return arrowXLocation;
	}

	public int getArrowYLocation() {
		return arrowYLocation;
	}

	public void setArrowXLocation(int input) {
		arrowXLocation = input;
	}

	public void setArrowYLocation(int input) {
		arrowYLocation = input;
	}

	public boolean getArrowHasBeenShot() {
		return arrowHasBeenShot;
	}

	public double getGravityCounter() {
		return gravityCounter;
	}

	public void increaseGravityCounter() {
		gravityCounter += .5;
	}

	public void resetGravityCounter() {
		gravityCounter = 0;
	}
	/**
	 * Fills in the grid with all the possible blocks
	 * Currently it only fills with edgelimits, air, ground, and tanks
	 * I need to work on filling with barrel
	 */
	public void setUpGame() {
		createEdgeLimitsAndFillWithAir();
		createGround(groundLevel);
		createTank(tankOneLocation);
		createTank(tankTwoLocation);
		//fillSmallGrid();
		updateGrid();
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
				if(r == 0 || /*c == leftLimit ||*/ r == groundLevel) {
					//bigGrid[r][c] = new EdgeLimit(r,c);
					bigGrid[r][c] = new Air(r,c);
				}
//				else if(c > rightLimit && c < rightLimit + 5 && r < groundLevel){
//					//bigGrid[r][c] = new EdgeLimit(r,c);
//					bigGrid[r][c] = new Air(r,c);
//				}
				else if(r > 0 && c > leftLimit && r < groundLevel&& c < rightLimit){
					bigGrid[r][c] = new Air(r,c);
				}
				else {
					bigGrid[r][c] = new TheBigBlackSpace(r,c);
				}
			}
		}
	}

	private boolean hitBarrier(int c, int r) {
//		if(playTurn) {
//			if(r > groundLevel - 20 && r < groundLevel && c > tankOneLocation && c < tankOneLocation + 100) {
//				return true;
//			}
//			else if(r > groundLevel - 40 && r < groundLevel - 20 && c > tankOneLocation + 30 && c < tankOneLocation + 70) {
//				return true;
//			}
//		}
//		if(!playTurn) {
//			if(r > groundLevel - 20 && r < groundLevel && c > tankTwoLocation && c < tankTwoLocation + 100) {
//				return true;
//			}
//			else if(r > groundLevel - 40 && r < groundLevel - 20 && c > tankTwoLocation + 30 && c < tankTwoLocation + 70) {
//				return true;
//			}
//		}
		if(c > rightLimit) {
			return true;
		}
		if(c < leftLimit) {
			return true;
		}
		if(r < 1) {
			return true;
		}
		if(r > groundLevel) {
			return true;
		}
		return false;
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
		if(BowmanGame.countDown > 0) {
			g.drawString("bruh", 350, 350);
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
					System.out.println(c);
					System.out.println(arrowXLocation);
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
		//System.out.println(s);
		return Math.sqrt(Math.pow(diffX, 2.0) + Math.pow(diffY, 2.0)) * .05;
		//return Math.sqrt(Math.pow(diffinX, 2.0) + Math.pow(diffInY, 2.0)) * .05;

	}


	public void moveRocket() {

		//count+= .25;
		//increaseGravityCounter();
		arrowYLocation -= (Math.sin(findAngle()) * findSpeed() -gravityCounter);
		String s = String.format("%.2f",  -(Math.sin(findAngle()) * findSpeed() -gravityCounter));
		//System.out.println("adding:" + s);
		arrowXLocation += (Math.cos(findAngle()) * findSpeed());
		//System.out.println(arrowXLocation + "," + arrowYLocation);
		//arrowXLocation++;
		//System.out.println("x:" +arrowXLocation);
		//System.out.println("y:"+ arrowYLocation);
//		if(arrowXLocation > rightLimit) {
//			//arrowXLocation = rightLimit;
//			//arrowHasBeenShot = false;
//			turnOver();
//		}
//		if(arrowYLocation > groundLevel) {
//			//arrowYLocation = groundLevel;
//			//arrowHasBeenShot = false;
//			//finalXLoc = arrowXLocation;
//			turnOver();
//		}
		if(hitBarrier(arrowXLocation, arrowYLocation)) {
			turnOver();
			return;
		}
		//		findTanks();
		//		for(int i = 0; i < tankLocations.size(); i++) {
		//			if(new Point(arrowXLocation, arrowYLocation).equals(tankLocations.get(i))) {
		//				turnOver();
		//			}
		//		}
		//drawRocket();
	}


	private void findTanks() {
		for(int r= 0; r < bigGrid.length; r++) {
			for(int c = 0; c < bigGrid[r].length; c++) {
				try {
					if(bigGrid[r][c].equals(new Tank(0,0)));
					tankLocations.add(new Point(c,r));
				}
				catch(Exception e) {
					continue;
				}
			}
		}
	}
	
	private void turnOver() {
		// TODO Auto-generated method stub
		arrowHasBeenShot = false;
		lastShot = arrowXLocation;
		resetGravityCounter();
		playTurn = !playTurn;
		BowmanGame.countDown = 20;
	}
	
	public void resetArrow() {
		if(playTurn == true) {
			arrowXLocation = 600;
			arrowYLocation = groundLevel - 10;
		}
		if(playTurn == false) {
			arrowXLocation = 1775;
			arrowYLocation = groundLevel - 10;
			if(humanhuman == false) {
				arrowHasBeenShot = true;
			}
		}
	}

//	public void moveRocket2() {
//		increaseGravityCounter();	
//		//System.out.println("called");
//		arrowYLocation += -(Math.sin(findAngle()) * findSpeed() -gravityCounter);
//		String s = String.format("%.2f",  -(Math.sin(findAngle()) * findSpeed() -gravityCounter));
//
//		//System.out.println("adding p2:" + s);
//
//
//		arrowXLocation -= (Math.cos(findAngle()) * findSpeed());
//
//		//System.out.println("p2 coords: " + arrowXLocation + "," + arrowYLocation);
//		//arrowXLocation++;
//		//System.out.println("x:" +arrowXLocation);
//		//System.out.println("y:"+ arrowYLocation);
////		if(arrowXLocation > rightLimit) {
////			//arrowXLocation = rightLimit;
////			//arrowHasBeenShot = false;
////			turnOver();
////		}
////		if(arrowYLocation > groundLevel) {
////			//arrowYLocation = groundLevel;
////			//arrowHasBeenShot = false;
////			//finalXLoc = arrowXLocation;
////			turnOver();
////		}
//		if(hitBarrier(arrowXLocation, arrowYLocation)) {
//			turnOver();
//		}
//	}
//
//	public void moveRocket2AI() {
//		increaseGravityCounter();	
//		//System.out.println("called");
//		arrowYLocation += -(Math.sin(angle()) * strength() -gravityCounter);
//		String s = String.format("%.2f",  -(Math.sin(angle()) * strength() -gravityCounter));
//
//		//System.out.println("adding p2:" + s);
//
//
//		arrowXLocation -= (Math.cos(angle()) * strength());
//
//		//System.out.println("p2 coords: " + arrowXLocation + "," + arrowYLocation);
//		if(arrowXLocation > rightLimit) {
//			//arrowXLocation = rightLimit;
//			//arrowHasBeenShot = false;
//			turnOver();
//		}
//		if(arrowYLocation > groundLevel) {
//			//arrowYLocation = groundLevel;
//			//arrowHasBeenShot = false;
//			//finalXLoc = arrowXLocation;
//			turnOver();
//		}
//		if(hitBarrier(arrowXLocation, arrowYLocation)) {
//			turnOver();
//		}
//	}
	
//	public void justClicked2(MouseEvent me) {
//		clickX2 = me.getX();
//		clickY2 = me.getY();
//	}
//
//	public void releasedAt2(MouseEvent me) {
//		releaseX2 = me.getX();
//		releaseY2 = me.getY();
//
//		arrowHasBeenShot = true;
//
//
//	}
	
	public double findAngle2() {
		double diffinX  = clickX - releaseX;
		double diffInY = releaseY - clickY;
		if(diffinX!=0) {

			return Math.atan((-diffInY/diffinX));

		}
		return -1;
	}
	
	public double findSpeed2() {

		int diffX = releaseX - clickX;
		int diffY = releaseY - clickY;
		//return  Math.sqrt(diffY^2+diffX^2)*1.5;
		//return  Math.sqrt((diffY * diffY) +(diffX * diffX))*1.5;
		
		
		//System.out.println("speed:"+Math.sqrt(Math.pow(diffinX, 2.0) + Math.pow(diffInY, 2.0)) * .05);
		String s = String.format("%.2f", Math.pow(diffX, 2.0) + Math.pow(diffY, 2.0) * .05);
		return Math.sqrt(Math.pow(diffX, 2.0) + Math.pow(diffY, 2.0)) * .05;
		//return Math.sqrt(Math.pow(diffinX, 2.0) + Math.pow(diffInY, 2.0)) * .05;

	}
	
	
	public void moveRocket2() {
		
		increaseGravityCounter();
		arrowYLocation -= (Math.sin(findAngle2()) * findSpeed2() -gravityCounter);
		String s = String.format("%.2f",  -(Math.sin(findAngle2()) * findSpeed2() -gravityCounter));
		
		arrowXLocation -= (Math.cos(findAngle2()) * findSpeed2());
		if(hitBarrier(arrowXLocation, arrowYLocation)) {
			turnOver();
		}
	}

	/**
	 *I made the rocket a square of 5 by 5 for now
	 */
	public void drawRocket() {
		for(int r = arrowYLocation - 5; r < arrowYLocation; r++) {
			for(int c = 320; c < 325; c++) {

				smallGrid[r][c] = new Rocket(r,c);
				//System.out.println("drawing:"+ r);
			}
		}
	}

	public double strength() {

		if (firstStrength) {
			Random r = new Random();
			firstStrength = false;
			currStrength = r.nextInt(20000) + 240000;
			return r.nextInt(20000) + 240000;
		}
		else {
			//get the arrow's position and subtract it from the position of the tank
			if(playTurn) {
				if (lastShot - tankOneLocation> 0) {  //overshot 
					currStrength--;
					return currStrength;
				}
				else {	//undershot or hit
					currStrength++;
					return currStrength;
				}
			}
			else {
				if (lastShot - tankTwoLocation> 0) {  //overshot 
					currStrength--;
					return currStrength;
				}
				else {	//undershot or hit
					currStrength++;
					return currStrength;
				}
			}
		}
	}

	public double angle() {

		if (firstAngle) {
			Random r = new Random();
			firstAngle = false;
			currAngle = r.nextInt(70) + 1;
			return r.nextInt(70) + 1;
		}
		else {
			//get the arrow's position and subtract it from the position of the tank
			if(playTurn) {
				if (lastShot - tankTwoLocation> 0) {  //overshot 
					currAngle -= 5;
					return currAngle;
				}
				else {	//undershot or hit
					currAngle += 5;
					return currAngle;
				}
			}
			else {
				if (lastShot - tankTwoLocation> 0) {  //overshot 
					currAngle -= 5;
					return currAngle;
				}
				else {	//undershot or hit
					currAngle += 5;
					return currAngle;
				}
			}
		}
	}

	public void drawSetUpScreen(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
