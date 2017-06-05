package GUI;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import Database.Request;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UseTOTPPane extends JPanel{
	public UseTOTPPane(MenuGUI f, GetPasswordGUI passwordPane,String key) {
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel lblNewLabel = new JLabel("Voulez-vous utilisez un code TOTP ?");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 105, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, -80, SpringLayout.EAST, this);
		lblNewLabel.setForeground(Color.WHITE);
		add(lblNewLabel);
		
		JButton btnOui = new JButton("Oui");
		springLayout.putConstraint(SpringLayout.NORTH, btnOui, 61, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, btnOui, 55, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, btnOui, 164, SpringLayout.WEST, this);
		btnOui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				f.initTOTPPane(key,passwordPane);
				setVisible(false);
			}
		});
		add(btnOui);
		
		JButton btnNon = new JButton("Non");
		springLayout.putConstraint(SpringLayout.NORTH, btnNon, 0, SpringLayout.NORTH, btnOui);
		springLayout.putConstraint(SpringLayout.WEST, btnNon, -158, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.EAST, btnNon, -55, SpringLayout.EAST, this);
		btnNon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordPane.setVisible(true);
				setVisible(false);
			}
		});
		add(btnNon);
	}
}