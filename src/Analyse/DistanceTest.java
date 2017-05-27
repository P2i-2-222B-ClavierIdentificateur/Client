package Analyse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import Database.ConnectionBD;
import Database.Request;
import KeystrokeMeasuring.KeyStroke;
import Main.Password;

public class DistanceTest {
	
	//TODO régler les valeurs des seuils
	private static final double euclidianRatioThreshold = 0.37;
	private static final double manhattanRatioThreshold = 0.1;
	
	//TODO fusionner login,domain et password dans une instace ce compte
	public static boolean test(KeyStrokeSet testSet, String login, String domain, String password){
		LinkedList <KeyStrokeSet> sets = KeyStrokeSet.buildReferenceSet(login,domain,password);
		double[][] euclidianDistances = buildEuclidianDistances(testSet,sets);
		double[][] manhattanDistances = buildManhattanDistances(testSet,sets);
		double[] avgEuclidianDistance  = new double [euclidianDistances.length];
		double[] avgManhattanDistance = new double [manhattanDistances.length];
		double[] avgEuclidianDistanceRatio = new double [euclidianDistances.length];
		double[] avgManhattanDistanceRatio = new double [manhattanDistances.length];
		double avgEuclidianRatio = 0;
		double avgManhatanRatio = 0;
		int i;
		for(i=0; i<euclidianDistances.length;i++){
			avgEuclidianDistance[i] = 0;
			avgManhattanDistance[i] = 0;
			for(int j=0; j<euclidianDistances[i].length;j++ ){

				if(testSet.getSet().get(j).getNorme2()!=0 && testSet.getSet().get(j).getNorme1()!=0 && j<testSet.getSet().size()){
					avgEuclidianDistance[i]+=euclidianDistances[i][j];
					avgManhattanDistance[i]+=manhattanDistances[i][j];
					avgEuclidianDistanceRatio[i]+=euclidianDistances[i][j]/(testSet.getSet().get(j).getNorme2()+sets.get(i).getSet().get(j).getNorme2());
					avgManhattanDistanceRatio[i]+=manhattanDistances[i][j]/(testSet.getSet().get(j).getNorme1()+sets.get(i).getSet().get(j).getNorme1());
				}
				System.out.println("Norme & : " + testSet.getSet().get(j).getNorme1() + " Norme 2 : " +testSet.getSet().get(j).getNorme2() );

				System.out.println("EuclidianDistance : " + euclidianDistances[i][j]+"      ManhattanDistance : " + manhattanDistances[i][j]);
				System.out.println("AvgEuclidianDistance : " + avgEuclidianDistance[i]);
				//System.out.println("AvgManhattanDistance : " + avgManhattanDistance[i]);
			}
			avgEuclidianDistanceRatio[i] /= euclidianDistances[i].length;
			avgManhattanDistance[i]/=manhattanDistances[i].length;
			avgEuclidianDistance[i]/=euclidianDistances[i].length;
			avgManhattanDistance[i]/=manhattanDistances[i].length;
			//System.out.println("AvgEuclidianDistance : " + avgEuclidianDistance[i]);
			//System.out.println("AvgManhattanDistance : " + avgManhattanDistance[i]);
			System.out.println("avgE : " + avgEuclidianDistance[i] + " avgM : " + avgManhattanDistance[i]);
			
			avgEuclidianRatio+=avgEuclidianDistanceRatio[i];
			avgManhatanRatio+=avgManhattanDistanceRatio[i];
			System.out.println("avgRE : " + avgEuclidianRatio + " avgRM : " + avgManhatanRatio);

		}
		avgEuclidianRatio /=(euclidianDistances.length);
		avgManhatanRatio/=(euclidianDistances.length);
		System.out.println("avgRE : " + avgEuclidianRatio + " avgRM : " + avgManhatanRatio);

		return(avgEuclidianRatio<euclidianRatioThreshold );
	}
	
	
	
	//TODO convertir en ArrayList pour gagner du temps à l'exécution?
	private static double[][] buildEuclidianDistances(KeyStrokeSet testSet,LinkedList <KeyStrokeSet> sets){
		//System.out.println("size : " + sets.get(0).getSet().size() );
		double[][] distances = new double[sets.size()][];
		for(int i=0;i<distances.length;i++){
			distances[i] = new double[sets.get(i).getSet().size()];
			for(int j=0; j<distances[i].length; j++){
				if(j<testSet.getSet().size()){
					distances[i][j] = testSet.getSet().get(j).euclidianDistance(sets.get(i).getSet().get(j));
				}else{
					System.out.println(testSet.getSet().size());
					distances[i][j] = 0;
				}
			}
		}
		return distances;
	}
	
	//TODO convertir en ArrayList pour gagner du temps à l'exécution?
	private static double[][] buildManhattanDistances(KeyStrokeSet testSet,LinkedList <KeyStrokeSet> sets){
		double[][] distances = new double[sets.size()][];
		for(int i=0;i<distances.length;i++){
			distances[i] = new double[sets.get(i).getSet().size()];
			for(int j=0; j<distances[i].length; j++){
				if(j<testSet.getSet().size()){
					distances[i][j] = testSet.getSet().get(j).manhattanDistance(sets.get(i).getSet().get(j));
				}else{
					distances[i][j] = 0;
				}

			}
		}
		return distances;
	}


}