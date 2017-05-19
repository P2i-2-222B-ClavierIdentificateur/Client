package Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * The Class PasswordGetter.
 * @author Jules
 */
public class PasswordGetter {
	
	
	/**
	 * Compare le mot de passe en paramètre avec celui de la base de donnée, si résultat positif, on récupére la 
	 * la longeur du mot de passe à  générer et on le génère..
	 * @author Jules
	 * @param password le mot de passe saisi par l'utilisateur
	 * @param login Le login saisi par l'utilisateur
	 * @param domain Le domaine saisi par l'utilisatuer
	 * @return le mot de passe généré
	 */
	public static String getPassword (String password, String login, String domain){
		ResultSet rs = Database.Request.getLogin(domain.hashCode());
		int passwordLength=0;
		boolean correctAccount = false;
		try {
			while (rs.next()){
				if(Encryption.Encryption.checkPassword(rs.getString("masterPassword"), password)){
					if(login.hashCode() == Integer.parseInt(rs.getString("Login"))){
						correctAccount = true;
						passwordLength =(int)(Encryption.Encryption.decryptValue(rs.getString("passwordLength"), password));
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return generatePassword(login,password,domain,passwordLength);
	}
	
	/**
	 * Génére un mot de passe.
	 *@author Jules
	 * @param login le Login
	 * @param masterPassword le mot de passe maître
	 * @param domaine le domaine
	 * @param passwordLength La longeur du mot de passe à générer
	 * @return le mot de passe généré
	 */
	public static   String generatePassword(String login, String masterPassword, String domaine,int passwordLength){
		Byte[] loginByte = new Byte[login.getBytes().length];
		Byte[] mpByte = new Byte [masterPassword.getBytes().length];
		Byte[] domainByte = new Byte [domaine.getBytes().length];
		for (int i =0; i<loginByte.length;i++){
			loginByte[i] = new Byte(login.getBytes()[i]);
		}
		for (int i =0; i<mpByte.length;i++){
			mpByte[i] = new Byte(masterPassword.getBytes()[i]);
		}
		for (int i =0; i<domainByte.length;i++){
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
