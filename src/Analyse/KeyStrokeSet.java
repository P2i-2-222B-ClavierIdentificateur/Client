package Analyse;

import java.util.LinkedList;

import KeystrokeMeasuring.KeyStroke;

public class KeyStrokeSet {
	
	private LinkedList<KeyStroke> set;
	
	public KeyStrokeSet(LinkedList<KeyStroke> set){
		this.setSet(set);
	}

	public LinkedList<KeyStroke> getSet() {
		return set;
	}

	public void setSet(LinkedList<KeyStroke> set) {
		this.set = set;
	}

}
