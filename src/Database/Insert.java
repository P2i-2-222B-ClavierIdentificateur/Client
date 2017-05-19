package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Encryption.Encryption;
import Main.Password;
import Session.Session;



public class Insert {

	
	public static void addCompte(Password p,String domain,int passwordLength){
		
		System.out.println("Ajout d'un compte");
		String ePassword = Encryption.encryptPassword(p.toString());
		int  eLogin = p.getUserID().hashCode();
		int hDomain = domain.hashCode();
		String ePasswordLength = Encryption.encryptInt(passwordLength, p.toString());
		
		try {
			String compte = "INSERT INTO Compte (Login,masterPassword,domainHash,passwordLength,CompteSystem_Login) values (?,?,?,?,?);";
			PreparedStatement compteStatement = ConnectionBD.conn.prepareStatement(compte);
			compteStatement.setInt(1, eLogin);
			compteStatement.setString(2, ePassword);
			compteStatement.setInt(3, hDomain);
			compteStatement.setString(4, ePasswordLength);
			compteStatement.setString(5, "test");
			//TODO ceci ne doit pas toujours etre un test
			compteStatement.execute();
			System.out.println("Compte ajouté");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur lors de l'ajout du compte");
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
		
		String account = "SELECT Compte.Index FROM Compte WHERE Login = ? and domainHash = ?"; 
						
		String session = "INSERT INTO Session (Compte_Index,sucess) values(?,?); ";
		
		String sessionIndex = "SELECT max(Session.index) FROM Session;";
		
		String entree = "INSERT INTO Entree (Session_index,Local,try) values (?,?,?)";
		
		String entreeIndex = "SELECT max(Entree.Index) From Entree where Session_index = ?;";
		
		String touche = "INSERT INTO Touche (Entree_Index,timeUp,timeDown,pressure,modifierSequence,"
				+ "shiftUp,shiftDown,shiftLocation,ctrlUp,ctrlDown,ctrlLocation,altUp,altDown,"
				+ "altLocation,capslockUp,capsLockDown) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";		
                
        PreparedStatement accountStatement;
		try {
			accountStatement = ConnectionBD.conn.prepareStatement(account);
			accountStatement.setInt(1,userId);
			accountStatement.setInt(2,domain);
			res = accountStatement.executeQuery();
			
			int accountId = 0;
			while (res.next()){
				accountId = res.getInt(1);
			}
		        
			PreparedStatement sessionStatement = ConnectionBD.conn.prepareStatement(session);
			sessionStatement.setInt(1,accountId);
			sessionStatement.setString(2,Encryption.encryptBoolean(s.isSuccess(), s.getPassword()));
			sessionStatement.executeUpdate();
			
			Statement sessionIndexStatement = ConnectionBD.conn.createStatement();
			res = sessionIndexStatement.executeQuery(sessionIndex);
			int sessionId = 0;
			while(res.next()){
				sessionId = res.getInt("max(Session.index)");
			}
				
			for (int i=0; i<s.getPasswordTries().size();i++){
				PreparedStatement entreeStatement = ConnectionBD.conn.prepareStatement(entree);
				entreeStatement.setInt(1,sessionId);
				entreeStatement.setString(2,s.getLocal());
				entreeStatement.setInt(3,i);
				entreeStatement.executeUpdate();
					
				PreparedStatement entreeIndexStatement = ConnectionBD.conn.prepareStatement(entreeIndex);
				entreeIndexStatement.setInt(1, sessionId);
				res = entreeIndexStatement.executeQuery();
				int entreeId=0;
				while(res.next()){
					entreeId = res.getInt("max(Entree.Index)");
				}
				for(int j=0; j<s.getPasswordTries().get(i).getKeys().size();j++){
					PreparedStatement toucheStatement = ConnectionBD.conn.prepareStatement(touche);
					ArrayList<String>encryptedValues = s.getPasswordTries().get(i).getKeys().get(j).getEncryptedValues(new Password(s.getPassword().toCharArray(),s.getUserId()));
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
       
	}
}