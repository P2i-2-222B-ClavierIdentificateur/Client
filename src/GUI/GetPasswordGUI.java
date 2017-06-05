package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import Analyse.DistanceTest;
import Analyse.KeyStrokeSet;
import Database.Request;
import Exception.BadLoginException;
import GUIElements.CancelButton;
import KeystrokeMeasuring.KeyStroke;
import KeystrokeMeasuring.TimingManager;
import Main.Account;
import Main.Main;
import Main.PasswordGetter;
import Warnings.SimpleWarning;

@SuppressWarnings("serial")
public class GetPasswordGUI extends JPanel  {

	private JLabel domainLabel;
	private JLabel idLabel;
	private JLabel psswdLabel;

	private JTextField domainField;
	private JTextField idField;
	private JPasswordField psswdField;

	private JButton getPsswd;
	private JButton cancel;

	private final TimingManager timingManager;

	private MenuGUI f;

	private String password;

	private boolean premiereEntree = true;
	
	DatabaseWorkFrame dbPane;
	
	private Account account;


	public GetPasswordGUI(JPanel menuPane, final MenuGUI f) {
		password = "";
		this.f = f;
		SpringLayout layout = f.getLayout();
		setLayout(layout);

		setBackground(Color.DARK_GRAY);

		cancel = new CancelButton(menuPane, this);
		this.add(cancel);

		domainLabel = new JLabel("Domaine : ");
		domainLabel.setForeground(Color.white);
		this.add(domainLabel);

		idLabel = new JLabel("Identifiant : ");
		idLabel.setForeground(Color.white);
		this.add(idLabel);

		psswdLabel = new JLabel("Mot de passe : ");
		psswdLabel.setForeground(Color.white);
		this.add(psswdLabel);

		domainField = new JTextField();
		this.add(domainField);

		idField = new JTextField();
		this.add(idField);

		getPsswd = new JButton("Récupérer le mot de passe");

		psswdField = new JPasswordField();
		this.add(psswdField);
		timingManager = new TimingManager(psswdField);
		psswdField.addKeyListener(timingManager);

		psswdField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {

				if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE || arg0.getKeyCode() == KeyEvent.VK_DELETE) {
					psswdField.setText("");
					timingManager.getAccount().setPassword(new String());

				} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						tryConnection();
					} catch (BadLoginException e) {
						// TODO Auto-generated catch block
					}
					// timingManager.getAccount().setPassword(new String());
					// TODO fixer le nettoyage du password
				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				if ((int) arg0.getKeyChar() != 10) {
					String entree = new String();
					if (premiereEntree) {
						entree = String.valueOf(arg0.getKeyChar());
					} else {
						entree = timingManager.getAccount().getPasswordAsString();
						entree += arg0.getKeyChar();

					}
					timingManager.getAccount().setPassword(entree);
					premiereEntree = false;
				}
			}

		});

		psswdField.addFocusListener((new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				timingManager.setAccount(new Account(idField.getText(), domainField.getText(), new String()));

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

		}));

		getPsswd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					tryConnection();
				} catch (BadLoginException e) {
					// TODO Auto-generated catch block
				}
			}

		});
		this.add(getPsswd);

		layout.putConstraint(SpringLayout.NORTH, domainLabel, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, domainLabel, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH, idLabel, 10, SpringLayout.SOUTH, domainLabel);
		layout.putConstraint(SpringLayout.WEST, idLabel, 0, SpringLayout.WEST, domainLabel);

		layout.putConstraint(SpringLayout.NORTH, psswdLabel, 10, SpringLayout.SOUTH, idLabel);
		layout.putConstraint(SpringLayout.WEST, psswdLabel, 0, SpringLayout.WEST, domainLabel);

		layout.putConstraint(SpringLayout.SOUTH, domainField, 0, SpringLayout.SOUTH, domainLabel);
		layout.putConstraint(SpringLayout.EAST, domainField, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, domainField, 10, SpringLayout.EAST, psswdLabel);

		layout.putConstraint(SpringLayout.SOUTH, idField, 0, SpringLayout.SOUTH, idLabel);
		layout.putConstraint(SpringLayout.EAST, idField, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, idField, 10, SpringLayout.EAST, psswdLabel);

		layout.putConstraint(SpringLayout.SOUTH, psswdField, 0, SpringLayout.SOUTH, psswdLabel);
		layout.putConstraint(SpringLayout.EAST, psswdField, -10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, psswdField, 10, SpringLayout.EAST, psswdLabel);

		layout.putConstraint(SpringLayout.SOUTH, getPsswd, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, getPsswd, 10, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, getPsswd, -10, SpringLayout.HORIZONTAL_CENTER, this);

		layout.putConstraint(SpringLayout.SOUTH, cancel, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, cancel, 10, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, cancel, -10, SpringLayout.EAST, this);

	}

	private void tryConnection() throws BadLoginException {

		Main.sessionManager.getCurrentSession().reshceduleEnd();
		timingManager.build();

		LinkedList<KeyStroke> ksl = new LinkedList<KeyStroke>(timingManager.getKeyStrokes());
		String login = idField.getText();
		if (login.endsWith(" ")) {
			int i = login.length() - 1;
			do {
				i--;
			} while (login.charAt(i) == ' ');
			login = login.substring(0, i + 1);
		}
		String domain = domainField.getText();
		if (domain.endsWith(" ")) {
			int i = domain.length() - 1;
			do {
				i--;
			} while (domain.charAt(i) == ' ');
			domain = domain.substring(0, i + 1);
		}
		if (login.length() > 2 && domain.length() > 2) {
			password = new String(psswdField.getPassword());
			Account account = new Account(login, domain, password);
			this.setAccount(account);
			Main.sessionManager.getCurrentSession().setAccount(account);
			// timingManager.getStrokes().clear();
			// timingManager.getKeyStrokes().clear();
			if (Request.checkIfAccountExists(account, Main.conn)) {

				int i = Main.sessionManager.getCurrentSession().getPasswordTries().size() - 1;
				if (i >= 0) {
					try {
						if (ksl.size() > 0) {
							if (DistanceTest.test(new KeyStrokeSet(ksl), account)) {
								// if (NormalizedGaussTest.test(new
								// KeyStrokeSet(ksl), account)) {
								// if(CosineTest.test(new KeyStrokeSet(ksl),
								// account)){
								Main.sessionManager.getCurrentSession().getPasswordTries().get(i).setSuccess(true);
								initBdPane(Main.sessionManager.getCurrentSession().getPasswordTries().size());
								Main.sessionManager.newSession(dbPane);
								f.showPasswordPane(PasswordGetter.getPassword(account));
								setVisible(false);
								close();

							} else {
								String key;
								if((key = Request.getTOTPKey(Main.currentSystemAccount.getLogin()))!=null){
									f.initUseTOTP(this, key);
									setVisible(false);
								}
								System.out.println(key);

								new SimpleWarning("Maniere d'ecrire non reconnue");
								
								Main.sessionManager.getCurrentSession().getPasswordTries().get(i).setSuccess(false);

							}
						}
					} catch (BadLoginException e) {
						// TODO Auto-generated catch block
						System.out.println(
								account.getLogin() + "|" + account.getDomain() + "|" + account.getPasswordAsString());
					}
				} else
					System.err.println("PasswordTries null");
			} else {
				psswdField.setText(new String());
				timingManager.getKeyStrokes().clear();
				timingManager.getStrokes().clear();
				throw new BadLoginException();
			}
		} else {
			new SimpleWarning("L'un des champs est trop court");
		}

		psswdField.setText(new String());
		timingManager.getKeyStrokes().clear();
		timingManager.getStrokes().clear();
		timingManager.getAccount().setPassword(new String());
		premiereEntree = true;
	}
	
	public void initBdPane(int maxValue){
		dbPane = new DatabaseWorkFrame(maxValue);		

	}

	public JTextField getDomainField() {
		return domainField;
	}

	public void setDomainField(JTextField domainField) {
		this.domainField = domainField;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void close() {
		timingManager.close();
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}