import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class BowmanWorld {
	private static Square[][] grid;
	private final static int groundLevel = 550;
	public BowmanWorld() {
		grid = new Square[BowmanGame.getHeight()][BowmanGame.getLength()];
	}

	public static void setUpGame() {
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
		
		for(int r = groundLevel; r < groundLevel + 10; r++) {
			for(int c = 0; c < grid[0].length; c++) {
				grid[r][c] = new Ground(r,c);
			}
		}
		for(int r = groundLevel - 50; r < groundLevel; r++) {
			for(int c = 100; c < 300; c++) {
				grid[r][c] = new Tank(r,c);
			}
		}
		for(int r = groundLevel - 100; r < groundLevel - 50; r++) {
			for(int c = 160; c < 240; c++) {
				grid[r][c] = new Tank(r,c);
			}
		}
		
		
		for(int r = groundLevel - 50; r < groundLevel; r++) {
			for(int c = 1100; c < 1300; c++) {
				grid[r][c] = new Tank(r,c);
			}
		}
		for(int r = groundLevel - 100; r < groundLevel - 50; r++) {
			for(int c = 1160; c < 1240; c++) {
				grid[r][c] = new Tank(r,c);
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
		// TODO Auto-generated method stub

	}
}
