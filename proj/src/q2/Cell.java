package q2;

import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	public static final int size = 20;
	private int x,y;
	private int type;
	public Cell(int x, int y, int type) {
		this.x= x;
		this.y= y;
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public Color getColor() {
		switch(type) {
		case 1:
			return Color.black;
		case 2:
			return Color.blue;
		case 3:
			return Color.red;
		case 4:
			return Color.GREEN;
		case 5:
			return Color.MAGENTA;
		case 6:
			return Color.yellow;
		case 0:
			return Color.CYAN;
		}
		return Color.white;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(10+(x-2)*60, 10+(y-2)*60, 40, 40);
	} 
}
