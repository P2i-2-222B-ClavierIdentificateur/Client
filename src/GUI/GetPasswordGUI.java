package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import Analyse.GaussTest;
import Analyse.KeyStrokeSet;
import Exception.BadLoginException;
import GUIElements.CancelButton;
import KeystrokeMeasuring.KeyStroke;
import KeystrokeMeasuring.TimingManager;
import Main.Account;
import Main.Main;
import Main.PasswordGetter;
import Main.PasswordTry;
import Warnings.SimpleWarning;

@SuppressWarnings("serial")
public class GetPasswordGUI extends JPanel{
	
	private JLabel domainLabel;
	private JLabel idLabel;
	private JLabel psswdLabel;
	
	private JTextField domainField;
	private JTextField idField;
	private JPasswordField psswdField;
	
	private JButton getPsswd;
	private JButton cancel;
	
	TimingManager timingManager;

	private MenuGUI f;
	
	public GetPasswordGUI(JPanel menuPane, final MenuGUI f){
		this.f=f;
		SpringLayout layout = f.getLayout();
		setLayout(layout);
		
		setBackground(Color.DARK_GRAY);
		
		cancel = new CancelButton(menuPane,this);
		this.add(cancel);
		
		domainLabel = new JLabel ("Domaine : ");
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
		timingManager = new TimingManager (psswdField);
		psswdField.addKeyListener(timingManager);
		
		System.out.println(Thread.currentThread());
		
		psswdField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE || arg0.getKeyCode() == KeyEvent.VK_DELETE){
					psswdField.setText("");
				
				}else if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					tryConnection();
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
		

		getPsswd.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				tryConnection()	;			
			}
			
			
		});
		this.add(getPsswd);
		
		layout.putConstraint(SpringLayout.NORTH, domainLabel, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, domainLabel, 10, SpringLayout.WEST, this);

		layout.putConstraint(SpringLayout.NORTH,idLabel , 10, SpringLayout.SOUTH, domainLabel);
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

		//setVisible(false);
	}
	
	private void tryConnection(){
		Main.sessionManager.getCurrentSession().reshceduleEnd();

		ArrayList<KeyStroke> ks = new ArrayList<KeyStroke>(timingManager.getKeyStrokes());
		LinkedList<KeyStroke> ksl = new LinkedList<KeyStroke>(ks);
		System.out.println("ksl :" + ksl.size());
		String login = idField.getText();
		if(login.endsWith(" ")){
			int i = login.length()-1;
			do{
				i--;
			}
			while(login.charAt(i)==' ');
			login = login.substring(0, i+1);
		} 
		String domain = domainField.getText();
		if(domain.endsWith(" ")){
			int i = domain.length()-1;
			do{
				i--;
			}
			while(domain.charAt(i)==' ');
			domain = domain.substring(0, i+1);
		} 
		if(login.length()>2 && domain.length()>2){
			Account account = new Account(login,domain,psswdField.getPassword());
			Main.sessionManager.getCurrentSession().setAccount(new Account(idField.getText(),domainField.getText(),psswdField.getPassword()));
			Main.sessionManager.getCurrentSession().addPasswordTry(new PasswordTry(timingManager.getKeyStrokes()));
			System.out.println("PasswordTry ajouté");
			//timingManager.getStrokes().clear();
			//timingManager.getKeyStrokes().clear();
			
	
			
			int i = Main.sessionManager.getCurrentSession().getPasswordTries().size()-1;
			try {
				//if(DistanceTest.test(new KeyStrokeSet(ksl), account)){
				if(GaussTest.test(new KeyStrokeSet(ksl),account)){
				//if(CosineTest.test(new KeyStrokeSet(ksl), account)){
					Main.sessionManager.getCurrentSession().getPasswordTries().get(i).setSuccess(true);
					f.showPasswordPane(PasswordGetter.getPassword(account));
					Main.sessionManager.endCurrentSession();

				}else{
					new SimpleWarning("Maniere d'ecrire non reconnue");
					Main.sessionManager.getCurrentSession().getPasswordTries().get(i).setSuccess(false);

				}
			} catch (BadLoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			new SimpleWarning("L'un des champs est trop court");
		}
	}
	
	public JTextField getDomainField() {
		return domainField;
	}

	public void setDomainField(JTextField domainField) {
		this.domainField = domainField;
}

}