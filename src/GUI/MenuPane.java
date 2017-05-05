package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class MenuPane extends JPanel{
	
	JButton create;
	JButton request;

	public MenuPane(final MenuGUI f){
		setBackground(Color.DARK_GRAY);
		SpringLayout layout = f.layout;
		setLayout(layout);
		create = new JButton ("Create a new account");
		request = new JButton ("get an existing password");
		
		this.add(create);
		this.add(request);
		
		create.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
				f.createAccountPane.setVisible(true);
			}
			
		});
		
		request.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
				f.getPsswdPane.setVisible(true);
			}
			
		});
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, create, -getWidth()/4, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, create, 0, SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.WEST, create, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, create, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, create, -10, SpringLayout.HORIZONTAL_CENTER, this);


		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, request, getWidth()/4, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, request, 0, SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, request, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, request, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, request, 10, SpringLayout.HORIZONTAL_CENTER, this);

		
		
	}
	
}
