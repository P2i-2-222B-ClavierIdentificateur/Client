package Exception;

import Warnings.SimpleWarning;

@SuppressWarnings("serial")
public class AccountAlreadyExistsException extends Exception{
	
	public AccountAlreadyExistsException(){
		new SimpleWarning("Ce compte existe déja");
	}
}
