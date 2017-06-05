package GUI;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.Color;
import javax.swing.SwingConstants;

import GUIElements.CancelButton;
import Main.PasswordGetter;
import TOTP.CodeTester;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;

public class TOTPCodePane extends JPanel{
	
	String key;
	private JTextField textField;
	GetPasswordGUI gpg;
	MenuGUI f;

	public TOTPCodePane(String key,GetPasswordGUI gpg,MenuGUI f){
		this.gpg = gpg;
		this.f=f;
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		this.key=key;
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					tryCode();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, textField, 20, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, textField, -166, SpringLayout.EAST, this);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		add(textField);
		textField.setColumns(10);
		
		JButton btnOk = new JButton("Ok");
		springLayout.putConstraint(SpringLayout.NORTH, btnOk, 88, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnOk, 81, SpringLayout.WEST, this);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tryCode();

			}
		});
		add(btnOk);
		
		CancelButton cancel =new CancelButton(gpg,this);
		springLayout.putConstraint(SpringLayout.NORTH, cancel, 0, SpringLayout.NORTH, btnOk);
		springLayout.putConstraint(SpringLayout.EAST, cancel, -80, SpringLayout.EAST, this);
		add(cancel);
		
		
		
		
	
	}
	
	private void tryCode(){
		int code = Integer.parseInt(textField.getText());
		if(CodeTester.testCode(code, key)){
			System.out.println("success");
			f.showPasswordPane(PasswordGetter.getPassword(gpg.getAccount()));
			Main.Main.sessionManager.newSession(null);
			setVisible(false);
		}else{
			textField.setText("");
		}
	}
}