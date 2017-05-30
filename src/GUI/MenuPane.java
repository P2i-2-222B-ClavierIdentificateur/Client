package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class MenuPane extends JPanel {

	JButton create;
	JButton request;
	JButton deleteAccount;

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

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, deleteAccount, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, deleteAccount, (f.getHeight() / 4),
				SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, deleteAccount, -f.getWidth() / 4, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, deleteAccount, -10, SpringLayout.SOUTH, this);

	}

}