package TOTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class CodeTester {

	private final static String testCode = "59177e2512375d9c37c0210708fb89d4e9a17f5";

	public static boolean testCode(int code, String key) {
		DataOutputStream os = null;
		InputStreamReader is = null;
		try {
			Socket socket = new Socket("92.222.25.158", 8189);
			os = new DataOutputStream(socket.getOutputStream());
			is = new InputStreamReader(socket.getInputStream());
			System.out.println("client done");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			os.writeBytes(testCode + "," + code + "," + key + "\n");
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(is);
		String result = null;
		try {
			while ((result = br.readLine()) == null) {

			}
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Boolean.valueOf(result);
	}

}