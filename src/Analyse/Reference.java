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
		buildSets(p);
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
	
	/**
	 * Cr�e l'historique des entr�es r�ussies
	 * @param p le mot de passe qui sert au d�chiffrement des donn�es
	 */
	private void buildSets(Password p){
		sets = new LinkedList<KeyStrokeSet>();
		for (int i=0; i<usedRows.length; i++){
			ResultSet keys = Request.getTouchesForEntry(usedRows[i]);
			LinkedList <KeyStroke> set = new LinkedList<KeyStroke>();
			ArrayList<String> tempEncryptedValues = new ArrayList<String>();
			try {
				while(keys.next()){
					for(int j=2; j<=keys.getMetaData().getColumnCount(); j++) //on part de 2 pour �liminer l'index
						tempEncryptedValues.add(keys.getString(j)); //on r�cup�re toutes les donn�es pour un caract�re
					set.add(new KeyStroke(tempEncryptedValues,p)); //on cr�e un nouveau caract�re
					set.clear(); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			sets.add(new KeyStrokeSet(set));
		}
	}
	
	

}
