package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import Database.Insert;
import Database.Request;
import TOTP.KeyGetter;

@SuppressWarnings("serial")
public class MenuPane extends JPanel {

	JButton create;
	JButton request;
	JButton deleteAccount;
	JButton addTOTP;

	public MenuPane(final MenuGUI f) {
		setBackground(Color.DARK_GRAY);
		SpringLayout layout = f.layout;
		setLayout(layout);
		create = new JButton("Ajouter un compte");
		request = new JButton("Récupérer un mot de passe");
		setVisible(false);

		this.add(create);
		this.add(request);

		create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
				f.createAccountPane.setVisible(true);
				f.createAccountPane.getTxt1().grabFocus();
			}

		});

		request.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				f.initGetPasswordPane();
				f.getMenuPane().setVisible(false);

			}

		});

		deleteAccount = new JButton("Supprimer un compte");

		this.add(deleteAccount);
		deleteAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.initDeleteAccountPane();
				setVisible(false);
			}

		});
		
		addTOTP = new JButton("Ajouter une clé TOTP");
		this.add(addTOTP);
		addTOTP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String existingKey = Request.getTOTPKey(Main.Main.currentSystemAccount.getLogin());
				if (existingKey != null ) {
					JOptionPane option = new JOptionPane();
					if (option.showConfirmDialog(null, "Une clé existe déja, voulez-vous la remplacer ?", "Attention",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						KeyGetter kg = new KeyGetter();
						kg.start();
						try {
							kg.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String newKey = kg.getKey();
						Insert.addTOTPKey(newKey, Main.Main.currentSystemAccount.getLogin());
						f.initTOTPKeyPane(newKey);
						setVisible(false);
					}
				} else {
					JOptionPane option = new JOptionPane();
					if (option.showConfirmDialog(null, "Etes-vous certain de vouloir ajour une clé TOTP ?", "Attention",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						KeyGetter kg = new KeyGetter();
						kg.start();
						try {
							kg.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String newKey = kg.getKey();
						Insert.addTOTPKey(newKey, Main.Main.currentSystemAccount.getLogin());
						f.initTOTPKeyPane(newKey);
						setVisible(false);
					}
				}
			}

		});


		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, create, -f.getWidth() / 4, SpringLayout.HORIZONTAL_CENTER,
				this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, create, -f.getHeight() / 4, SpringLayout.VERTICAL_CENTER,
				this);
		layout.putConstraint(SpringLayout.WEST, create, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, create, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, create, -10, SpringLayout.HORIZONTAL_CENTER, this);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, request, f.getWidth() / 4, SpringLayout.HORIZONTAL_CENTER,
				this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, request, -f.getHeight() / 4, SpringLayout.VERTICAL_CENTER,
				this);
		layout.putConstraint(SpringLayout.EAST, request, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, request, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, request, 10, SpringLayout.HORIZONTAL_CENTER, this);

		layout.putConstraint(SpringLayout.VERTICAL_CENTER, deleteAccount, (f.getHeight() / 4),
				SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, deleteAccount,- 20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, deleteAccount, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, deleteAccount, 20, SpringLayout.HORIZONTAL_CENTER, this);

		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addTOTP, -getWidth() / 4,SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, addTOTP, (f.getHeight() / 4),SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.WEST, addTOTP, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, addTOTP, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, addTOTP,- 20, SpringLayout.HORIZONTAL_CENTER, this);
	}

}