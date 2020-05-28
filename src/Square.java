import java.awt.Color;
import java.awt.Graphics;

public abstract class Square {
	private int row;
	private int col;
	private Color color;
	public Square(Color c, int r, int co) {
		color = c;
		row = r;
		col = co;
	}
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(col, row, 1, 1);
		System.out.println("sqaures:" + col);
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return col;
	}
}
