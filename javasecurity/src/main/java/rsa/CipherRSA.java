package rsa;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
/**
 * RSA : 공개키 암호 알고리즘, 비대칭키
 * 공개키로 암호화 -> 개인키로 복호화 가능 : 기밀성
 * 개인키로 암호화 -> 공개키로 복호화 가능 : 부인 방비
 * @author dhtmd
 *
 */
public class CipherRSA {
	static Cipher cipher;
	static PrivateKey priKey; // 개인키
	static PublicKey pubKey; // 공개키
	static {
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
			key.initialize(2048);
			KeyPair keyPair = key.genKeyPair();
			priKey = keyPair.getPrivate();
			pubKey = keyPair.getPublic();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String encrypt(String plain) {
		byte[] cipherMsg = new byte[1024];
		try {
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			cipherMsg = cipher.doFinal(plain.getBytes());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return byteToHex(cipherMsg);
	}
	
	
	public static String decrypt(String cipherMsg) {
		byte[] plainMsg = new byte[1024];
		try {
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			plainMsg = cipher.doFinal(hexToByte(cipherMsg.trim()));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new String(plainMsg).trim();
	}
	private static String byteToHex(byte[] cipherMsg) {
		if (cipherMsg == null) {
			return null;
		}
		String str = "";
		for (byte b : cipherMsg) {
			str += String.format("%02X", b);
		}
		return str;
	}
	
	
	private static byte[] hexToByte(String str) {
		if (str == null || str.length() < 2) {
			return null;
		}
		int len = str.length() / 2;
		byte[] buf = new byte[len];
		for (int i = 0; i < len; i++) {
			buf[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
		}
		return buf;
	}
	
	public static void getKey() {
		try {
			KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
			key.initialize(2048);
			KeyPair keyPair = key.generateKeyPair();
			PrivateKey priKey = keyPair.getPrivate();
			PublicKey pubKey = keyPair.getPublic();
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("privatekey.ser"));
			out.writeObject(priKey);
			out.flush();
			out.close();
			out = new ObjectOutputStream(new FileOutputStream("publickey.ser"));
			out.writeObject(pubKey);
			out.flush();
			out.close();			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
