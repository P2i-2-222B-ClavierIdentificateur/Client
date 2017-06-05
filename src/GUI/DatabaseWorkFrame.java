package GUI;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.SpringLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class DatabaseWorkFrame extends JFrame{
	
	JLabel label;
	JProgressBar progressBar;
	
	public DatabaseWorkFrame(int maxValue) {
		setTitle("Pressureboard");

		setLocation(200,200);
		setSize(290,127);
		JPanel mainPane = new JPanel();
		mainPane.setBackground(Color.DARK_GRAY);
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		SpringLayout sl_mainPane = new SpringLayout();
		mainPane.setLayout(sl_mainPane);
		
		label = new JLabel("Travail sur la base de donnée en cours");
		sl_mainPane.putConstraint(SpringLayout.NORTH, label, 10, SpringLayout.NORTH, mainPane);
		springLayout.putConstraint(SpringLayout.NORTH, label, 27, SpringLayout.NORTH, mainPane);
		springLayout.putConstraint(SpringLayout.EAST, label, -92, SpringLayout.EAST, mainPane);
		label.setForeground(Color.WHITE);
		mainPane.add(label);
		
		progressBar = new JProgressBar(0,maxValue);
		sl_mainPane.putConstraint(SpringLayout.NORTH, progressBar, 21, SpringLayout.SOUTH, label);
		sl_mainPane.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, progressBar);
		sl_mainPane.putConstraint(SpringLayout.WEST, progressBar, -268, SpringLayout.EAST, mainPane);
		sl_mainPane.putConstraint(SpringLayout.EAST, progressBar, -23, SpringLayout.EAST, mainPane);
		progressBar.setStringPainted(true);
		springLayout.putConstraint(SpringLayout.NORTH, progressBar, 37, SpringLayout.SOUTH, label);
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 87, SpringLayout.WEST, mainPane);
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, 77, SpringLayout.SOUTH, label);
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -92, SpringLayout.EAST, mainPane);
		mainPane.add(progressBar);
		setContentPane(mainPane);
		setVisible(true);
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
}
