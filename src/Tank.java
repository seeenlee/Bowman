import java.awt.Color;

/**
 * Represents part of the tank
 * @author seanlee
 *
 */
public class Tank extends Square implements Comparable<Tank>{
	
	private int row;
	private int col;
	
	public Tank(int r, int co) {
		super(Color.GREEN, r, co);
	}
	
	@Override
	public int compareTo(Tank tank) {
		if(tank.getCol() == col && tank.getRow() == row) {
			return 1;
		}
		return -1;
	}
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return col;
	}
}
