package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

import Main.Entry;
import Main.Password;
import Session.Session;
import Encryption.Encryption;


// C'est moche mais bon ça marche...

public class Insert {

	
	public static void addCompte(Password p,String domain,int passwordLength){
		
		System.out.println("Ajout d'un compte");
		String ePassword = Encryption.encryptPassword(p.toString());
		int  eLogin = p.getUserID().hashCode();
		int hDomain = domain.hashCode();
		String ePasswordLength = Encryption.encryptInt(passwordLength, p.toString());
		
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e1) {
			System.err.println("Could not find driver");
			e1.printStackTrace();
			System.exit(0);

		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);

		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);

		}
        System.out.println("Driver Found...");
        try {
			conn = DriverManager.getConnection("jdbc:mysql://5.196.123.198:3306/" + "P2I", "G222_B", "G222_B");
		} catch (SQLException e1) {
			System.err.println("Could not connect to the database");
			e1.printStackTrace();
			System.exit(0);
		}
        System.out.println("Connected...");
        
       String compte = "INSERT INTO Compte (Login,masterPassword,domainHash,passwordLength,CompteSystem_Login) values (\""+eLogin+"\",\""+
    		   ePassword+"\",+"+hDomain+",\""+ePasswordLength+"\",\"thisisatest\");";
       
       try {
			Statement compteStatement = conn.createStatement();
			compteStatement.executeUpdate(compte);
			System.out.println("Compte ajouté");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur lors de l'ajout du compte");
		}
       try {
		conn.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
       
	}
		
	
	
	public static  void addSession(Session s){
		PreparedStatement insertEntry=null;
		Statement getId = null;
		ResultSet res = null;
		int nextId = 0;
		
		// on recupere le compte associe a la session
		
		int userId = s.getUserId().hashCode();
		
		int domain = s.getDomain().hashCode();
		
		String account = "SELECT Compte.Index FROM Compte WHERE Login = \""+userId+
				"\" and domainHash = " + domain+";"; 
				
		
		String session = "INSERT INTO Session (Compte_Index,sucess) values(?,?); ";
		
		String sessionIndex = "SELECT max(Session.index) FROM Session;";
		
		String entree = "INSERT INTO Entree (Session_index,Local,try) values (?,?,?)";
		
		String entreeIndex = "SELECT max(Entree.Index) From Entree where Session_index = ?;";
		
		String touche = "INSERT INTO Touche (Entree_Index,timeUp,timeDown,pressure,modifierSequence,"
				+ "shiftUp,shiftDown,shiftLocation,ctrlUp,ctrlDown,ctrlLocation,altUp,altDown,"
				+ "altLocation,capslockUp,capsLockDown) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		
		
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e1) {
			System.err.println("Could not find driver");
			e1.printStackTrace();
			System.exit(0);

		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);

		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);

		}
        System.out.println("Driver Found...");
        try {
			conn = DriverManager.getConnection("jdbc:mysql://5.196.123.198:3306/" + "P2I", "G222_B", "G222_B");
		} catch (SQLException e1) {
			System.err.println("Could not connect to the database");
			e1.printStackTrace();
			System.exit(0);
		}
        System.out.println("Connected...");
                
        Statement accountStatement;
		try {
			accountStatement = conn.createStatement();
			 res =accountStatement.executeQuery(account);
		        
		        int accountId = 0;
		        while (res.next()){
		        	accountId = res.getInt(1);
		        }
		        
		        PreparedStatement sessionStatement = conn.prepareStatement(session);
		        sessionStatement.setInt(1,accountId);
		        sessionStatement.setString(2,Encryption.encryptBoolean(s.isSuccess(), s.getPassword()));
				sessionStatement.executeUpdate();
				
				Statement sessionIndexStatement = conn.createStatement();
				res = sessionIndexStatement.executeQuery(sessionIndex);
				int sessionId = 0;
				while(res.next()){
					sessionId = res.getInt("max(Session.index)");
				}
				
				for (int i=0; i<s.getPasswordTries().size();i++){
					PreparedStatement entreeStatement = conn.prepareStatement(entree);
					entreeStatement.setInt(1,sessionId);
					entreeStatement.setString(2,s.getLocal());
					entreeStatement.setInt(3,i);
					entreeStatement.executeUpdate();
					
					PreparedStatement entreeIndexStatement = conn.prepareStatement(entreeIndex);
					entreeIndexStatement.setInt(1, sessionId);
					res = entreeIndexStatement.executeQuery();
					int entreeId=0;
					while(res.next()){
						entreeId = res.getInt("max(Entree.Index)");
					}
					for(int j=0; j<s.getPasswordTries().get(i).getKeys().size();j++){
						PreparedStatement toucheStatement = conn.prepareStatement(touche);
						ArrayList<String>encryptedValues = s.getPasswordTries().get(i).getKeys().get(j).getEncryptedValues(new Password(
								s.getPassword().toCharArray(),s.getUserId()));
						toucheStatement.setInt(1,entreeId);
						
						//TODO moddifier avec un iterator 
						int k=0;
						for (k=0; k<encryptedValues.size();k++){
							toucheStatement.setString(k+2,encryptedValues.get(k));
						}
						
						while(k+2<17){
							toucheStatement.setString(k+2, Encryption.encryptText("NULL", s.getPassword()));
							k++;
						}

						toucheStatement.executeUpdate();
					}
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        System.out.println("Session ajoutée");
       
        try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}