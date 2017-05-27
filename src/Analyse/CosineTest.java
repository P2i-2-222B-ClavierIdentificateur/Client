package Analyse;

import java.util.Iterator;
import java.util.LinkedList;

import KeystrokeMeasuring.KeyStroke;

public class CosineTest {
	
	private static final double cosineSimilarityThreshold = 0.5; 

	public static boolean test(KeyStrokeSet testSet, String login, String domain,  String password ){
		
		LinkedList<KeyStrokeSet> sets = KeyStrokeSet.buildReferenceSet(login, domain, password);
		LinkedList<LinkedList<Double>> similarityMatrix = new LinkedList<LinkedList<Double>>();
		Iterator<KeyStrokeSet> setsIterator = sets.iterator();
		LinkedList<Double> averageSimilarity = new LinkedList<Double>();
		
		//On definit la matrice de similarite avec les similarites cosinus de chaque touche de chaque entree, on calcule aussi les similarites moyennes pour chaque entree
		while(setsIterator.hasNext()){
			
			LinkedList<KeyStroke> temp = setsIterator.next().getSet();
			
			if(temp.size() == testSet.getSet().size()){
				
				Iterator<KeyStroke> tempIterator = temp.iterator();
				Iterator<KeyStroke> testIterator = testSet.getSet().iterator();
				LinkedList<Double> tempSimilarities = new LinkedList<Double>();
				
				double somme = 0.0;
			
				while(tempIterator.hasNext()){
					
					KeyStroke test = testIterator.next();
					KeyStroke ref = tempIterator.next();
					double tempCosineSimilarity = test.getCosineSimilarity(ref); 
					tempSimilarities.add(tempCosineSimilarity);
					somme += tempCosineSimilarity;
				
				}
				
				similarityMatrix.add(tempSimilarities);
				averageSimilarity.add(somme / temp.size());
			
			} else return false; //si pas la bonne taille, le mot de passe est forcemment faux
		
		}
		
		boolean isTheSamePerson = true;
		
		Iterator<Double> averageSimilarityIterator = averageSimilarity.iterator();
		
		while(isTheSamePerson && averageSimilarityIterator.hasNext()){
			
			if(averageSimilarityIterator.next() < cosineSimilarityThreshold)
				isTheSamePerson = false;
						
		}
		
		//TODO traitement des autres donnees statistiques possibles (mediane, ecart-type...)
		
		return isTheSamePerson;
				
	}
}