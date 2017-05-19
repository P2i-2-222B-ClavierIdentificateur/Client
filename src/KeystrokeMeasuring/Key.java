package KeystrokeMeasuring;

import java.util.ArrayList;

import Encryption.Encryption;
import Main.Password;

public abstract class Key {
	private long timeUp, timeDown;
	
	public Key(long timeUp, long timeDown){
		this.setTimeUp(timeUp);
		this.setTimeDown(timeDown);
	}
	
	public long getTimeUp() {
		return timeUp;
	}
	
	public ArrayList<String> getEncryptedValues(Password p){
		ArrayList<String> encryptedValues = new ArrayList<String>();
		encryptedValues.add(Encryption.encryptLong(timeUp, new String(p.getPassword())));
		encryptedValues.add(Encryption.encryptLong(timeDown, new String(p.getPassword())));
		return encryptedValues;
	}

	public void setTimeUp(long timeUp) {
		this.timeUp = timeUp;
	}
	
	public long getTimeDown() {
		return timeDown;
	}

	public void setTimeDown(long timeDown) {
		this.timeDown = timeDown;
	}
}
