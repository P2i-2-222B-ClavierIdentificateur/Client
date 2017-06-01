package Arduino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import KeystrokeMeasuring.TimingManager;
import jssc.SerialPortException;

public class PressionManager implements Runnable {

	private ArrayList<Double> tabTriee;
	private LinkedList<Mesure> tabMesures;
	private ArduinoUsbChannel vcpChannel;
	private final TimingManager tm;
	private boolean stop;
	private boolean end = false;

	public PressionManager(TimingManager tm) {

		setStop(false);

		this.tm = tm;

		String port = null;

		// Recherche du port de l'Arduino

		System.out.println("RECHERCHE d'un port disponible...");
		port = ArduinoUsbChannel.getOneComPort();

		if (port != null) {
			try {
				vcpChannel = new ArduinoUsbChannel(port);
			} catch (IOException e) {
				e.printStackTrace(System.err);
			}
		} else {
			System.out.println("Impossible de se connecter au module de mesure de pressions, poursuite du programme "
					+ "sans mesure de pressions");
			tm.setArduinoConnected(false);
			this.setEnd(true);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ex) {
			}
		}
	}

	@Override
	public void run() {
		BufferedReader vcpInput = null;
		if (!(end)) {
			try {
				vcpChannel.open();
			} catch (SerialPortException | IOException e1) {
				e1.printStackTrace(System.err);
			}

			tabMesures = new LinkedList<Mesure>(); // mesures
													// brutes de
													// pression

			vcpInput = new BufferedReader(new InputStreamReader(vcpChannel.getReader()));
		}
		while (!stop && tm.isArduinoConnected()) {

			try {

				// Attend le debut de la lecture des donnees par le clavier
				synchronized (tm) {
					try {
						System.out.println("Waiting!");
						tm.wait();
						setEnd(false);
						System.out.print("Done waiting!");
					} catch (InterruptedException ie) {
					}
				}

				while (!end) {
					if (!Thread.interrupted()) {
						try {

							String line;
							if ((line = vcpInput.readLine()) != null) {
								insertionTab(line);
								System.out.println("Data from Arduino: " + line);
							}

						} catch (java.io.InterruptedIOException e) {
						}
					}
				}

				triTab();

			} catch (IOException e) {
				e.printStackTrace(System.err);
			}

		}

		try {
			vcpInput.close();
			vcpChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
		}

	}

	public void insertionTab(String s) {

		String[] temp = s.split("_");

		if (temp.length == 3) {

			char ident = temp[0].charAt(0);
			double p = Double.parseDouble(temp[1]);
			int cmpt = Integer.parseInt(temp[2]);

			tabMesures.add(new Mesure(cmpt, p, ident));

		}
	}

	public void triTab() {

		Iterator<Mesure> mesuresIter = tabMesures.iterator();
		LinkedList<Double> m = new LinkedList<Double>();

		int cmpt = tabMesures.getFirst().compt;
		double pres = tabMesures.getFirst().pression;
		char ident = tabMesures.getFirst().id;

		while (mesuresIter.hasNext()) {
			Mesure tempMesure = mesuresIter.next();
			if (tempMesure.compt == cmpt && tempMesure.id == ident) {
				if (tempMesure.pression >= pres) {
					pres = tempMesure.pression;
				}
			} else {
				m.add(pres);
				ident = tempMesure.id;
				pres = tempMesure.pression;
				cmpt = tempMesure.compt;
			}
		}

		m.add(pres);

		setTabTriee(new ArrayList<Double>(m));

	}

	public void afficherTabTriee() {

		for (Double m : tabTriee)
			System.out.println(m);

	}

	public void close() {
		setStop(true);
	}

	public ArrayList<Double> getTabTriee() {
		return tabTriee;
	}

	public void setTabTriee(ArrayList<Double> tabTriee) {
		this.tabTriee = tabTriee;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

}
