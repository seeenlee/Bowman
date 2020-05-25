import java.awt.*;
public class fire {
public final double GRAVITY =-9.8;
//dimensions of the projectile, I did 5 and 5 for now 
public final int HEIGHT =5; // 
public final int WIDTH = 5;
//keeps track of the position of the top-left corner of the projectile
private int x;
private int y;

private double speedX;
private double speedY;
private boolean Visible;


public fire(int x, int y, double speed, double angle) { // so the angle is the speed is a value that the user is probabaly going to decide but for now I just left it as somwthing that you enter when you call this method
	this.x = x;
	this.y = y;
	this.speedX = speed * Math.sin(angle); //physics is cool :)
	this.speedY = speed * Math.cos(angle);
	this.Visible =true;
	

}
public void drawRocket() {
	for(int r = x; r< x+WIDTH; r++) {
		for(int c = y; c<c+HEIGHT; c++) {
			BowmanWorld.getGrid()[x][y]= new Rocket(r,c); // I have no idea if this actually works, I just modeled it after the drawTank
		}
	}
	}

public void move() {
		while(x<800 && y<600 & y>=0) { // these are the measurements of the corners I think
			int time = 1;
			x+= speedX;
			y+= speedY;
			speedY+= GRAVITY*time;
			
			this.drawRocket();    // I dont know how to delete the previous rocket that was drawn
			time++;
		}
	if(x<800 && y<600 & y>=0) {
		Visible = false;         // I still need to find a way to make the projectile disapear 
	}
}


}
