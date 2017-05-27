package KeystrokeMeasuring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import Encryption.Encryption;
import Main.Password;
import Warnings.SimpleWarning;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

public class KeyStroke extends Key {
	
	private double pressure;
	private int modifierSequence;
	private char c;
	private Modifier shift, ctrl, alt, capsLock;
	private KeyStroke next;
	
	public KeyStroke(char c, long timeUp, long timeDown){
		super(timeUp, timeDown);
		this.setC(c);
		this.setNext(null);
	}
	
	public KeyStroke(ArrayList<String> encryptedValues, Password p) throws EncryptionOperationNotPossibleException {
		
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
	
	public double getNorme2 (){
		double[] values = getValuesArray();
		double n = 0;
		for(int i=0; i<values.length; i++){
			n += Math.pow(values[i], 2);
		}
		return Math.sqrt(n);
	}
	
	public double getNorme1(){
		double[] values = getValuesArray();
		double n = 0;
		for(int i=0; i<values.length; i++){
			n += Math.abs(values[i]);
		}
		return n;
	}
	
	public double[] getValuesArray(){
		double [] values = new double[15];
		if(getNext()!=null){
			values[0] = getNext().getTimeUp() - super.getTimeUp();
			values[1] = getNext().getTimeDown()-super.getTimeDown();
			values[2] = getNext().getPressure()-getPressure();
			if(shift != null){
				values[4] = getNext().getShift().getTimeUp()-shift.getTimeUp();
				values[5] = getNext().getShift().getTimeDown()-shift.getTimeDown();
				//values[6] = shift.getLocation();
				values[6] = 0;
			}else{
				values[4] = 0;
				values[5] = 0;
				values[6] = 0;
			}
			if(ctrl!=null){
				values[7] = getNext().getCtrl().getTimeUp()-ctrl.getTimeUp();
				values[8] = getNext().getCtrl().getTimeDown()-ctrl.getTimeDown();
				//values[9] = ctrl.getLocation();
				values[9] = 0;
			}else{
				values[7] = 0;
				values[8] = 0;
				values[9] = 0;
			}
			if(alt!=null){
				values[10] = getNext().getAlt().getTimeUp()- alt.getTimeUp();
				values[11] = getNext().getAlt().getTimeDown()-alt.getTimeDown();
				//values[12] = alt.getLocation();
				values[12] =0;
			}else{
				values[10] = 0;
				values[11] = 0;
				values[12] = 0;
			}
			if(capsLock!=null){
				values[13] = getNext().getCapsLock().getTimeUp()-capsLock.getTimeUp();
				values[14] = getNext().getCapsLock().getTimeDown() - capsLock.getTimeDown();
			}else{
				values[13] = 0;
				values[14] = 0;
			}
		}
			return values;

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
		double result,dTimeUp,dTimeDown,dPressure,dShiftUp = 0,dShiftDown = 0,dShiftLocation = 0,dCtrlUp = 0,dCtrlDown = 0,dCtrlLocation = 0,
			dAltUp = 0,dAltDown = 0,dAltLocation = 0,dCapslockUp = 0,dCapslockDown = 0;
		if (k.getNext()!=null & this.next!=null){
			 dTimeUp = Math.pow((k.getNext().getTimeUp()-k.getTimeUp())-(this.getNext().getTimeUp()-this.getTimeUp()), 2);
			 dTimeDown = Math.pow((k.getNext().getTimeDown()-k.getTimeDown())-(this.getNext().getTimeDown()-this.getTimeDown()), 2);
			 dPressure = Math.pow((k.getNext().getPressure()-k.getPressure())-(this.getNext().getPressure()-this.getPressure()), 2);
			if(k.getShift()!=null && k.getNext().getShift()!=null && this.getShift()!=null && this.getNext().getShift()!=null){
				 dShiftUp = Math.pow((k.getNext().getShift().getTimeUp() - k.getShift().getTimeUp())-(this.getNext().getShift().getTimeUp()-this.getShift().getTimeUp()), 2);
				 dShiftDown = Math.pow((k.getNext().getShift().getTimeDown() - k.getShift().getTimeDown())-(this.getNext().getShift().getTimeDown()-this.getShift().getTimeDown()), 2);
				// dShiftLocation = Math.pow((k.getNext().getShift().getLocation()-k.getShift().getLocation())-(this.getNext().getShift().getLocation()-this.getShift().getLocation()), 2); //pas s�r qu'une distance euclidienne soit adapt�e
			}
			if(k.getCtrl()!=null && k.getNext().getCtrl()!=null && this.getCtrl()!=null && this.getNext().getCtrl()!=null){
				 dCtrlUp = Math.pow((k.getNext().getCtrl().getTimeUp()-k.getCtrl().getTimeUp())-(this.getNext().getCtrl().getTimeUp()-this.getCtrl().getTimeUp()), 2);
				 dCtrlDown = Math.pow((k.getNext().getCtrl().getTimeDown()-k.getCtrl().getTimeDown())-(this.getNext().getCtrl().getTimeDown()-this.getCtrl().getTimeDown()), 2);
				// dCtrlLocation = Math.pow((k.getNext().getCtrl().getLocation() - k.getCtrl().getLocation()) - (this.getNext().getCtrl().getLocation()-this.getCtrl().getLocation()), 2); //idem
			}
			if(k.getAlt()!=null && k.getNext().getAlt()!=null && this.getAlt()!=null && this.getNext().getAlt()!=null){
				 dAltUp = Math.pow((k.getNext().getAlt().getTimeUp()-k.getAlt().getTimeUp()) - (this.getNext().getAlt().getTimeUp() - this.getAlt().getTimeUp()), 2);
				 dAltDown = Math.pow((k.getNext().getAlt().getTimeDown()-k.getAlt().getTimeDown()) - (this.getNext().getAlt().getTimeDown() - this.getAlt().getTimeDown()), 2);
				// dAltLocation = Math.pow((k.getNext().getAlt().getLocation()-k.getAlt().getLocation())-(this.getNext().getAlt().getLocation() - this.getAlt().getLocation()), 2); //idem	
			}
			if(k.getCapsLock()!=null && k.getNext().getCapsLock()!=null && this.getCapsLock()!=null && this.getNext().getCapsLock()!=null){
				 dCapslockUp = Math.pow((k.getNext().getCapsLock().getTimeUp() - k.getCapsLock().getTimeUp())-(this.getNext().getCapsLock().getTimeUp()-this.getCapsLock().getTimeUp()), 2);
				 dCapslockDown = Math.pow((k.getNext().getCapsLock().getTimeDown()-k.getCapsLock().getTimeDown())-(this.getNext().getCapsLock().getTimeDown()-this.getCapsLock().getTimeDown()), 2);
			}
			result = Math.sqrt(dTimeUp+dTimeDown+dPressure+dShiftUp+dShiftDown+dShiftLocation+dCtrlUp+dCtrlDown+dCtrlLocation
					+dAltUp+dAltDown+dAltLocation+dCapslockUp+ dCapslockDown);
		}else{
			result = 0;
		}

		System.out.println(result);
		return result;

	}
	
	public double manhattanDistance(KeyStroke k){
		double result,dTimeUp,dTimeDown,dPressure,dShiftUp = 0,dShiftDown = 0,dShiftLocation = 0,dCtrlUp = 0,dCtrlDown = 0,dCtrlLocation = 0,
				dAltUp = 0,dAltDown = 0,dAltLocation = 0,dCapslockUp = 0,dCapslockDown = 0;
		if (k.getNext()!=null & this.next!=null){
			 dTimeUp = Math.abs((k.getNext().getTimeUp()-k.getTimeUp())-(this.getNext().getTimeUp()-this.getTimeUp()));
			 dTimeDown = Math.abs((k.getNext().getTimeDown()-k.getTimeDown())-(this.getNext().getTimeDown()-this.getTimeDown()));
			 dPressure = Math.abs((k.getNext().getPressure()-k.getPressure())-(this.getNext().getPressure()-this.getPressure()));
			if(k.getShift()!=null && k.getNext().getShift()!=null && this.getShift()!=null && this.getNext().getShift()!=null){

				 dShiftUp = Math.abs((k.getNext().getShift().getTimeUp() - k.getShift().getTimeUp())-(this.getNext().getShift().getTimeUp()-this.getShift().getTimeUp()));
				 dShiftDown = Math.abs((k.getNext().getShift().getTimeDown() - k.getShift().getTimeDown())-(this.getNext().getShift().getTimeDown()-this.getShift().getTimeDown()));
				// dShiftLocation = Math.abs((k.getNext().getShift().getLocation()-k.getShift().getLocation())-(this.getNext().getShift().getLocation()-this.getShift().getLocation())); //pas s�r qu'une distance euclidienne soit adapt�e
			}
			if(k.getCtrl()!=null && k.getNext().getCtrl()!=null && this.getCtrl()!=null && this.getNext().getCtrl()!=null){

				 dCtrlUp = Math.abs((k.getNext().getCtrl().getTimeUp()-k.getCtrl().getTimeUp())-(this.getNext().getCtrl().getTimeUp()-this.getCtrl().getTimeUp()));
				 dCtrlDown = Math.abs((k.getNext().getCtrl().getTimeDown()-k.getCtrl().getTimeDown())-(this.getNext().getCtrl().getTimeDown()-this.getCtrl().getTimeDown()));
				// dCtrlLocation = Math.abs((k.getNext().getCtrl().getLocation() - k.getCtrl().getLocation()) - (this.getNext().getCtrl().getLocation()-this.getCtrl().getLocation())); //idem
			}
			if(k.getAlt()!=null && k.getNext().getAlt()!=null && this.getAlt()!=null && this.getNext().getAlt()!=null){

				 dAltUp = Math.abs((k.getNext().getAlt().getTimeUp()-k.getAlt().getTimeUp()) - (this.getNext().getAlt().getTimeUp() - this.getAlt().getTimeUp()));
				 dAltDown = Math.abs((k.getNext().getAlt().getTimeDown()-k.getAlt().getTimeDown()) - (this.getNext().getAlt().getTimeDown() - this.getAlt().getTimeDown()));
				// dAltLocation = Math.abs((k.getNext().getAlt().getLocation()-k.getAlt().getLocation())-(this.getNext().getAlt().getLocation() - this.getAlt().getLocation())); //idem
			}
			if(k.getCapsLock()!=null && k.getNext().getCapsLock()!=null && this.getCapsLock()!=null && this.getNext().getCapsLock()!=null){
	
				 dCapslockUp = Math.abs((k.getNext().getCapsLock().getTimeUp() - k.getCapsLock().getTimeUp())-(this.getNext().getCapsLock().getTimeUp()-this.getCapsLock().getTimeUp()));
				dCapslockDown = Math.abs((k.getNext().getCapsLock().getTimeDown()-k.getCapsLock().getTimeDown())-(this.getNext().getCapsLock().getTimeDown()-this.getCapsLock().getTimeDown()));
			}
			
			result = (dTimeUp+dTimeDown+dPressure+dShiftUp+dShiftDown+dShiftLocation+dCtrlUp+dCtrlDown+dCtrlLocation
									+dAltUp+dAltDown+dAltLocation+dCapslockUp+ dCapslockDown);
		}else{
			result = 0;
		}
		System.out.println(result);
		return result;
}
	
	
	@Override
	public long getReleasePressTimes(){
		if(next!=null)
			return this.next.getTimeDown() - this.getTimeUp();
		else return 0;
	}
	
	@Override
	public long getReleaseReleaseTimes(){
		if(next!=null)
			return this.next.getTimeUp() - this.getTimeUp();
		else return 0;
	}
	
	/**
	 * Retourne le produit scalaire entre ce vecteur et le vecteur de reference
	 * @param ref Le vecteur de reference
	 * @return le produit scalaire
	 */
	public double getScalarProduct(KeyStroke ref){
		double scalarProduct = ref.getPressure()*this.getPressure() + ref.getPressReleaseTimes()*this.getPressReleaseTimes() + ref.getReleasePressTimes()*this.getReleasePressTimes() + ref.getReleaseReleaseTimes()*this.getReleaseReleaseTimes();
		if(ref.getShift()!=null && this.getShift()!=null)
			scalarProduct += this.getShift().getScalarProduct(ref.getShift());
		if(ref.getCtrl()!=null && this.getCtrl()!=null)
			scalarProduct += this.getCtrl().getScalarProduct(ref.getCtrl());
		if(ref.getAlt()!=null && this.getAlt()!=null)
			scalarProduct += this.getAlt().getScalarProduct(ref.getAlt());
		if(ref.getCapsLock()!=null && this.getCapsLock()!=null)
			scalarProduct += this.getCapsLock().getScalarProduct(ref.getCapsLock());
		return scalarProduct;
	}
	
	
	public double getNormSquared(){
		double normSquared = Math.pow(pressure, 2) + Math.pow(getPressReleaseTimes(), 2) + Math.pow(getReleasePressTimes(), 2) + Math.pow(getReleaseReleaseTimes(), 2);
		if(shift!=null)
			normSquared += shift.getNormSquared();
		if(ctrl!=null)
			normSquared += ctrl.getNormSquared();
		if(alt!=null)
			normSquared += alt.getNormSquared();
		if(capsLock!=null)
			normSquared += capsLock.getNormSquared();
		return normSquared;
	}
	
	/**
	 * Retourne la similarite cosinus entre cette touche et une touche de reference
	 * @param ref La touche de reference
	 * @return La similarite cosinus
	 */
	public double getCosineSimilarity(KeyStroke ref){
		return this.getScalarProduct(ref) / Math.sqrt(this.getNormSquared() * ref.getNormSquared());
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
		if(shift!=null)
		this.shift.setAssociatedKeyStroke(this);
	}

	public Modifier getCtrl() {
		return ctrl;
	}

	public void setCtrl(Modifier ctrl) {
		this.ctrl = ctrl;
		if(ctrl!=null)
		this.ctrl.setAssociatedKeyStroke(this);
	}

	public Modifier getAlt() {
		return alt;
	}

	public void setAlt(Modifier alt) {
		this.alt = alt;
		if(alt!=null)
		this.alt.setAssociatedKeyStroke(this);
	}

	public Modifier getCapsLock() {
		return capsLock;
	}

	public void setCapsLock(Modifier capsLock) {
		this.capsLock = capsLock;
		if(capsLock!=null)
		this.capsLock.setAssociatedKeyStroke(this);
	}

	public KeyStroke getNext() {
		return next;
	}

	public void setNext(KeyStroke next) {
		this.next = next;
	}	
}