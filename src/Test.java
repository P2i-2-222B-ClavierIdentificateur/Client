import Encryption.Encryption;

public class Test {
	
	public static void main (String[] args){
		System.out.println(Encryption.encryptText("test","password"));
		System.out.println(Encryption.decryptText(Encryption.encryptText("test","password"),"password"));
		
	}
	
	public static  String generatePassword(String login, String masterPassword, String domaine){
		int passwordLength = 50;
		String password = login+domaine+masterPassword;
		Byte[] loginByte = new Byte[login.getBytes().length];
		Byte[] mpByte = new Byte [masterPassword.getBytes().length];
		Byte[] domainByte = new Byte [domaine.getBytes().length];
		for (int i =0; i<loginByte.length;i++){
			loginByte[i] = new Byte(login.getBytes()[i]);
		}
		for (int i =0; i<mpByte.length;i++){
			mpByte[i] = new Byte(masterPassword.getBytes()[i]);
		}
		for (int i =0; i<loginByte.length;i++){
			domainByte[i] = new Byte(domaine.getBytes()[i]);
		}
		int[] passwordInt = new int[passwordLength];
		int j = 0;
		while(j<passwordLength){
			for (int i=0;i<Math.max(loginByte.length,Math.max(mpByte.length,domainByte.length));i++){
				if (j<passwordLength){
					passwordInt[j] += loginByte[i%(loginByte.length-1)].intValue();
					j++;
				}
				if (j<passwordLength){
					passwordInt[j] += mpByte[i%(mpByte.length-1)].intValue();
					j++;
				}
				if (j<passwordLength){
					passwordInt[j] += domainByte[i%(domainByte.length-1)].intValue();
					j++;
				}
			}
		}
		
		char [] passwordChar = new char[passwordLength];
		for(int i=0; i<passwordLength; i++){
			passwordChar[i] = (char)(48+((passwordInt[i]*i)%78));
		}
		return new String(passwordChar);
	}

}
