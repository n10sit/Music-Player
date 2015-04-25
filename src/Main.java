import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import java.io.*;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;


@SuppressWarnings({ "serial", "unused" })
public class Main extends JFrame {

	private JPanel contentPane;
	private final JButton btnNewButton_1 = new JButton("|<<");
	private final JButton btnNewButton_2 = new JButton(">>|");
	private final JButton btnNewButton_3 = new JButton("||");
	private final JButton btnNewButton_4 = new JButton("Shuffle");
	
	private boolean playing = false;
	private boolean shuffle = false;
	
	private String songTitle;
	private String songArtist;
	private long songLength;

	private FileInputStream FIS;
	private BufferedInputStream BIS;
	
	public Player player;
	
	public long pauseLocation;

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
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (pauseLocation <= 0)
					playSong("./songs/01. Legend.mp3");
				else
					resumeSong();
			}
		});
		btnNewButton.setBounds(180, 13, 75, 25);
		contentPane.add(btnNewButton);
		btnNewButton_1.setBounds(93, 13, 75, 25);
		contentPane.add(btnNewButton_1);
		btnNewButton_2.setBounds(267, 13, 75, 25);
		contentPane.add(btnNewButton_2);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseSong();
			}
		});
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
	
	public void playSong(String path) {
		try {
			FIS = new FileInputStream(path);
			BIS = new BufferedInputStream(FIS);
			songLength = FIS.available();
			songTitle = path;
			player = new Player(BIS);
			//System.out.println("song length: "+songLength);
		} catch (JavaLayerException | IOException e) {
			e.printStackTrace();
		}
		new Thread() {
			@Override
			public void run() {
				try {
					player.play();
					playing = true;
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void resumeSong() {
		try {
			FIS = new FileInputStream(songTitle);
			BIS = new BufferedInputStream(FIS);
			player = new Player(BIS);
			long lengthToSkip = songLength - pauseLocation;
			FIS.skip(lengthToSkip);
			//System.out.println("skipped:"+lengthToSkip);
		} catch (JavaLayerException | IOException e) {
			e.printStackTrace();
		}
		new Thread() {
			@Override
			public void run() {
				try {
					player.play();
					playing = true;
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void pauseSong() {
		if (player != null) {
			try {
				pauseLocation = FIS.available();
				player.close();
				playing = false;
				//System.out.println("paused at: "+pauseLocation);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
