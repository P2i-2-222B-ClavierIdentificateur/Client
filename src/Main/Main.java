package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Database.ConnectionBD;
import GUI.MenuGUI;
import Session.SessionManager;


public class Main {
	
	public static Password p;
	public static String userId;
	public static boolean tests = false; //variable qui permet d'attendre que tous les mots de passe soit entrees
	public static FileWriter resultats;	//permet d'ecrire les resultats dans un fichier resultats.csv
	public static PrintWriter out;	//Complement du file writter (pas forcement utile d'ailleur)
	public static double [][] times; //contient les valeurs de temps separant chaque touche pour les differents essais
	public static SessionManager sessionManager;
	
	public static void main(String[] args) throws InterruptedException{
		
		ConnectionBD.connect();
				
		sessionManager =new SessionManager();
		
		//	GUI initGui = new GUI(); //initialisation de l'interface
		MenuGUI mg = new MenuGUI(); 
		
		try {
			// initialisation du FileWriter
			resultats = new FileWriter("resultats.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//initialisation du PrintWriter
		out= new PrintWriter(resultats);
		initCSV(); // creation de la structure de .csv
		//tant que le mot de passe n'est pas cree, on attend qu'il soit cree
		while(p==null){
			
			Thread.sleep(1000);
		}
		//une fois le mot de passe cree, on passe a l'etape suivante avec l'autre interface

		//on attend que les mots de passe soient entres
		while (tests == false){
			Thread.sleep(1000);
		}
		
		ConnectionBD.closeConnection();
		
		
	}
	
	//permet de tester si deux mots de passe correspondent
	public static boolean passwordMatch(char[] p1, char[] p2){
		if (p1.length == p2.length){ //on test d'abbord les longueurs pour gagner du temps
			//on test ensuite chaque caracere separement
			for (int i=0; i<p1.length;i++){
				if (p1[i]!=p2[i]){
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
	}
	
	//cree la structure du fichier resultats.csv
	public static void initCSV (){
		System.out.println("initialise");
		out.print("cycle"); // cree la colonne cycle
		out.print(",");
		for(int i=0; i<10; i++){
			out.print("char num" + i ); // cree la colonne correspondant a chaque caractere du mot de passe
			out.print(",");
		}
		out.print("userId");
		out.println(""); // retour a la ligne
		
	}
	
	//ecrit les intervalles de temps pour chaque caractere
	public static void writeCSV (int n , double[] times,Password p) {
		
		out.print((n));//index
		out.print(",");
		int i;
		for(i=0;i<times.length;i++){
			out.print(times[i]); // ecrit la valeur correspondant au caractere i
			System.out.println(String.valueOf(i + "   " +times[i]));
			out.print(",");
		}
		
		// adaptation au nombre max de carcteres prevus par iniCSV() :
		while (i<10){
			out.print(",");
			i++;
		}
		// On ajoute l'indentifiant correspondant aux donnees :
		out.print(p.getUserID());
		out.println("");
		
	}
	
	// permet d'ajouter des donnees au resultats.compiles.csv
	public static void compileMesures(double[] times,String userId){
		//On commence par aller chercher la derniere valeur d'index
		String line="";
		int n=0;
		try {
			//on initialise le reader
			BufferedReader br = new BufferedReader(new FileReader("resultats-compiles.csv"));
			// On compte le nombre de lignes
			while((line = br.readLine())!=null){
				n++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			// On ecrit les valeurs dans le fichier
			FileWriter  writer = new FileWriter("resultats-compiles.csv",true);
			writer.append(String.valueOf(n-1)+",");
			for(int i =0; i<times.length; i++){
				writer.append(String.valueOf(times[i])+",");
			}
			writer.append(userId + "\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
