package Main;

import java.sql.Connection;

import Database.ConnectionBD;
import GUI.MenuGUI;
import Session.SessionManager;
import Sync.SyncUtil;

public class Main {

	public static SessionManager sessionManager;
	public static SystemAccount currentSystemAccount;
	public static Connection conn;
	public static MenuGUI mg ;

	public static void main(String[] args) throws InterruptedException {
		conn = ConnectionBD.connect();
		sessionManager = new SessionManager();
		// GUI initGui = new GUI(); //initialisation de l'interface
		mg = new MenuGUI();
		//SyncUtil sync = new SyncUtil();
		// sync.start();
	}

	// permet de tester si deux mots de passe correspondent
	public static boolean passwordMatch(char[] p1, char[] p2) {
		if (p1.length == p2.length) { // on test d'abbord les longueurs pour
										// gagner du temps
			// on test ensuite chaque caracere separement
			for (int i = 0; i < p1.length; i++) {
				if (p1[i] != p2[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

}
