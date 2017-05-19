package GUI;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import Main.Password;

public class MenuGUI extends JFrame {
	
	public JPanel mainPane;
	public MenuPane menuPane;
	public AddAccountGUI createAccountPane; // panel permattant la creation d'un nouveau compte
	JPanel requestPsswdPane; // panel permettant la recuperation d'un mot de passe associe a un compte
	public SpringLayout layout; // le layout
	// deux boutons pour choisir entre creer un compte et recuperer un mot de passe
	JButton create;
	JButton request;
	BDGUI bdGui;
	PasswordPane passwordPane;

	
	GetPasswordGUI getPsswdPane;
	
	public MenuGUI () {
		
		setLocation(300,300);
		setSize(500,200);
		
		layout = new SpringLayout ();
		

		
		mainPane = new JPanel();
		mainPane.setBackground(Color.blue);
		mainPane.setLayout(layout);
		setResizable(false);
		
		menuPane = new MenuPane(this);
		

		mainPane.add(menuPane);
		
		
		layout.putConstraint(SpringLayout.WEST, menuPane, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, menuPane, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, menuPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, menuPane, 0, SpringLayout.NORTH, mainPane);
		
		createAccountPane = new AddAccountGUI(menuPane,this);
		
		mainPane.add(createAccountPane);
		
		getPsswdPane = new GetPasswordGUI(menuPane,this);
		mainPane.add(getPsswdPane);

		
		layout.putConstraint(SpringLayout.WEST, menuPane, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, menuPane, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, menuPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, menuPane, 0, SpringLayout.NORTH, mainPane);
		
		layout.putConstraint(SpringLayout.WEST, getPsswdPane, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, getPsswdPane, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, getPsswdPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, getPsswdPane, 0, SpringLayout.NORTH, mainPane);
		
		
		layout.putConstraint(SpringLayout.WEST, createAccountPane, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, createAccountPane, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, createAccountPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, createAccountPane, 0, SpringLayout.NORTH, mainPane);
		
		
		
		setContentPane(mainPane);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void initBdGui(Password p,String domaine,int passwordLength){
		bdGui = new BDGUI(p,domaine,passwordLength,this);
		mainPane.add(bdGui);
		layout.putConstraint(SpringLayout.WEST, bdGui, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, bdGui, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, bdGui, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, bdGui, 0, SpringLayout.NORTH, mainPane);
		bdGui.setVisible(true);
		System.out.println("test");
		
	}
	
	public void hideBdGui(){
		bdGui.setVisible(false);
	}
	
	public void showPasswordPane(String generatedPassword){
		passwordPane = new PasswordPane(generatedPassword,this);
		mainPane.add(passwordPane);
		if(bdGui != null){
			if(bdGui.isVisible()){
				bdGui.setVisible(false);
			}
		}else if (getPsswdPane.isVisible()){
			getPsswdPane.setVisible(false);
		}
		layout.putConstraint(SpringLayout.WEST, passwordPane, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, passwordPane, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, passwordPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, passwordPane, 0, SpringLayout.NORTH, mainPane);
	}
	
	public void showMenuPane(){
		if(bdGui.isVisible()){
			bdGui.setVisible(false);
		}
		menuPane.setVisible(true);
	}

	
}
