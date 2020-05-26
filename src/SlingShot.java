import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
// I added getters to BowmanWorld and changed the ints to static to be able to get these values 
public class SlingShot {
private int clickX;
private int clickY;
private int releaseX;
private int releaseY;
private static int speed;
private static int angle;
public SlingShot() { 
	this.clickX = BowmanWorld.getclickX();
	this.clickY = BowmanWorld.getclickY();
	this.releaseX = BowmanWorld.getreleaseX();
	this.releaseY= BowmanWorld.getreleaseY();
	int height = Math.abs(releaseY-clickY);  // i did the absolute value for now we might have to fix this later
	int  distance = Math.abs(releaseY-clickY);
	this.angle =(int)(Math.atan(height/distance)); //angle that is used in fire in radians
	this.speed = (int)(Math.sqrt(height^2+distance^2));   //the speed


}
public static int getAngle() {
	return angle;
}
public static int getSpeed() {
	if(speed>50) {   //I set the max speed to be 50 for now
		return 50;    
	}
	return speed;
}
	

}
