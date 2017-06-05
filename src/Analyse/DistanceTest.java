package Analyse;

import java.util.Iterator;
import java.util.LinkedList;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

import Exception.BadLoginException;
import GUI.DatabaseWorkFrame;
import KeystrokeMeasuring.KeyStroke;
import Main.Account;

public class DistanceTest {
	

	// TODO régler les valeurs des seuils
	private static final double euclidianRatioThreshold = 0.45;

	// TODO fusionner login,domain et password dans une instace ce compte
	public static boolean test(KeyStrokeSet bruteTestSet, Account account) throws BadLoginException {
		try {

			LinkedList<KeyStrokeSet> bruteSets = new LinkedList<KeyStrokeSet>(KeyStrokeSet.buildReferenceSet(account));
			GaussNormalizer gn = new GaussNormalizer(bruteSets);
			LinkedList<KeyStrokeSet> sets = gn.getNormalizedSets();
			KeyStrokeSet testSet = gn.normalizeKeyStrokeSet(bruteTestSet);
			double[][] euclidianDistances = buildEuclidianDistances(testSet, sets);
			double[] avgEuclidianDistance = new double[euclidianDistances.length];
			double[] avgEuclidianDistanceRatio = new double[euclidianDistances.length];
			double avgEuclidianRatio = 0;
			Iterator<KeyStrokeSet> setsIter = sets.iterator();
			int i = 0;
			while (setsIter.hasNext()) {
				avgEuclidianDistance[i] = 0;
				KeyStrokeSet tempSet = setsIter.next();
				Iterator<KeyStroke> tempIter = tempSet.getSet().iterator();
				Iterator<KeyStroke> testIter = testSet.getSet().iterator();
				int j = 0;
				while (testIter.hasNext()) {
					KeyStroke testStroke = testIter.next();
					KeyStroke refStroke = tempIter.next();
					if (testStroke.getNorme2() != 0 && i < euclidianDistances.length
							&& j < euclidianDistances[i].length) {
						avgEuclidianDistance[i] += euclidianDistances[i][j];
						avgEuclidianDistanceRatio[i] += euclidianDistances[i][j]
								/ (testStroke.getNorme2() + refStroke.getNorme2());
					}
					j++;

				}
				avgEuclidianDistanceRatio[i] /= euclidianDistances[i].length;
				avgEuclidianDistance[i] /= euclidianDistances[i].length;

				avgEuclidianRatio += avgEuclidianDistanceRatio[i];
				i++;

			}
			avgEuclidianRatio /= (euclidianDistances.length);
			System.out.println("avg Euclidiant Distance : " + avgEuclidianRatio);

			return (avgEuclidianRatio < euclidianRatioThreshold);
		} catch (EncryptionOperationNotPossibleException e) {
			throw new BadLoginException();
		}
	}

	private static double[][] buildEuclidianDistances(KeyStrokeSet testSet, LinkedList<KeyStrokeSet> sets) {
		double[][] distances = new double[sets.size()][];
		Iterator<KeyStrokeSet> setsIter = sets.iterator();
		int i = 0;
		while (setsIter.hasNext()) {
			KeyStrokeSet tempSet = setsIter.next();
			distances[i] = new double[tempSet.getSet().size()];
			Iterator<KeyStroke> tempIter = tempSet.getSet().iterator();
			Iterator<KeyStroke> testIter = testSet.getSet().iterator();
			int j = 0;
			while (tempIter.hasNext()) {
				if (j < testSet.getSet().size()) {
					distances[i][j] = testIter.next().euclidianDistance(tempIter.next());
				} else {
					System.out.println(testSet.getSet().size());
					distances[i][j] = 0;
				}
				j++;
			}
			i++;
		}
		return distances;
	}
	
	
	
}