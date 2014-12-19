package q2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("serial")
public class GamePanel extends JPanel {// in felan hame chi has. ham panel ham engine :D
	private Cell[][] cells;
	private boolean isActive;
	private int x,y;
	JButton k;
	public  GamePanel() {
		// TODO Auto-generated constructor stub
		cells  = new Cell[13][13];
		initializeCells();
		isActive = false;
		this.setSize(1000,700);
		setBounds(10,10,800,640);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row,column;
				row = arg0.getX()/60 +2;
				column = arg0.getY()/60 + 2;
				if(!isActive) {
					isActive = true;
					x = row;
					y= column;
					repaint();
				}
				else {
					isActive = false;
					if(Math.abs(x-row)+Math.abs(y-column) <=1) {
						Cell cell = cells[x][y];
						cells[x][y] = cells[row][column];
						cells[row][column] = cell;
					}
					
					destroyAfterMove(x,y,row,column);
					while(clearTable()) 
						refillTable();
					repaint();
				}
			}
		});
	}

	public void rearrangeCells() {
		for(int i=2;i<12;i++) 
			shiftAndInsert(i);
	}
	
	public boolean clearTable() {
		boolean ret = false;
		for(int x1=2;x1<12;x1++) {
			for(int y1=2;y1<12;y1++) {
				if(cells[x][y1].getType() != -1) {
				int a = 0,b=0;boolean his = false;
				while(cells[x1][y1].getType() == cells[x1-a][y1].getType())
					a++;
				while(cells[x1][y1].getType() == cells[x1+b][y1].getType())
					b++;
				a--;b--;
				if(a+b>=2) {
					his = true;
					ret= true;
					for(int i=(-1)*a;i<=b;i++) {
						if(i!=0)
							cells[x1+i][y1].setType(-1);;
					}
				}
				a=b=0;
				while(cells[x1][y1].getType() == cells[x1][y1-a].getType())
					a++;
				while(cells[x1][y1].getType() == cells[x1][y1+b].getType())
					b++;
				a--;b--;
				if(a+b>=2) {
					ret = true;
					his = true;
					for(int i=(-1)*a;i<=b;i++) {
						if(i!=0)
							cells[x1][y1+i].setType(-1);
					}
				}
				if(his)
					cells[x1][y1].setType(-1);
				}
			}
		}
		if(ret)
			rearrangeCells();
		return ret;
	}
	
	public void shiftAndInsert(int row) {
		int s=0;
		while(s<9) {
			if(cells[row][10-s].getType() != -1)
				s++;
			else {
				
					for(int i=s;i<9;i++)//shift
						cells[row][10-i] = cells[row][10-(i+1)];
			}
		}
		refillTable();
	}
	
	public void refillTable() {
		for(int i=2;i<12;i++)
			for(int j=2;j<12;j++)
				if(cells[i][j].getType() == 0) 
					cells[i][j] = new Cell(i,j,new Random().nextInt(6)+1);
	}
	public void destroyAfterMove(int x1, int y1, int x2, int y2) {
		int a = 0,b=0;boolean his = false;
		while(cells[x1][y1].getType() == cells[x1-a][y1].getType())
			a++;
		while(cells[x1][y1].getType() == cells[x1+b][y1].getType())
			b++;
		a--;b--;
		if(a+b>=2) {
			his = true;
			for(int i=(-1)*a;i<=b;i++) {
				if(i!=0)
					cells[x1+i][y1].setType(-1);;
			}
		}
		a=b=0;
		while(cells[x1][y1].getType() == cells[x1][y1-a].getType())
			a++;
		while(cells[x1][y1].getType() == cells[x1][y1+b].getType())
			b++;
		a--;b--;
		if(a+b>=2) {
			his = true;
			for(int i=(-1)*a;i<=b;i++) {
				if(i!=0)
					cells[x1][y1+i].setType(-1);
			}
		}
		if(his)
			cells[x1][y1].setType(-1);
		//
		a=b=0;his = false;
		while(cells[x2][y2].getType() == cells[x2-a][y2].getType())
			a++;
		while(cells[x2][y2].getType() == cells[x2+b][y2].getType())
			b++;
		a--;b--;
		if(a+b>=2) {
			his = true;
			for(int i=(-1)*a;i<=b;i++) {
				if(i!=0)
					cells[x2+i][y2].setType(-1);
			}
		}
		a=b=0;
		while(cells[x2][y2].getType() == cells[x2][y2-a].getType())
			a++;
		while(cells[x2][y2].getType() == cells[x2][y2+b].getType())
			b++;
		a--;b--;
		if(a+b>=2) {
			his = true;
			for(int i=(-1)*a;i<=b;i++) {
				if(i!=0)
					cells[x2][y2+i].setType(-1);
			}
		}
		if(his)
			cells[x2][y2].setType(-1);;
		// alan khalia manfie yekan
		rearrangeCells();
	}
	
	public boolean isThereMove() {
		for(int i=0;i<7;i++)
			for(int j=0;j<7;j++);
				//if(cells[i][j].getType());
				return true;
	}
	
	public void initializeCells() {
		Random r = new Random();
		int rand;
		for(int i=0;i<13;i++) {
			for(int j=0;j<13;j++) {
				if(i<=1 || i>10 || j<=1 || j>10)
						cells[i][12-j] = new Cell(i,12-j,0);// doresho sefr mizerim
				else {
						rand = r.nextInt(6) + 1;
						if(rand == cells[i-1][12-j].getType() && cells[i-2][12-j].getType()==cells[i-1][12-j].getType())
							rand = ((rand+1) % 6) +1;
						if(rand == cells[i][13-j].getType() && cells[i][13-j].getType() == cells[i][14-j].getType())
							rand = ((rand+1) % 6) +1;
						cells[i][12-j] = new Cell(i,12-j,rand);
				}
			}
		}
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