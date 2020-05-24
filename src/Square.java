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
		g.drawRect(col, row, 1, 1);
	}
}
