package Analyse;

import java.util.Iterator;
import java.util.LinkedList;

import KeystrokeMeasuring.KeyStroke;

public abstract class GaussTest {

	private static final int nbParams = 15;
	
	public static int getNbparams() {
		return nbParams;
	}

	protected static double[][] getAvgMatrix(LinkedList<KeyStrokeSet> sets) {

		// On definit la matrice des moyennes pour chaque parametre de chaque
		// touche
		double[][] avgMatrix = new double[sets.getFirst().getSet().size()][nbParams];

		Iterator<KeyStrokeSet> setsIter = sets.iterator();

		// On calcule la moyenne de chaque parametre
		while (setsIter.hasNext()) {

			LinkedList<KeyStroke> strokes = setsIter.next().getSet();
			Iterator<KeyStroke> strokesIter = strokes.iterator();
			int keyIndex = 0;

			while (strokesIter.hasNext()) {
				KeyStroke curr = strokesIter.next();
				double[] values = curr.getValues();
				for (int i = 0; i < values.length; i++) {
					avgMatrix[keyIndex][i] += (values[i] / ((double) sets.size()));
				}
				keyIndex++;

			}

			System.out.println("End of sets iteration");

		}

		return avgMatrix;

	}

	protected static double[][] getStandardDeviationMatrix(LinkedList<KeyStrokeSet> sets, double[][] avgMatrix) {
		double[][] standardDeviationMatrix = new double[sets.getFirst().getSet().size()][nbParams];

		// On reinitialise l'iterateur de sets
		Iterator<KeyStrokeSet> setsIter = sets.iterator();

		// On calcule la matrice des variances
		while (setsIter.hasNext()) {

			LinkedList<KeyStroke> strokes = setsIter.next().getSet();
			Iterator<KeyStroke> strokesIter = strokes.iterator();
			int keyIndex = 0;

			while (strokesIter.hasNext()) {

				double[] values = strokesIter.next().getValues();
				for (int i = 0; i < values.length; i++)
					standardDeviationMatrix[keyIndex][i] += Math.pow(values[i] - avgMatrix[keyIndex][i], 2)
							/ ((double) sets.size());
				keyIndex++;

			}

		}

		// L'ecart-type est la racine carree de la variance
		for (int i = 0; i < standardDeviationMatrix.length; i++)
			for (int j = 0; j < standardDeviationMatrix[i].length; j++)
				standardDeviationMatrix[i][j] = Math.sqrt(standardDeviationMatrix[i][j]);

		return standardDeviationMatrix;

	}

}
