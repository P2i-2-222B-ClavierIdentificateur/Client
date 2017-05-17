package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class FirstPanel extends JPanel{
	
	private final MenuGUI frame;
	private JButton connection;
	private JButton createAccount;
	
	public FirstPanel (final MenuGUI frame){
		this.frame = frame;
		setBackground(Color.DARK_GRAY);
		SpringLayout layout = frame.layout;
		setLayout(layout);
		
		createAccount = new JButton ();
		createAccount.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.showCreateAccountSystem();
			}
		});
		
		this.add(createAccount);
		
		connection = new JButton ();
		connection.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		
		this.add(connection);
		
		layout.putConstraint(SpringLayout.NORTH, createAccount, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, createAccount, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, createAccount, 10, SpringLayout.SOUTH, this);
		
		layout.putConstraint(SpringLayout.NORTH, connection, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, connection, 10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, connection, 10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, connection, 10, SpringLayout.EAST, createAccount);
		
		
		setVisible(true);
	}
	
	/*public void createAcc (){
		this.setVisible(false);
		frame.createAccountSystem.setVisible(true);
	}*/
	

}
