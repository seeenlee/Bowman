import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class BowmanWorld {
	private Square[][] grid;
	public BowmanWorld() {
		grid = new Square[BowmanGame.getHeight()][BowmanGame.getLength()];
	}

	public void setUpGame() {
		int r = 0;
		int c = 0;
		while(r < grid.length && c < grid[r].length) {
			if(r == 0 || c == 0 || r == grid.length - 1|| c == grid[r].length - 1) {
				grid[r][c] = new EdgeLimit(r,c);
				c++;
				if(c == grid[r].length - 1) {
					c = 0;
					r++;
				}
			}
		}
	}


	public void draw(Graphics g) {
		for(int r = 0; r < grid.length; r++) {
			for( int c = 0; c < grid[r].length; c++) {
				grid[r][c].draw(g);
			}
		}
	}

	public void justClicked(MouseEvent me) {
		// TODO Auto-generated method stub

	}
}
