package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import Database.Request;
import Encryption.Encryption;
import GUIElements.CancelButton;
import Main.Main;
import Main.SystemAccount;
import Warnings.SimpleWarning;

@SuppressWarnings("serial")
public class SystemConnectionPane extends JPanel{
	
	MenuGUI frame;
	
	public SystemConnectionPane(MenuGUI frame){
		this.frame=frame;
		setBackground(Color.DARK_GRAY);
		
		SpringLayout layout = frame.layout;
		setLayout(layout);
		
		JLabel loginLabel = new JLabel("Identifiant :");
		loginLabel.setForeground(Color.white);
		this.add(loginLabel);
		
		JLabel passwordLabel = new JLabel ("Mot de passe :");
		passwordLabel.setForeground(Color.white);
		this.add(passwordLabel);
		
		JTextField loginField = new JTextField();
		this.add(loginField);
		
		JPasswordField passwordField = new JPasswordField();
		this.add( passwordField);
		passwordField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					tryConnection(loginField,passwordField);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		JButton connect = new JButton("Connection");
		this.add(connect);
		connect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tryConnection(loginField,passwordField);

			}
			
		});
		
		CancelButton cancel = new CancelButton(frame.getFirstPanel(),this);
		this.add(cancel);
		
		layout.putConstraint(SpringLayout.WEST, loginLabel, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, loginLabel, 20, SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, passwordLabel, 0, SpringLayout.WEST, loginLabel);
		layout.putConstraint(SpringLayout.NORTH, passwordLabel, 20, SpringLayout.SOUTH, loginLabel);
		
		layout.putConstraint(SpringLayout.WEST, passwordField, 20, SpringLayout.EAST, passwordLabel);
		layout.putConstraint(SpringLayout.SOUTH, passwordField, 0, SpringLayout.SOUTH, passwordLabel);
		layout.putConstraint(SpringLayout.EAST, passwordField, -20, SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.WEST, loginField, 0, SpringLayout.WEST, passwordField);
		layout.putConstraint(SpringLayout.SOUTH, loginField, 0, SpringLayout.SOUTH, loginLabel);
		layout.putConstraint(SpringLayout.EAST, loginField, -20, SpringLayout.EAST, this);
		
		
		layout.putConstraint(SpringLayout.WEST, connect, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, connect, -20, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, connect, frame.getWidth()/4, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, connect, -40, SpringLayout.SOUTH, connect);

		
		layout.putConstraint(SpringLayout.EAST, cancel, -20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, cancel, -20, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cancel, -frame.getWidth()/4, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, cancel, -40, SpringLayout.SOUTH, cancel);

		
		setVisible(false);

	}
	
	private void tryConnection(JTextField loginField, JPasswordField passwordField){
		String login = loginField.getText();
		if(login.endsWith(" ")){
			int i = login.length()-1;
			do{
				i--;
			}
			while(login.charAt(i)==' ' && i>0);
			login = login.substring(0, i+1);
		}
		if(login.length()>2){
			System.out.println("!"+login+"!");
			String dbPassword = Request.getPasswordForSystemAccount(login,Main.conn);
			if(Encryption.checkPassword(dbPassword,new String (passwordField.getPassword()))){
				Main.currentSystemAccount = new SystemAccount (login);
				System.out.println("Vous êtes connecté en tant que " + login);
				frame.showMenuPane();
				frame.hideSystemConnectionPane();
			}else{
				new SimpleWarning("Echec de la connection");
				passwordField.setText("");
			}
		}else{
			new SimpleWarning("L'identifiant trop court");
		}
	}

}