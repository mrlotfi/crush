package q2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	private Cell[][] cells;
	private boolean isActive;
	private int x,y;
	private Engine engine;
	public  GamePanel(Engine engine) {
		this.engine = engine;
		cells  = engine.getCells();
		isActive = false;
		this.setSize(1000,700);
		setBounds(20,40,800,640);
		
		addMouseListener(new mouseListener());
	}

	
	
	public class mouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent arg0) {
				int row,column;
				row = arg0.getX()/Cell.size +2;
				column = arg0.getY()/Cell.size + 2;
				if(!isActive) {
					isActive = true;
					x = row;
					y= column;
					repaint();
					engine.updatePoints();
				}
				else {
					isActive = false;
					if(Math.abs(x-row)+Math.abs(y-column) <=1) {
						//TODO classe board benivisam
						Cell cell = cells[x][y];
						cells[x][y] = cells[row][column];
						cells[row][column] = cell;
					}
					
					if(engine.checkMove(x, y) || engine.checkMove(row, column)) {
						MoveDoer test = new MoveDoer(engine,x,y,row,column);
						test.start();
						
						if(engine.checkLose()) {
							JOptionPane.showMessageDialog(GamePanel.this, engine.frame.name+" you have lost the game with score "+engine.points);
							engine.frame.dispose();
						}
					}
					else {
						//return jabejaii
						if(Math.abs(x-row)+Math.abs(y-column) <=1) {
							Cell cell = cells[x][y];
							cells[x][y] = cells[row][column];
							cells[row][column] = cell;
						}
						engine.updatePoints();
						repaint();
					}
				}
		}
	}
	
	
	
	public boolean isThereMove() {
		for(int i=0;i<7;i++)
			for(int j=0;j<7;j++);
				//if(cells[i][j].getType());
				return true;
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(isActive) {
			g.setColor(Color.GRAY);
			g.fillRect((x-2)*60, (y-2)*60, 60, 60);
		}
		g.setColor(Color.black);
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				g.setColor(Color.black);
				g.drawRect(i*60, j*60, 60, 60);
				g.setColor(cells[i+2][j+2].getColor());
				g.fillOval(10+i*60, 10+j*60, 40, 40);
				
			}
		}
		
		
	}
}


class MoveDoer extends Thread {
	Engine engine;
	int x,y,row,column;
	public MoveDoer(Engine eng, int x, int y, int row, int column) {
		engine = eng;
		this.x = x;
		this.y = y;
		this.row = row;
		this.column = column;
	}
	public void run() {
		engine.destroyAfterMove(x, y, row, column);
		engine.rearrangeCells();
		engine.refillTable();
		while(engine.clearTable()) 
			engine.refillTable();
		engine.updatePoints();
		JOptionPane.showMessageDialog(null, "dg");
		engine.frame.getPanel().repaint();;
	}
}
