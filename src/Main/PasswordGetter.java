package Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordGetter {
	
	
	@SuppressWarnings("unused")
	public static String getPassword (Account account){
		ResultSet rs = Database.Request.getLogin(account.getDomainHash(),Main.conn);
		int passwordLength=0;
		boolean correctAccount = false;
		try {
			while (rs.next()){
				if(Encryption.Encryption.checkPassword(rs.getString("masterPassword"), account.getPasswordAsString())){
					if(account.getLoginHash() == Integer.parseInt(rs.getString("Login"))){
						correctAccount = true;
						passwordLength =(int)(Encryption.Encryption.decryptValue(rs.getString("passwordLength"), account.getPasswordAsString()));
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return generatePassword(account,passwordLength);
	}
	
	public static   String generatePassword(Account account,int passwordLength){
		Byte[] loginByte = new Byte[account.getLogin().getBytes().length];
		Byte[] mpByte = new Byte [account.getPasswordAsString().getBytes().length];
		Byte[] domainByte = new Byte [account.getDomain().getBytes().length];
		for (int i =0; i<loginByte.length;i++){
			loginByte[i] = new Byte(account.getLogin().getBytes()[i]);
		}
		for (int i =0; i<mpByte.length;i++){
			mpByte[i] = new Byte(account.getPasswordAsString().getBytes()[i]);
		}
		for (int i =0; i<domainByte.length;i++){
			domainByte[i] = new Byte(account.getDomain().getBytes()[i]);
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
