package TOTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class KeyGetter extends Thread{
	
	Socket socket;
	DataOutputStream os;
	InputStreamReader is;

	private String requestString = "6eb7d8e08a8d97e647bf520f1b7e8e5fde0af0b8";
	private final String testCode = "59177e2512375d9c37c0210708fb89d4e9a17f5";

	private String key;
	public KeyGetter() {
		try {
			socket = new Socket("92.222.25.158", 8189);
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
		// if (socket != null && os != null && is != null) {

		// }

	}

	public void run() {
		try {
			os.writeBytes(requestString + "\n");
			os.flush();
			System.out.println("sent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean keyReceived = false;
		String key = null;

		while (!keyReceived) {
			BufferedReader br = new BufferedReader(is);
			try {
				key = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (key != null) {
				keyReceived = true;
				this.setKey(key);
				System.out.println(key);
			}
		}
		
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


}