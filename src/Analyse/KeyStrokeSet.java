package Analyse;

import java.util.Iterator;
import java.util.LinkedList;

import KeystrokeMeasuring.KeyStroke;

public class KeyStrokeSet {
	
	private LinkedList<KeyStroke> set;
	
	public KeyStrokeSet(LinkedList<KeyStroke> set){
		this.setSet(new LinkedList<KeyStroke>(set));
		Iterator<KeyStroke> itr = set.iterator();
		KeyStroke cur=set.getFirst();
		KeyStroke next;
		while (itr.hasNext()){
			next = itr.next();
			cur.setNext(next);
			cur=next;
		}
	}

	public LinkedList<KeyStroke> getSet() {
		return set;
	}

	public void setSet(LinkedList<KeyStroke> set) {
		this.set=new LinkedList<KeyStroke>(set);
	}

}
