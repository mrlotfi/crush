package q2;
import javax.swing.*;
import java.awt.*;
public class GameFrame extends JFrame {
	private GamePanel panel;
	String name;
	JLabel score;
	JLabel nameL;
	public GameFrame(String name) {
		super();
		getContentPane().setLayout(null);
		setSize(1000,700);
		setBounds(0,0,1000,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(name);
		nameL = new JLabel();
		nameL.setBounds(600,30,200,200);
		nameL.setFont(nameL.getFont().deriveFont(30.0f));
		getContentPane().add(nameL);
		nameL.setText(name);
		score = new JLabel();
		score.setBounds(600, 30, 200, 200);
		score.setSize(400, 400);
		score.setFont(score.getFont().deriveFont(30.0f));
		score.setText("You scored:\n");
		getContentPane().add(score);
		Engine eng = new Engine(this);
		panel = new GamePanel(eng);
		getContentPane().add(panel);
		this.name = name;
	}
	public GamePanel getPanel() {
		return panel;
	}
}
