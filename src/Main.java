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
	
	private static File lastSong;
	

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
		/*test();
		while(player != null) {
			System.out.println("hey");
			if (player.isComplete()) {
				System.out.println("yo");
				playSong("./songs/"+getRandomSong());
			}
		}*/
	}
	
	public static void test() {
		System.out.println("hi");
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
				if (!playing) {
					String song = "./songs/"+getRandomSong();
					if (pauseLocation <= 0)
						playSong(song);
					else
						resumeSong();
					playing = true;
				} else {
					System.out.println("A song is already playing!");
				}
			}
		});
		btnNewButton.setBounds(180, 13, 75, 25);
		contentPane.add(btnNewButton);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (lastSong != null) {
					pauseSong();
					playSong(lastSong.getPath());
				}
			}
		});
		btnNewButton_1.setBounds(93, 13, 75, 25);
		contentPane.add(btnNewButton_1);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseSong();
				playSong("./songs/"+getRandomSong());
			}
		});
		btnNewButton_2.setBounds(267, 13, 75, 25);
		contentPane.add(btnNewButton_2);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseSong();
			}
		});
		btnNewButton_3.setBounds(180, 51, 75, 25);
		contentPane.add(btnNewButton_3);
		
		
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
	
	public static String getRandomSong() {
		File songs = new File("./songs/");
		String[] songsList = songs.list();
		int length = songsList.length;
		return songsList[random(length)];
		//return "";
	}
	
	public static void playSong(String path) {
		try {
			FIS = new FileInputStream(path);
			BIS = new BufferedInputStream(FIS);
			songLength = FIS.available();
			songTitle = path;
			lastSong = new File(path);
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
					//playing = true;
					if (player.isComplete()) {
						lastSong = new File(path);
						playSong("./songs/"+getRandomSong());
					}
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
					//playing = true;
					if (player.isComplete()) {
						//System.out.println("yo");
						playSong("./songs/"+getRandomSong());
					}
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
	
	public static int random(int range) {
		return (int)(java.lang.Math.random() * (range+1));
	}
}
