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
	private final JButton btnNewButton_5 = new JButton("Pick song");
	private JProgressBar progressBar = new JProgressBar();
	private static JTextPane textPane = new JTextPane();
	
	private static boolean playing = false;
	private boolean shuffle = false;
	
	private static String songTitle;
	private static String songArtist;
	private static long songLength;

	private static FileInputStream FIS;
	private static BufferedInputStream BIS;
	
	public static Player player;
	
	public static long pauseLocation;
	

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
					playSong("./songs/"+getRandomSong());
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
		
		
		textPane.setEditable(false);
		textPane.setBounds(12, 96, 408, 144);
		contentPane.add(textPane);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							FileChooser frame = new FileChooser();
							frame.setVisible(true);
							frame.setResizable(false);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		btnNewButton_5.setBounds(267, 51, 97, 25);
		contentPane.add(btnNewButton_5);


	}
	
	public String getRandomSong() {
		File songs = new File("./songs/");
		String[] songsList = songs.list();
		return songsList[random(17)];
		//return "";
	}
	
	public static void playSong(String path) {
		try {
			FIS = new FileInputStream(path);
			BIS = new BufferedInputStream(FIS);
			songLength = FIS.available();
			songTitle = path;
			player = new Player(BIS);
			textPane.setText(songTitle.replace("./songs/", "").replace(".mp3", ""));
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
	
	public static void pauseSong() {
		if (player != null) {
			try {
				pauseLocation = FIS.available();
				BIS.close();
				FIS.close();
				player.close();
				playing = false;
				//System.out.println("paused at: "+pauseLocation);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int random(int range) {
		return (int)(java.lang.Math.random() * (range+1));
	}
}
