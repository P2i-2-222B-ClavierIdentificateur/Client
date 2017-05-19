package KeystrokeMeasuring;

import java.util.ArrayList;

import Encryption.Encryption;
import Main.Password;

public class KeyStroke extends Key {
	
	private double pressure;
	private int modifierSequence;
	private char c;
	private Modifier shift, ctrl, alt, capsLock;
	
	public KeyStroke(char c, long timeUp, long timeDown){
		super(timeUp, timeDown);
		this.setC(c);
	}
	
	public KeyStroke(ArrayList<String> encryptedValues, Password p){
		super(Encryption.decryptLong(encryptedValues.get(0), new String(p.getPassword())), Encryption.decryptLong(encryptedValues.get(0), new String(p.getPassword())));
		setPressure(Encryption.decryptValue(encryptedValues.get(2), new String(p.getPassword())));
		setModifierSequence(Encryption.decryptInt(encryptedValues.get(3), new String(p.getPassword())));
		long tempDown=Encryption.decryptLong(encryptedValues.get(5), new String(p.getPassword()));
		if(tempDown>=0){
			setShift(new Modifier(Encryption.decryptLong(encryptedValues.get(4), new String(p.getPassword())), tempDown,Encryption.decryptInt(encryptedValues.get(6), new String(p.getPassword()))));
		} else setShift(null);
		tempDown=Encryption.decryptLong(encryptedValues.get(8), new String(p.getPassword()));
		if(tempDown>=0){
			setCtrl(new Modifier(Encryption.decryptLong(encryptedValues.get(7), new String(p.getPassword())), tempDown,Encryption.decryptInt(encryptedValues.get(9), new String(p.getPassword()))));
		} else setCtrl(null);
		tempDown=Encryption.decryptLong(encryptedValues.get(11), new String(p.getPassword()));
		if(tempDown>=0){
			setAlt(new Modifier(Encryption.decryptLong(encryptedValues.get(10), new String(p.getPassword())), tempDown,Encryption.decryptInt(encryptedValues.get(12), new String(p.getPassword()))));
		} else setAlt(null);
		tempDown=Encryption.decryptLong(encryptedValues.get(14), new String(p.getPassword()));
		if(tempDown>=0){
			setCapsLock(new Modifier(Encryption.decryptLong(encryptedValues.get(13), new String(p.getPassword())), tempDown));
		} else setCapsLock(null);
		
	}
	
	@Override
	public ArrayList<String> getEncryptedValues(Password p){
		ArrayList<String> encryptedValues = super.getEncryptedValues(p);
		encryptedValues.add(Encryption.encryptValue(pressure,new String(p.getPassword())));
		encryptedValues.add(Encryption.encryptInt(modifierSequence,new String(p.getPassword())));
		if(shift!=null)
			encryptedValues.addAll(shift.getEncryptedValues(p));
		if(ctrl!=null)
			encryptedValues.addAll(ctrl.getEncryptedValues(p));
		if(alt!=null)
			encryptedValues.addAll(alt.getEncryptedValues(p));
		if(capsLock!=null)
			encryptedValues.addAll(capsLock.getEncryptedValues(p));
		return encryptedValues;
	}
	
	public static char[] getChars(ArrayList<KeyStroke> keyStrokes){
		char[] chars = new char[keyStrokes.size()];
		for(int i=0; i<chars.length; i++){
			chars[i]=keyStrokes.get(i).getC();
		}
		return chars;	
	}
	
