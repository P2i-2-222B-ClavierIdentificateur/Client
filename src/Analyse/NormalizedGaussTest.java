package Analyse;

import java.util.Iterator;
import java.util.LinkedList;

import Exception.BadLoginException;
import KeystrokeMeasuring.KeyStroke;
import Main.Account;

public class NormalizedGaussTest {
	
	public static boolean test(KeyStrokeSet testSet, Account account) throws BadLoginException {

		LinkedList<KeyStrokeSet> sets = KeyStrokeSet.buildReferenceSet(account);

		double[][] avgMatrix = GaussTest.getAvgMatrix(sets);
		double[][] sdMatrix = GaussTest.getStandardDeviationMatrix(sets, avgMatrix);

		Iterator<KeyStroke> keyIter = testSet.getSet().iterator();
		int keyIndex = 0;

		double normValue = 0.0;
		
		double normValueThreshold = avgMatrix.length * GaussTest.getNbparams();

		while (keyIter.hasNext()) {
			double[] tempValues = keyIter.next().getValues();
			for (int i = 0; i < tempValues.length; i++) {
				normValue += Math.abs(tempValues[i] - avgMatrix[keyIndex][i]) / sdMatrix[keyIndex][i];
			}
			keyIndex++;
		}
		
		System.out.println("value: " + normValue + "|threshold: " + normValueThreshold);
		
		if(normValue <= normValueThreshold)
			return true;
		else return false;		

	}

}
