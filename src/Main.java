import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.sound.*;


@SuppressWarnings({ "serial", "unused" })
public class Main extends JFrame {

	private JPanel contentPane;
	private final JButton btnNewButton_1 = new JButton("|<<");
	private final JButton btnNewButton_2 = new JButton(">>|");
	private final JButton btnNewButton_3 = new JButton("||");
	private final JButton btnNewButton_4 = new JButton("Shuffle");
	
	private boolean playing = false;
	private boolean shuffle = false;

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
		btnNewButton_1.setBounds(93, 13, 75, 25);
		contentPane.add(btnNewButton_1);
		btnNewButton_2.setBounds(267, 13, 75, 25);
		contentPane.add(btnNewButton_2);
		btnNewButton_3.setBounds(180, 51, 75, 25);
		contentPane.add(btnNewButton_3);
		btnNewButton_4.setBounds(56, 51, 112, 25);
		contentPane.add(btnNewButton_4);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spinner.setBounds(319, 51, 42, 22);
		contentPane.add(spinner);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(12, 96, 408, 90);
		contentPane.add(textPane);
		
		JLabel lblVolume = new JLabel("Volume:");
		lblVolume.setBounds(267, 51, 75, 25);
		contentPane.add(lblVolume);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(12, 199, 408, 25);
		contentPane.add(progressBar);

	}
}
