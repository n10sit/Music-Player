import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class Main extends JFrame {

	private JPanel contentPane;
	private final JButton btnNewButton_1 = new JButton("|<<");
	private final JButton btnNewButton_2 = new JButton(">>|");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Play");
		btnNewButton.setBounds(180, 13, 75, 25);
		contentPane.add(btnNewButton);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_1.setBounds(93, 13, 75, 25);
		contentPane.add(btnNewButton_1);
		btnNewButton_2.setBounds(267, 13, 75, 25);
		contentPane.add(btnNewButton_2);

	}
}
