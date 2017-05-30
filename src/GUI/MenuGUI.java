package GUI;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import Main.Account;

@SuppressWarnings("serial")
public class MenuGUI extends JFrame implements WindowListener {
	
	public JPanel mainPane;
	public MenuPane menuPane;
	public AddAccountGUI createAccountPane; // panel permattant la creation d'un nouveau compte
	JPanel requestPsswdPane; // panel permettant la recuperation d'un mot de passe associe a un compte
	public SpringLayout layout; // le layout
	// deux boutons pour choisir entre creer un compte et recuperer un mot de passe
	public CreateAccountSystem createAccountSystem; //panel qui permet de creer un compte systeme
	public FirstPanel firstPanel;
	JButton create;
	JButton request;
	BDGUI bdGui;
	PasswordPane passwordPane;
	SystemConnectionPane systemConnectionPane;
	DeleteAccountPane deleteAccountPane;

	
	GetPasswordGUI getPsswdPane;
	
	public MenuGUI () {
		
		setLocation(300,300);
		setSize(500,200);
		
		layout = new SpringLayout ();
		

		
		mainPane = new JPanel();
		mainPane.setBackground(Color.blue);
		mainPane.setLayout(layout);
		//setResizable(false);
		
		menuPane = new MenuPane(this);
		

		mainPane.add(menuPane);
		
		
		layout.putConstraint(SpringLayout.WEST, menuPane, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, menuPane, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, menuPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, menuPane, 0, SpringLayout.NORTH, mainPane);
		
		createAccountPane = new AddAccountGUI(menuPane,this);
		
		mainPane.add(createAccountPane);
		
		//getPsswdPane = new GetPasswordGUI(menuPane,this);
		//mainPane.add(getPsswdPane);

		
		layout.putConstraint(SpringLayout.WEST, menuPane, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, menuPane, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, menuPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, menuPane, 0, SpringLayout.NORTH, mainPane);
		
	
		
		
		layout.putConstraint(SpringLayout.WEST, createAccountPane, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, createAccountPane, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, createAccountPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, createAccountPane, 0, SpringLayout.NORTH, mainPane);
		
		firstPanel = new FirstPanel(this); 
		mainPane.add(firstPanel);
		
		layout.putConstraint(SpringLayout.WEST, firstPanel, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, firstPanel, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, firstPanel, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, firstPanel, 0, SpringLayout.NORTH, mainPane);
		
		createAccountSystem = new CreateAccountSystem(this);
		mainPane.add(createAccountSystem);
		
		layout.putConstraint(SpringLayout.WEST, createAccountSystem, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, createAccountSystem, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, createAccountSystem, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, createAccountSystem, 0, SpringLayout.NORTH, mainPane);
		
		systemConnectionPane = new SystemConnectionPane(this);
		mainPane.add(systemConnectionPane);
		
		layout.putConstraint(SpringLayout.WEST, systemConnectionPane, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, systemConnectionPane, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, systemConnectionPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, systemConnectionPane, 0, SpringLayout.NORTH, mainPane);
		
		setContentPane(mainPane);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void showSystemConnectionPane (){
		systemConnectionPane.setVisible(true);
	}
	
	public void hideSystemConnectionPane (){
		systemConnectionPane.setVisible(false);
	}
	
	public void initBdGui(Account account,int passwordLength){
		bdGui = new BDGUI(account,passwordLength,this);
		mainPane.add(bdGui);
		layout.putConstraint(SpringLayout.WEST, bdGui, 0, SpringLayout.WEST, mainPane);
		layout.putConstraint(SpringLayout.EAST, bdGui, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, bdGui, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, bdGui, 0, SpringLayout.NORTH, mainPane);
		bdGui.setVisible(true);
		
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
	
	public void initGetPasswordPane(){
		getPsswdPane=new GetPasswordGUI(menuPane,this);
		mainPane.add(getPsswdPane);
		layout.putConstraint(SpringLayout.WEST, getPsswdPane, 0, SpringLayout.WEST,mainPane);
		layout.putConstraint(SpringLayout.EAST, getPsswdPane, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, getPsswdPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, getPsswdPane, 0, SpringLayout.NORTH, mainPane);
		getPsswdPane.setVisible(true);
		getPsswdPane.getDomainField().grabFocus();
	}
	
	public void initDeleteAccountPane(){
		deleteAccountPane=new DeleteAccountPane(menuPane,this);
		mainPane.add(deleteAccountPane);
		layout.putConstraint(SpringLayout.WEST, deleteAccountPane, 0, SpringLayout.WEST,mainPane);
		layout.putConstraint(SpringLayout.EAST, deleteAccountPane, 0, SpringLayout.EAST, mainPane);
		layout.putConstraint(SpringLayout.SOUTH, deleteAccountPane, 0, SpringLayout.SOUTH, mainPane);
		layout.putConstraint(SpringLayout.NORTH, deleteAccountPane, 0, SpringLayout.NORTH, mainPane);
		deleteAccountPane.setVisible(true);
		deleteAccountPane.getDomainField().grabFocus();
	}
	public void showMenuPane(){

		menuPane.setVisible(true);
	}
	
	public void showCreateAccountSystem(){
		createAccountSystem.setVisible(true);
	}
	
	public void hideCreateAccountSystem(){
		createAccountSystem.setVisible(false);

	}
	
	public void hideFirstPanel(){
		firstPanel.setVisible(false);
	}

	public JPanel getMainPane() {
		return mainPane;
	}

	public void setMainPane(JPanel mainPane) {
		this.mainPane = mainPane;
	}

	public MenuPane getMenuPane() {
		return menuPane;
	}

	public void setMenuPane(MenuPane menuPane) {
		this.menuPane = menuPane;
	}

	public AddAccountGUI getCreateAccountPane() {
		return createAccountPane;
	}

	public void setCreateAccountPane(AddAccountGUI createAccountPane) {
		this.createAccountPane = createAccountPane;
	}

	public JPanel getRequestPsswdPane() {
		return requestPsswdPane;
	}

	public void setRequestPsswdPane(JPanel requestPsswdPane) {
		this.requestPsswdPane = requestPsswdPane;
	}

	@Override
	public SpringLayout getLayout() {
		return layout;
	}

	public void setLayout(SpringLayout layout) {
		this.layout = layout;
	}

	public CreateAccountSystem getCreateAccountSystem() {
		return createAccountSystem;
	}

	public void setCreateAccountSystem(CreateAccountSystem createAccountSystem) {
		this.createAccountSystem = createAccountSystem;
	}

	public FirstPanel getFirstPanel() {
		return firstPanel;
	}

	public void setFirstPanel(FirstPanel firstPanel) {
		this.firstPanel = firstPanel;
	}

	public JButton getCreate() {
		return create;
	}

	public void setCreate(JButton create) {
		this.create = create;
	}

	public JButton getRequest() {
		return request;
	}

	public void setRequest(JButton request) {
		this.request = request;
	}

	public BDGUI getBdGui() {
		return bdGui;
	}

	public void setBdGui(BDGUI bdGui) {
		this.bdGui = bdGui;
	}

	public PasswordPane getPasswordPane() {
		return passwordPane;
	}

	public void setPasswordPane(PasswordPane passwordPane) {
		this.passwordPane = passwordPane;
	}

	public GetPasswordGUI getGetPsswdPane() {
		return getPsswdPane;
	}

	public void setGetPsswdPane(GetPasswordGUI getPsswdPane) {
		this.getPsswdPane = getPsswdPane;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		if(getPsswdPane != null)
			getPsswdPane.close();
		if(createAccountPane != null)
			createAccountPane.close(); 		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

	
}
