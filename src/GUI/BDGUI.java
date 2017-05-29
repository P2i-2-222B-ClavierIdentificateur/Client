package GUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;

import Database.Insert;
import KeystrokeMeasuring.TimingManager;
import Main.Entry;
import Main.Main;
import Main.Account;
import Main.PasswordGetter;
import Main.PasswordTry;

@SuppressWarnings("serial")
public class BDGUI extends JPanel{ //fenetre ou se fait la saisie des mots de passe pour la BD 
									//(qui pour le moment est juste un fichier csv)
	
	JPanel mainPanel; // le panel principal
	JPasswordField psswd; // le champ de mot de passe
	Account account;
	int passwordLength;
	public JPanel progressBar;
	
	
	MenuGUI f;
	int validTries = 0;
	
	
	public BDGUI(Account account,int passwordLength, MenuGUI f){
		//On initialise tout
		this.f=f;
		this.account = account;
		this.passwordLength = passwordLength;
		JLabel label1 = new JLabel ("Saisir le mot de passe 15 fois sans erreur");
		psswd = new JPasswordField ("",15);

		
		this.setBackground(Color.DARK_GRAY);
		
		// un panel servant de barre de progression pour compter le nombre de mots de passe 
		// entres correctement
		progressBar = new JPanel(){
			
		
			public void paintComponent (Graphics g){
				g.setColor(Color.white);
				g.fillRect((int)(0), 0,350-20, 50);
				
				for(int i=0; i<Main.sessionManager.getCurrentSession().getPasswordTries().size(); i++){

					g.setColor(Color.blue);
					g.fillRect((int)(5 + i*((350-10)/15.0)), 5,(int)( (350-10)/15.0-5), 50-10);
				}
			}
		};
		
		this.add(progressBar);
		progressBar.repaint();
		
		label1.setForeground(Color.white);
		TimingManager timingManager = new TimingManager(account,psswd);		
		psswd.addKeyListener(timingManager);
		psswd.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					Main.sessionManager.getCurrentSession().reshceduleEnd();

					
						
						if(Main.sessionManager.getCurrentSession().getPasswordTries().size()>=15){
							flushSession();
						}
					
						progressBar.repaint();
						timingManager.getKeyStrokes().clear();
						timingManager.getStrokes().clear();
						//System.out.println("KeyStrokes : " +timingManager.getKeyStrokes().size() + " Strokes : " + timingManager.getStrokes().size());

					}else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE){
						psswd.setText("");
					}
					


				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.add(label1);
		this.add(psswd);
		
		SpringLayout layout = new SpringLayout(); 
		setLayout(layout);

		
		// on positionne la barre de progression
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, progressBar,0,SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, progressBar,-10,SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.NORTH, progressBar,-50,SpringLayout.SOUTH, progressBar);
        layout.putConstraint(SpringLayout.WEST, progressBar,10,SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, progressBar,-10,SpringLayout.EAST, this);

        
        
		
		// positionnement de label1
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, label1,0,SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, label1,5,SpringLayout.NORTH, this);
        
        // positionnement de psswd 
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, psswd,0,SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, psswd,5,SpringLayout.SOUTH, label1);
		
        

		setVisible(false);
	}
	
	private void flushSession(){

		for(int i=0; i<Main.sessionManager.getCurrentSession().getPasswordTries().size();i++){
			Main.sessionManager.getCurrentSession().getPasswordTries().get(i).setSuccess(true);
		}
		Insert.addCompte(account,passwordLength,Main.conn);
		String generatedPassword = PasswordGetter.generatePassword(account,passwordLength);
		Main.sessionManager.getCurrentSession().setAccount(account);
		Main.sessionManager.getCurrentSession().setSuccess(true);
		Main.sessionManager.endCurrentSession();
		f.showPasswordPane(generatedPassword);
		f.hideBdGui();

	}
	

	


}