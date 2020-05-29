import java.util.Random;

public class AI {
	
	private boolean firstStrength = true;
	private boolean firstAngle = true;
	private double currStrength = 0;
	private double currAngle = 0;
	
	public double strength(int xvalue) {
		
		if (firstStrength) {
			Random r = new Random();
			firstStrength = false;
			currStrength = r.nextInt(11) + 13;
			return r.nextInt(11) + 13;
		}
		else {
			//get the arrow's position and subtract it from the position of the tank
			
			if ( /* tank - arrow */ > 0) {  //overshot 
				currStrength--;
				return currStrength;
			}
			else {	//undershot or hit
				currStrength++;
				return currStrength;
			}
				
		}
			
		
	}
	
	public double angle(int xvalue) {
		
		if (firstAngle) {
			Random r = new Random();
			firstAngle = false;
			currAngle = 180 - r.nextInt(70) + 1;
			return 180 - r.nextInt(70) + 1;
		}
		else {
			//get the arrow's position and subtract it from the position of the tank
			
			if ( /* tank - arrow */ > 0) {  //overshot 
				currAngle += 5;
				return currAngle;
			}
			else {	//undershot or hit
				currAngle -= 5;
				return currAngle;
			}
				
		}
		
		return 0;
	}
	
	
}