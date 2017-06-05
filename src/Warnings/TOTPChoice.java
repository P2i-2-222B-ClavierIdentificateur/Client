package Warnings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import Database.Insert;
import Database.Request;
import Exception.AccountAlreadyExistsException;
import GUI.CreateAccountSystem;
import TOTP.KeyGetter;

public class TOTPChoice extends JFrame {
	private SpringLayout layout;
	private String key;
	String account;

	public TOTPChoice(CreateAccountSystem p) {
		setSize(300, 200);
		setLocation(300, 300);
		setTitle("Error");

		account = p.getIdField().getText();

		layout = new SpringLayout();

		JPanel mainPane = new JPanel();
		mainPane.setLayout(layout);

		JPanel first = new JPanel();
		mainPane.add(first);

		JLabel txt = new JLabel("Souhaitez vous associer ce compte a une cle TOTP ?");
		first.add(txt);

		JPanel keyPane = new JPanel();

		mainPane.add(keyPane);

		keyPane.setVisible(false);

		JLabel keyLabel = new JLabel("");

		JButton done = new JButton("ok");
		keyPane.add(done);
		done.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (key != null) {
					try {
						p.tryCreateAccount();
					} catch (AccountAlreadyExistsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Insert.addTOTPKey(key, account);
					setVisible(false);

				} else {
					System.err.println("key null");
				}
			}

		});

		keyPane.add(keyLabel);

		layout.putConstraint(SpringLayout.NORTH, keyPane, 0, SpringLayout.NORTH, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, keyPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.WEST, keyPane, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, keyPane, 0, SpringLayout.EAST, mainPane);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, keyLabel, 0, SpringLayout.HORIZONTAL_CENTER, mainPane);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, keyLabel, -30, SpringLayout.VERTICAL_CENTER, mainPane);

		layout.putConstraint(SpringLayout.NORTH, first, 0, SpringLayout.NORTH, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, first, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.WEST, first, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, first, 0, SpringLayout.EAST, mainPane);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, txt, 0, SpringLayout.HORIZONTAL_CENTER, mainPane);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, txt, -30, SpringLayout.VERTICAL_CENTER, mainPane);

		JButton okButton = new JButton("Oui");
		first.add(okButton);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, okButton, -100, SpringLayout.HORIZONTAL_CENTER, mainPane);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, okButton, 30, SpringLayout.VERTICAL_CENTER, mainPane);

		JButton noButton = new JButton("Non");
		first.add(noButton);

		noButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					p.tryCreateAccount();
				} catch (AccountAlreadyExistsException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);

			}

		});

		okButton.requestFocus();
		okButton.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					KeyGetter kg = new KeyGetter();
					try {
						kg.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					key = new String(kg.getKey());
					System.out.println(key);
					keyLabel.setText(kg.getKey());
					keyPane.setVisible(true);
					first.setVisible(false);

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

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				KeyGetter kg = new KeyGetter();
				kg.start();
				try {
					kg.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				key = new String(kg.getKey());
				System.out.println(key);
				keyLabel.setText(kg.getKey());
				keyPane.setVisible(true);
				first.setVisible(false);

			}

		});
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, noButton, 100, SpringLayout.HORIZONTAL_CENTER, mainPane);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, noButton, 30, SpringLayout.VERTICAL_CENTER, mainPane);
		noButton.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					setVisible(false);
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

		setContentPane(mainPane);
		setVisible(true);
	}
}