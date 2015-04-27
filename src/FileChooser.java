import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class FileChooser extends JFrame implements ActionListener {
	
	private JPanel contentPane;
	private JButton openButton;
	private JTextArea log;
    private JFileChooser fc;

	public FileChooser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setCurrentDirectory(new File("./songs/"));
        
        openButton = new JButton("Open");
        openButton.addActionListener(this);
        
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(FileChooser.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                log.append("Opening: " + file.getName() + "." + "\n");
                Main.pauseSong();
                Main.playSong(file.getAbsolutePath());
            } else {
                log.append("Open command cancelled by user." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
	}
}


