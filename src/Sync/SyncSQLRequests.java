package Sync;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SyncSQLRequests {
	
	public static int[] getMaxIndexes(Connection conn){
		String query = "SELECT max(Compte.Index),max(Session.index),max(Entree.Index),"
				+" FROM CompteSystem,Compte,Session,Entree,Touche;";
		ResultSet rs = null;
		int[] t = new int[5];
		try {
			Statement st  = conn.createStatement();
			rs=st.executeQuery(query);
			rs.first();
			for(int i=0;i<t.length;i++){
				t[i] = rs.getInt(i+1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

}
