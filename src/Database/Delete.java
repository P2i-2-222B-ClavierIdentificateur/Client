package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import Main.Account;

public class Delete {
	
	public static int deleteTouchesForEntry(int entry){
		Connection conn = Main.Main.conn;
		
		String query = "DELETE FROM Touche WHERE Touche.Entree_Index = ?";
		try {
			Statement begin = conn.createStatement();
			begin.executeQuery("Start Transaction;");
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, entry);
			int n= st.executeUpdate();
			Statement commit = conn.createStatement();
	        commit.executeQuery("Commit;");
	        return n;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static LinkedList<Integer> getEntriesForSession(int session){
		Connection conn = Main.Main.conn;
		String entries = "Select Entree.Index From Entree Where Entree.Session_index = ?";
		LinkedList<Integer>entriesIndexes = new LinkedList<Integer>();
		try {
			PreparedStatement st = conn.prepareStatement(entries);
			st.setInt(1, session);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()){
				entriesIndexes.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entriesIndexes;
	}
	
	public static void deleteEntriesForSession(int session){
		Connection conn = Main.Main.conn;

		try {
			Statement begin = conn.createStatement();
			begin.executeQuery("Start Transaction;");
			String  query = "Delete From Entree Where Entree.Session_index = ?;";
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, session);
			st.executeUpdate();
			Statement commit = conn.createStatement();
	        commit.executeQuery("Commit;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static LinkedList<Integer> getSessionsForAccount(int account){
		Connection conn = Main.Main.conn;
		String sessions = "Select Session.index From Session Where Session.Compte_Index"
				+ "=?;";
		LinkedList<Integer>sessionsIndexes = new LinkedList<Integer>();
		try {
			PreparedStatement st = conn.prepareStatement(sessions);
			st.setInt(1, account);
			ResultSet rs = st.executeQuery();
			while(rs.next()){
				sessionsIndexes.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sessionsIndexes;
	}
	
	public static void deleteSessionsForAccount(int account){
		Connection conn = Main.Main.conn;
		
		try {
			Statement begin = conn.createStatement();
			begin.executeQuery("Start Transaction;");
			String query = "Delete From Session Where Session.Compte_Index = ?;";
			PreparedStatement st=conn.prepareStatement(query);
			st.setInt(1, account);
			st.executeUpdate();
			Statement commit = conn.createStatement();
	        commit.executeQuery("Commit;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void deleteAccount(int account){
		Connection conn = Main.Main.conn;
		String query = "Delete from Compte Where Compte.Index = ?;";
		try {
			Statement begin = conn.createStatement();
			begin.executeQuery("Start Transaction;");
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, account);
			st.executeUpdate();
			Statement commit = conn.createStatement();
	        commit.executeQuery("Commit;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static int getAccountIndex(Account account){
		Connection conn = Main.Main.conn;
		String query = "Select Compte.Index From Compte Where Compte.Login = ?"
				+ " and Compte.domainHash = ? and Compte.CompteSystem_Login = ?"
				+ " Limit 1;";
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, account.getLoginHash());
			st.setInt(2,account.getDomainHash());
			st.setInt(3, account.getSysAccount().getSysLoginHash());
			ResultSet rs = st.executeQuery();
			rs.first();
			return rs.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}