	public double euclidianDistance (KeyStroke k){
		double dTimeUp = Math.pow(k.getTimeUp()-this.getTimeUp(), 2);
		double dTimeDown = Math.pow(k.getTimeDown()-this.getTimeDown(), 2);
		double dPressure = Math.pow(k.getPressure()-this.getPressure(), 2);
		double dShiftUp = Math.pow(k.getShift().getTimeUp()-this.getShift().getTimeUp(), 2);
		double dShiftDown = Math.pow(k.getShift().getTimeDown()-this.getShift().getTimeDown(), 2);
		double dShiftLocation = Math.pow(k.getShift().getLocation()-this.getShift().getLocation(), 2); //pas sûr qu'une distance euclidienne soit adaptée
		double dCtrlUp = Math.pow(k.getCtrl().getTimeUp()-this.getCtrl().getTimeUp(), 2);
		double dCtrlDown = Math.pow(k.getCtrl().getTimeDown()-this.getCtrl().getTimeDown(), 2);
		double dCtrlLocation = Math.pow(k.getCtrl().getLocation() - this.getCtrl().getLocation(), 2); //idem
		double dAltUp = Math.pow(k.getAlt().getTimeUp()-this.getAlt().getTimeUp(), 2);
		double dAltDown = Math.pow(k.getAlt().getTimeDown()-this.getAlt().getTimeDown(), 2);
		double dAltLocation = Math.pow(k.getAlt().getLocation()-this.getAlt().getLocation(), 2); //idem
		double dCapslockUp = Math.pow(k.getCapsLock().getTimeUp()-this.getCapsLock().getTimeUp(), 2);
		double dCapslockDown = Math.pow(k.getCapsLock().getTimeDown()-this.getCapsLock().getTimeDown(), 2);

		return Math.sqrt(dTimeUp+dTimeDown+dPressure+dShiftUp+dShiftDown+dShiftLocation+dCtrlUp+dCtrlDown+dCtrlLocation
				+dAltUp+dAltDown+dAltLocation+dCapslockUp+ dCapslockDown);

	}
	
	public double manhattanDistance(KeyStroke k){
		double dTimeUp = Math.abs(k.getTimeUp()-this.getTimeUp());
		double dTimeDown = Math.abs(k.getTimeDown()-this.getTimeDown());
		double dPressure = Math.abs(k.getPressure()-this.getPressure());
		double dShiftUp = Math.abs(k.getShift().getTimeUp()-this.getShift().getTimeUp());
		double dShiftDown = Math.abs(k.getShift().getTimeDown()-this.getShift().getTimeDown());
		double dShiftLocation = Math.abs(k.getShift().getLocation()-this.getShift().getLocation());
		double dCtrlUp = Math.abs(k.getCtrl().getTimeUp()-this.getCtrl().getTimeUp());
		double dCtrlDown = Math.abs(k.getCtrl().getTimeDown()-this.getCtrl().getTimeDown());
		double dCtrlLocation = Math.abs(k.getCtrl().getLocation() - this.getCtrl().getLocation());
		double dAltUp = Math.abs(k.getAlt().getTimeUp()-this.getAlt().getTimeUp());
		double dAltDown = Math.abs(k.getAlt().getTimeDown()-this.getAlt().getTimeDown());
		double dAltLocation = Math.abs(k.getAlt().getLocation()-this.getAlt().getLocation());
		double dCapslockUp = Math.abs(k.getCapsLock().getTimeUp()-this.getCapsLock().getTimeUp());
		double dCapslockDown = Math.abs(k.getCapsLock().getTimeDown()-this.getCapsLock().getTimeDown());

		return (dTimeUp+dTimeDown+dPressure+dShiftUp+dShiftDown+dShiftLocation+dCtrlUp+dCtrlDown+dCtrlLocation
				+dAltUp+dAltDown+dAltLocation+dCapslockUp+dCapslockDown);
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public int getModifierSequence() {
		return modifierSequence;
	}

	public void setModifierSequence(int modifierSequence) {
		this.modifierSequence = modifierSequence;
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}
	public Modifier getShift() {
		return shift;
	}

	public void setShift(Modifier shift) {
		this.shift = shift;
	}

	public Modifier getCtrl() {
		return ctrl;
	}

	public void setCtrl(Modifier ctrl) {
		this.ctrl = ctrl;
	}

	public Modifier getAlt() {
		return alt;
	}

	public void setAlt(Modifier alt) {
		this.alt = alt;
	}

	public Modifier getCapsLock() {
		return capsLock;
	}

	public void setCapsLock(Modifier capsLock) {
		this.capsLock = capsLock;
	}	
}
