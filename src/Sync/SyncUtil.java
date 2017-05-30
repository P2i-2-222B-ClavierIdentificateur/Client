package Sync;

import java.sql.Connection;
import java.sql.SQLException;

public class SyncUtil extends Thread {
	
	String INSADatabase = "jdbc:mysql://5.196.123.198/G222_B_BD3";
	String remoteDatabase = "jdbc:mysql://5.196.123.198/P2I";
	
	public SyncUtil (){
		super();
	}
	
	@Override
	public void run(){
		System.out.println("Syncronisation...");
		Connection INSA = ConnectionManager.createConnection(INSADatabase);
		Connection remote = ConnectionManager.createConnection(remoteDatabase);
		if(INSA != null && remote != null){
			int []INSAIndexes = SyncSQLRequests.getMaxIndexes(INSA);
			int[] remoteIndexes = SyncSQLRequests.getMaxIndexes(remote);
			
			for(int i=0; i<INSAIndexes.length; i++){
				if(INSAIndexes[i]!=remoteIndexes[i]){
					
					switch(i){
					case 0:
						
						System.out.println("Syncronisation de CompteSytem...");
						System.out.println("INSA : " + INSAIndexes[i]+ "    remote : " + remoteIndexes[i]);

						if(remoteIndexes[i]<INSAIndexes[i]){
							Updater.updateCompteSystem(INSA, remote, remoteIndexes[i]);
						}else{
							Updater.updateCompteSystem(remote, INSA, INSAIndexes[i]);
						}
						break;
						
					case 1:

						System.out.println("Syncronisation de Compte...");
						System.out.println("INSA : " + INSAIndexes[i]+ "    remote : " + remoteIndexes[i]);

						if(remoteIndexes[i]>INSAIndexes[i]){
							Updater.updateCompte(remote, INSA, INSAIndexes[i]);
						}else{
							Updater.updateCompte(INSA, remote, remoteIndexes[i]);
						}
						break;
						
					case 2:

						System.out.println("Syncronisation Session ...");
						System.out.println("INSA : " + INSAIndexes[i]+ "    remote : " + remoteIndexes[i]);

						if(remoteIndexes[i]>INSAIndexes[i]){
							Updater.updateSession(remote, INSA, remoteIndexes[i]);
						}else{
							Updater.updateSession(INSA, remote, INSAIndexes[i]);
						}
						break;
						
					case 3:

						System.out.println("Syncronisation Entree ...");
						System.out.println("INSA : " + INSAIndexes[i]+ "    remote : " + remoteIndexes[i]);

						if(remoteIndexes[i]>INSAIndexes[i]){
							Updater.updateEntree(remote, INSA, INSAIndexes[i]);
						}else{
							Updater.updateEntree(INSA, remote, remoteIndexes[i]);
						}
						break;
						
					case 4:

						System.out.println("Syncronisation Touche ...");
						System.out.println("INSA : " + INSAIndexes[i]+ "    remote : " + remoteIndexes[i]);

						if(remoteIndexes[i]>INSAIndexes[i]){
							Updater.updateTouche(remote, INSA, INSAIndexes[i]);
						}else{
							Updater.updateTouche(INSA, remote, remoteIndexes[i]);
						}
						break;
						
					default:
						break;
					}
				}
			}
		}
		try {
			INSA.close();
			remote.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Syncronisé.");
	}
	
}
