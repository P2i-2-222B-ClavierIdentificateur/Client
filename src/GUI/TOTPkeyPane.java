package GUI;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TOTPkeyPane extends JPanel {
	public TOTPkeyPane(MenuGUI f, String key) {
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel lblClTotp = new JLabel("Clé TOTP : " + key);
		springLayout.putConstraint(SpringLayout.NORTH, lblClTotp, 26, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, lblClTotp, -193, SpringLayout.EAST, this);
		lblClTotp.setForeground(Color.WHITE);
		add(lblClTotp);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.showMenuPane();
				setVisible(false);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnOk, 35, SpringLayout.SOUTH, lblClTotp);
		springLayout.putConstraint(SpringLayout.WEST, btnOk, 197, SpringLayout.WEST, this);
		add(btnOk);
	}
}