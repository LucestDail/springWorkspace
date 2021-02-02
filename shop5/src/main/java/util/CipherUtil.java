package util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CipherUtil {
	private static byte[] randomKey;
	private final static byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, (byte) 0x5A,
			(byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, (byte) 0x5A };
	static Cipher cipher;
	static {
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 암호화 알고리즘 부분 / 블럭암호화 모드 / PKCS5 Padding 패딩
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String makehash(String plain) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] pbyte = plain.getBytes();
		byte[] hash = md.digest(pbyte);
		return byteToHex(hash);
	}
	
	private String byteToHex(byte[] hash) {
		if(hash == null) {
			return null;
		}
		String str = "";
		for(byte b : hash) {
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
	
	private static byte[] makeKey(String key) {
		return key.substring(0,16).getBytes();
	}

	public String encrypt(String key, String email) {
		byte[] cipherMsg = new byte[1024];
		try {
			Key genKey = new SecretKeySpec(makeKey(key), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); // 초기화 백터 설정
			cipher.init(Cipher.ENCRYPT_MODE, genKey, paramSpec);
			cipherMsg = cipher.doFinal(email.getBytes());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return byteToHex(cipherMsg);
	}

	public String decrypt(String key, String email) {
		byte[] plainMsg = new byte[1024];
		try {
			Key genKey = new SecretKeySpec(makeKey(key),"AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, genKey, paramSpec);
			plainMsg = cipher.doFinal(hexToByte(email.trim()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new String(plainMsg).trim();
	}

}
