package q2;
import javax.swing.*;
import java.awt.*;
public class GameFrame extends JFrame {
	GamePanel panel;
	public GameFrame() {
		super();
		getContentPane().setLayout(null);
		setSize(1000,700);
		setBounds(0,0,1000,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel = new GamePanel();
		getContentPane().add(panel);
		
	}
}
