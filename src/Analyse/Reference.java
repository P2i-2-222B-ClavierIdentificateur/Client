package Analyse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import Database.Request;
import KeystrokeMeasuring.KeyStroke;
import Main.Password;

public class Reference {
	
	LinkedList <KeyStrokeSet> sets;
	LinkedList<Double> avgEuclidianDistance;
	LinkedList<Double>avgManhattanDistance;
	int[] usedRows;
	
	public Reference (Password p, String domain, String systemLogin){
		getRows(p.getUserID(),domain);
	}
	
	private void getRows(String login, String domain){
		usedRows = new int[50];
		ResultSet res = Request.getLastSuccessfulEntries(login, domain);
		int i=0;
		try {
			while(res.next()){
				usedRows[i] = res.getInt(1);
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void buildSets(Password p){
		sets = new LinkedList<KeyStrokeSet>();
		for (int i=0; i<usedRows.length; i++){
			ResultSet keys = Request.getTouchesForEntry(usedRows[i]);
			LinkedList <KeyStroke> set = new LinkedList<KeyStroke>();
			ArrayList<String> tempEncryptedValues = new ArrayList<String>();
			try {
				while(keys.next()){
					for(int j=1; j<=keys.getMetaData().getColumnCount(); j++)
						tempEncryptedValues.add(keys.getString(j)); //on récupère toutes les données pour un caractère
					set.add(new KeyStroke(tempEncryptedValues,p)); //on crée un nouveau caractère
					set.clear(); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			sets.add(new KeyStrokeSet(set));
		}
	}
	
	

}
