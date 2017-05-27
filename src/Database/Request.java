package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;


import Main.Main;

public class Request {
	
	public static ResultSet getLogin (int i,Connection conn){
   
      
        String request = "SELECT Login,masterPassword,passwordLength FROM Compte "
        		+ "WHERE CompteSystem_Login = ? and domainHash = ?;";

        PreparedStatement st;
        ResultSet rs = null;
		try {
			st = conn.prepareStatement(request);
			st.setString(1, String.valueOf(Main.currentSystemAccount.getLogin().hashCode()));
			st.setString(2, String.valueOf(i));
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return rs;
	}
	
	public static int[] getLastSuccessfulEntries (String login, String domain,Connection conn){
		
		int loginHash = login.hashCode();
		int domainHash = domain.hashCode();
		
		//TODO corriger la requête, elle ne gère pas le succès
		String request = "SELECT Entree.Index from Entree "
						+"	Where Entree.Session_index in (Select Session.index From Session "
						+"	Where Session.sucess = 1 and Session.Compte_Index in (Select Compte.Index From Compte"
						+"	Where Compte.Login = ? and Compte.domainHash = ?))"
						+"	Order by Entree.Index DESC Limit 50;";
		

        
        ResultSet res= null;
        int[] indexes = new int[50];
        
        try {
			PreparedStatement entriesStatement = conn.prepareStatement(request);
			entriesStatement.setInt(1, loginHash);
			entriesStatement.setInt(2, domainHash);
			res = entriesStatement.executeQuery();
			int i =0;
			while (res.next() && i<50){
				indexes[i] = res.getInt(1);
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return indexes;
	}
	
	public static ArrayList getTouchesForEntry(int entryIndex,Connection conn){
		String request = "Select * From Touche Where Touche.Entree_Index = ?;";

		
        ResultSet res= null;
        //System.out.println("test for entree : " + entryIndex);
        
        ArrayList<ArrayList> keys = new ArrayList<ArrayList>(16);
        try {
			PreparedStatement entriesStatement = conn.prepareStatement(request);
			entriesStatement.setInt(1,entryIndex);
			res = entriesStatement.executeQuery();
			while(res.next()){
		        ArrayList<String> values = new ArrayList<String>(16);
		        for(int i = 2;i<17;i++){
		        	values.add(res.getString(i));
		        	//System.out.println("String : " + res.getString(i));
		        }
		       // System.out.println("values" + values.size());
		        keys.add(values);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //System.out.println("keys before return : " + keys.size());
        return keys;
        
	}
	
	public static String getPasswordForSystemAccount(String login,Connection conn){
		
		login = String.valueOf(login.hashCode());
		System.out.println(login);
		
		String password = "";
		
		String request = "SELECT Password FROM CompteSystem WHERE Login = ? LIMIT 1;";
		
	
		PreparedStatement statement = null;
		
		ResultSet rs = null;
		
		try {
			statement = conn.prepareStatement(request);
			statement.setString(1, login);
			rs = statement.executeQuery();
			rs.next();
			password = rs.getString(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
		return password;
	}
	
	public static String getEncryptedPassword (String login, String domain,Connection conn){
		
		int loginHash = login.hashCode();
		int domainHash = domain.hashCode();
		
		
		String request = "SELECT masterPassword FROM Compte WHERE Login = ? AND domainHash = ?;";
		
		PreparedStatement statement = null;
		
		ResultSet rs = null;
		
		try {
			statement = conn.prepareStatement(request);
			statement.setInt(1, loginHash);
			statement.setInt(2, domainHash);
			rs = statement.executeQuery();
			rs.first();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			return rs.getString("masterPassword");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public static boolean checkIfAccountExists(String sysLogin, String login, String domain,Connection conn){
		int sysLoginHash = sysLogin.hashCode();
		int loginHash = login.hashCode();
		int domainHash = domain.hashCode();
		
		String query = "Select Compte.domainHash From Compte Where Compte.CompteSystem_Login = ? and Compte.Login = ?;";
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, String.valueOf(sysLoginHash));
			st.setInt(2, loginHash);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				if(rs.getInt(1) == domainHash){
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
		

}