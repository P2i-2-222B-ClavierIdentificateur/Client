package GUI;

import GUIElements.CancelButton;
import Main.Main;
import Main.Password;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.FocusListener;

import Warnings.SimpleWarning;

@SuppressWarnings("serial")
public class AddAccountGUI extends JPanel {
	
	//les panels
	JPanel mainPanel;
	JPanel initPsswd;
	
	JButton cancel;
	
	BDGUI bdGUI;
	
	// variable verifiant la corrspondance des mots de passe
	boolean psswdMatch;
	//premier champ de mot de passe
	JPasswordField  txt1;
	//deuxieme champ de mot de passe
	JPasswordField txt2;
	
	JLabel userIdLabel; // label pour l'identifiant
	JTextField userIdField; // champ pour l'identifiant
	
	JLabel domainLabel;
	JTextField domainField;
	SpringLayout layout;
	
	JLabel passwordLengthLabel;
	JTextField passwordLengthField;
	JSlider passwordLengthSlider;
	MenuGUI f;
	
	public AddAccountGUI(final JPanel menuPane,final MenuGUI f){
		this.f=f;
		setBackground(Color.darkGray);
		layout = (SpringLayout) menuPane.getLayout();
	//	setResizable(false);
	//	setTitle("Test Projet P2I");
		
		cancel = new CancelButton(menuPane,this);
		this.add(cancel);
		//premier champ de mot de passe
		JLabel label1 =  new JLabel("Enter password: ");
		label1.setForeground(Color.white);
		txt1 = new JPasswordField("", 15);
		
		//deuxieme champ de mot de passe 
		JLabel label2 =  new JLabel("Confirm password: ");
		label2.setForeground(Color.white);
		txt2 = new JPasswordField("", 15);
		
		domainLabel = new JLabel("Domain :");
		domainLabel.setForeground(Color.white);
		this.add(domainLabel);
		
		domainField = new JTextField();
		this.add(domainField);
		
		//champ d'identifiant :
		userIdLabel = new JLabel ("Id : ");
		userIdLabel.setForeground(Color.white);
		userIdField = new JTextField("");
		//on ajoute un key listener pour gerer la validation avec la touche entree
		userIdField.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					tryCreateAccount();

				}
				
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
				
			}
		});
		txt1.addKeyListener(userIdField.getKeyListeners()[0]);
		txt2.addKeyListener(userIdField.getKeyListeners()[0]);
		domainField.addKeyListener(userIdField.getKeyListeners()[0]);
		// ici meme chose qu'avec la touche entre mais avec un bouton 
		final JButton button1 = new JButton("Create Account");
		button1.setSize(15,10);
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tryCreateAccount();
			}
		});
		
		passwordLengthLabel = new JLabel("Longueur :");
		passwordLengthLabel.setForeground(Color.white);
		
		passwordLengthField = new JTextField("20");
		passwordLengthField.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				passwordLengthSlider.setValue(Integer.parseInt(passwordLengthField.getText()));				
			}
			
		});
		
		passwordLengthField.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void focusLost(FocusEvent e) {
				passwordLengthSlider.setValue(Integer.parseInt(passwordLengthField.getText()));				

			}
			
		});
		
		passwordLengthSlider = new JSlider(8,50,20);
		passwordLengthSlider.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
				passwordLengthField.setText(String.valueOf(passwordLengthSlider.getValue()));
			}
			
		});
		
		
		SpringLayout layout = new SpringLayout(); // le layout utilise pour le fenetre
		
		// un panel pour contenir tout le reste
		mainPanel = new JPanel(); 
		mainPanel.setBackground(Color.DARK_GRAY);
		
		//le panel d'initialisation des mots de passe
		initPsswd = new JPanel();
		initPsswd.setBackground(Color.DARK_GRAY);
		
		// on ajoute tout aux bons endroits
		mainPanel.add(initPsswd);
		mainPanel.setLayout(layout);
		initPsswd.setLayout(layout);
		setLayout(layout);
		this.add(label1);
		this.add(txt1);
		this.add(label2);
		this.add(txt2);
		this.add(button1);
		this.add(userIdLabel);
		this.add(userIdField);
		this.add(passwordLengthLabel);
		this.add(passwordLengthField);
		this.add(passwordLengthSlider);
		

        
        
		layout.putConstraint(SpringLayout.WEST, initPsswd,0,SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, initPsswd,0,SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, initPsswd,0,SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, initPsswd,0,SpringLayout.SOUTH, this);
        
        //positionnement de label1
        layout.putConstraint(SpringLayout.WEST, label1,5,SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label1,5,SpringLayout.NORTH, this);
        
        // positionnement de txt1 
        layout.putConstraint(SpringLayout.EAST, txt1,-5,SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, txt1,5,SpringLayout.NORTH, this);
        
        // positionnement de label2
        layout.putConstraint(SpringLayout.NORTH, label2, 10, SpringLayout.SOUTH, label1);
        layout.putConstraint(SpringLayout.WEST, label2, 0, SpringLayout.WEST, label1);
        
        //positionnement de txt2
        layout.putConstraint(SpringLayout.NORTH, txt2, 5, SpringLayout.SOUTH, txt1);
        layout.putConstraint(SpringLayout.WEST, txt2, 0, SpringLayout.WEST, txt1);
        
        // Positionnement de userIdLabel
		layout.putConstraint(SpringLayout.WEST, userIdLabel, 0, SpringLayout.WEST, label1);
		layout.putConstraint(SpringLayout.NORTH, userIdLabel, 10, SpringLayout.SOUTH, label2);
		
		// Positionnement de userIdField
		layout.putConstraint(SpringLayout.WEST, userIdField, 0, SpringLayout.WEST, txt1);
		layout.putConstraint(SpringLayout.NORTH, userIdField, 5, SpringLayout.SOUTH, txt2);
        layout.putConstraint(SpringLayout.EAST, userIdField,-5,SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.WEST, domainLabel, 0, SpringLayout.WEST, userIdLabel);
		layout.putConstraint(SpringLayout.NORTH, domainLabel, 10, SpringLayout.SOUTH, userIdLabel);
		
		layout.putConstraint(SpringLayout.WEST, domainField, 0, SpringLayout.WEST, userIdField);
		layout.putConstraint(SpringLayout.NORTH, domainField, 5, SpringLayout.SOUTH, userIdField);
        layout.putConstraint(SpringLayout.EAST, domainField,-5,SpringLayout.EAST, this);
        
        //positionnemnt de button1
        layout.putConstraint(SpringLayout.SOUTH, button1, -10, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, button1, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, button1, -10, SpringLayout.HORIZONTAL_CENTER, this);

        layout.putConstraint(SpringLayout.SOUTH, cancel, -10, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, cancel, 10, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, cancel, -10, SpringLayout.EAST, this);

		layout.putConstraint(SpringLayout.WEST, passwordLengthLabel, 0, SpringLayout.WEST, domainLabel);
		layout.putConstraint(SpringLayout.NORTH, passwordLengthLabel, 10, SpringLayout.SOUTH, domainLabel);
		
		layout.putConstraint(SpringLayout.WEST, passwordLengthSlider, 30, SpringLayout.EAST, passwordLengthLabel);
		layout.putConstraint(SpringLayout.SOUTH, passwordLengthSlider, 0, SpringLayout.SOUTH, passwordLengthLabel);
		
		layout.putConstraint(SpringLayout.WEST, passwordLengthField, 30, SpringLayout.EAST, passwordLengthSlider);
		layout.putConstraint(SpringLayout.SOUTH, passwordLengthField, 0, SpringLayout.SOUTH, passwordLengthSlider);
		
		//setContentPane(mainPanel);
		setVisible(false);
	}
	
	private void tryCreateAccount(){
		// on verifie que les deux mots de passe correspondent
		psswdMatch = Main.passwordMatch(txt1.getPassword(),txt2.getPassword());
		if(psswdMatch == true){ //si oui on passe a la suite
			if(txt1.getPassword().length>=8){
				String login = userIdField.getText();
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
					initPsswd.setVisible(false);
					Main.p = new Password (txt2.getPassword(),userIdField.getText());
					Main.userId = userIdField.getText();
					f.initBdGui(Main.p,domainField.getText(),passwordLengthSlider.getValue());
					Main.sessionManager.getCurrentSession().setUserId(login);
					Main.sessionManager.getCurrentSession().setDomain(domain);
					Main.sessionManager.getCurrentSession().setPassword(new String (txt1.getPassword()));
					setVisible(false);
				}else{
					new SimpleWarning("L'un des champs est trop court");
				}
			}else {
				new SimpleWarning("Mot de passe trop court \n min: 8");
			}
		}else{ // sinon on recommence
			new SimpleWarning("Les mots de passe ne correspondent pas");

			txt1.setText("");
			txt2.setText("");
		}
	
	}
	

}