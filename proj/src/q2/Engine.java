package q2;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.sun.swing.internal.plaf.synth.resources.synth;

public class Engine {
	GameFrame frame;
	int points;
	private Cell[][] cells;
	public Engine(GameFrame frame) {
		this.frame = frame;
		cells  = new Cell[13][13];
		initializeCells();
		while(checkLose()) {
			cells  = new Cell[13][13];
			initializeCells();
		}
			
	}
	
	public  boolean checkLose() {
		for(int i=2;i<11;i++) 
			for(int j=2;j<11;j++) {
				Cell cell = cells[i][j];
				cells[i][j] = cells[i][j+1];
				cells[i][j+1] = cell;
				if((checkMove(i, j) || checkMove(i,j+1))) {
					cell = cells[i][j]; //reswap
					cells[i][j] = cells[i][j+1];
					cells[i][j+1] = cell;
					return false;
				}
				cell = cells[i][j]; //reswap
				cells[i][j] = cells[i][j+1];
				cells[i][j+1] = cell;
				//
				cell = cells[i][j]; //reswap
				cells[i][j] = cells[i+1][j];
				cells[i+1][j] = cell;
				if((checkMove(i, j) || checkMove(i+1,j)) && cells[i][j].getType() != 0 && cells[i+1][j].getType() != -1) {
					cell = cells[i][j]; //reswap
					cells[i][j] = cells[i+1][j];
					cells[i+1][j] = cell;
					return false;
				}
				cell = cells[i][j]; //reswap
				cells[i][j] = cells[i+1][j];
				cells[i+1][j] = cell;
			}
		return true;
	}
	
	
	public Cell[][] getCells() {
		return cells;
	}
/*	public void increasePoints(int i) {
		points += i;
	}*/
	public  void updatePoints() {
		frame.score.setText("You scored: "+points);
		frame.repaint();
	}
	public  boolean checkMove(int x1, int y1) {
		int a = 0,b=0;
		while(x1-a>0 &&cells[x1][y1].getType() == cells[x1-a][y1].getType())
			a++;
		while(cells[x1][y1].getType() == cells[x1+b][y1].getType())
			b++;
		a--;b--;
		if(a+b>=2) 
			return true;
		a=b=0;
		while(y1-a>0 && cells[x1][y1].getType() == cells[x1][y1-a].getType())
			a++;
		while(cells[x1][y1].getType() == cells[x1][y1+b].getType())
			b++;
		a--;b--;
		if(a+b>=2) 
			return true;
		return false;
	}
	public  void destroyAfterMove(int x1, int y1, int x2, int y2) {
		int a = 0,b=0,c=0,d=0;;boolean his = false;
		int ted = 0;
		while(cells[x1][y1].getType() == cells[x1-a][y1].getType())
			a++;
		while(cells[x1][y1].getType() == cells[x1+b][y1].getType())
			b++;
		a--;b--;
		if(a+b>=2) {
			his = true;
			for(int i=(-1)*a;i<=b;i++) {
				if(i!=0)
					cells[x1+i][y1].setType(-1);// i==0 ro hanuz mikhaim salem bashe
			}
			ted += a+b;
		}
		while(cells[x1][y1].getType() == cells[x1][y1-c].getType())
			c++;
		while(cells[x1][y1].getType() == cells[x1][y1+d].getType())
			d++;
		c--;d--;
		if(c+d>=2) {
			his = true;
			for(int i=(-1)*c;i<=d;i++) {
				if(i!=0)
					cells[x1][y1+i].setType(-1);
			}
			ted += c+d;
		}
		if(his){
			cells[x1][y1].setType(-1);
			points += (ted-1) * 10;
		}
		//
		a=b=c=d=0;his = false; ted=0;
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
			ted += a+b;
		}
		while(cells[x2][y2].getType() == cells[x2][y2-c].getType())
			c++;
		while(cells[x2][y2].getType() == cells[x2][y2+d].getType())
			d++;
		c--;d--;
		if(c+d>=2) {
			his = true;
			for(int i=(-1)*c;i<=d;i++) {
				if(i!=0)
					cells[x2][y2+i].setType(-1);
			}
			ted+= c+d;
		}
		if(his) {
			cells[x2][y2].setType(-1);
			points += (ted+1-2)*10;
		}
		System.out.println(points);
		// alan khalia manfie yekan
	}
	public  void initializeCells() {
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
	public   void shift(int r) {
		animation anim = new animation(frame.getPanel(), r);
		anim.start();
		try {
			anim.waitForFinish();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().toString());
	}
	
	class animation extends Thread {
		GamePanel panel;
		boolean completed;
		int row;
		public animation(GamePanel panel, int row) {
			this.panel = panel;
			completed = false;
			this.row = row;
		}
		public synchronized void run() {
			final int row = this.row;
			int s=0;
			while(s<9) {
				if(cells[row][10-s].getType() != -1)
					s++;
				else {
						for(int i=s;i<9;i++){// shift
							
							cells[row][10-i] = cells[row][10-(i+1)];
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							panel.repaint();
							}		
				}
			}
			
			completed = true;
			notifyAll();
		}
		public synchronized void waitForFinish() throws InterruptedException {
			while(!completed)
				wait();
		}
	}
	
	public  void rearrangeCells() {
		for(int i=2;i<12;i++) 
			shift(i);
	}
	public  void refillTable() {
		for(int i=2;i<12;i++)
			for(int j=2;j<12;j++)
				if(cells[i][j].getType() == 0) 
					cells[i][j] = new Cell(i,j,new Random().nextInt(6)+1);
	}
	public  boolean clearTable() {
		boolean ret = false;
		int ted =0;
		for(int x1=2;x1<12;x1++) {
			for(int y1=2;y1<12;y1++) {
				if(cells[x1][y1].getType() != -1) {
					int a = 0,b=0,c=0,d=0;boolean his = false;
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
						ted+=a+b;
					}
					while(cells[x1][y1].getType() == cells[x1][y1-c].getType())
						c++;
					while(cells[x1][y1].getType() == cells[x1][y1+d].getType())
						d++;
					c--;d--;
					if(c+d>=2) {
						ret = true;
						his = true;
						for(int i=(-1)*c;i<=d;i++) {
							if(i!=0)
								cells[x1][y1+i].setType(-1);
						}
						ted+=c+d;
					}
					if(his) {
							cells[x1][y1].setType(-1);
							ted++;
					}
				}
			}
		}
		if(ret)
			rearrangeCells();
		if(ted!=0)
			points += (ted-1)*10;
		//frame.score.setText(points+"");
		return ret;
	}
}
