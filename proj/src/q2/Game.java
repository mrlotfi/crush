package q2;

import javax.swing.JOptionPane;



public class Game {
	int k;
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		GameFrame frame = new GameFrame(JOptionPane.showInputDialog(null,"Please enter your name:"));
		frame.setVisible(true);
		System.out.println(Thread.currentThread());
		}

}
