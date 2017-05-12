package GUI;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import Encryption.Encryption;

public class PasswordPane extends JPanel {
	
	SpringLayout layout;
	int passwordLength;
	
	public PasswordPane(String generatedPassword,MenuGUI f){
		super();
		this.passwordLength = passwordLength;
		layout = (SpringLayout) f.mainPane.getLayout();
		setLayout(layout);
		
		setBackground(Color.DARK_GRAY);
		
		JTextField psswd = new JTextField(generatedPassword);
		psswd.setBackground(Color.white);
		psswd.setForeground(Color.BLACK);
		psswd.setEditable(false);
		this.add(psswd);
		
		JButton addToClipboard = new JButton ("Ajouter au presse papier");
		addToClipboard.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(psswd.getText()), null);
			}
			
		});
		this.add(addToClipboard);
		
		JButton ok = new JButton ("Ok");
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				f.showMenuPane();
			}
			
		});
		this.add(ok);
		
		
		layout.putConstraint(SpringLayout.NORTH, psswd, 10, SpringLayout.NORTH, f.mainPane);
		layout.putConstraint(SpringLayout.SOUTH, psswd, 50, SpringLayout.SOUTH, f.mainPane);
		layout.putConstraint(SpringLayout.WEST, psswd, 10, SpringLayout.WEST, f.mainPane);
		layout.putConstraint(SpringLayout.EAST, psswd, -10, SpringLayout.EAST, f.mainPane);
		layout.putConstraint(SpringLayout.SOUTH, psswd, 20, SpringLayout.NORTH, psswd);
		
		layout.putConstraint(SpringLayout.SOUTH, addToClipboard, -10, SpringLayout.SOUTH, f.mainPane);
		layout.putConstraint(SpringLayout.EAST, addToClipboard, -20, SpringLayout.HORIZONTAL_CENTER, f.mainPane);
		
		layout.putConstraint(SpringLayout.SOUTH, ok, -10, SpringLayout.SOUTH, f.mainPane);
		layout.putConstraint(SpringLayout.WEST, ok, 20, SpringLayout.HORIZONTAL_CENTER, f.mainPane);

	}
	
	


}
