package aes;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {
	private static byte[] randomKey;
	// initiative vector : 초기화 백터, CBC(cipher block chain) 블럭 암호화 부분에서 앞블럭의 암호문을
	// 다음블럭 암호화할때 삽입됨, 암호 블럭이 계속 연결됨(CBC 모드다~~)
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

	public static byte[] getRandomKey(String algo) throws NoSuchAlgorithmException {
		// AES 용 키 생성 객체
		KeyGenerator keyGen = KeyGenerator.getInstance(algo);
		// 128비트 키 생성하기.
		keyGen.init(128); // 128 비트 ~ 196 비트 키 유동적으로 설정 가능
		SecretKey key = keyGen.generateKey(); // 랜덤키 생성
		return key.getEncoded(); // <- 바이트형 배열로 만들어줌
	}

	//암호화 메소드
	public static String encrypt(String plain) {
		byte[] cipherMsg = new byte[1024];
		try {
			randomKey = getRandomKey("AES"); // 암호화에 사용된 키 설정, 복호화에서 사용되어야함
			Key key = new SecretKeySpec(randomKey, "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); // 초기화 백터 설정
			// Cipher.ENCRYPT_MODE : 암호화 설정(AES 알고리즘, CBC 모드, PSCS5Padding)
			// key : 키 설정
			// paramSpec : 초기화 백터
			cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			cipherMsg = cipher.doFinal(plain.getBytes()); // 암호화 실행
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byteToHex(cipherMsg).trim(); // 16진수코드값의 문자열 반환
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

	//복호화 메소드
	public static String decrypt(String cipherMsg) {
		byte[] plainMsg = new byte[1024];
		try {
			//복호화 키
			Key key = new SecretKeySpec(randomKey, "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			// Cipher.DECRYPT_MODE : 복호화 설정(AES 알고리즘, CBC 모드, PSCS5Padding)
			// key : 키 설정
			// paramSpec : 초기화 백터
			cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			plainMsg = cipher.doFinal(hexToByte(cipherMsg.trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(plainMsg).trim();
	}
	//16진수 문자열 값을 바이트형 배열 형태로 원복
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
	//키 설정 후 암호화
	public static String encrypt(String plain, String key) {
		// TODO Auto-generated method stub
		byte[] cipherMsg = new byte[1024];
		try {
			Key genKey = new SecretKeySpec(makeKey(key), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); // 초기화 백터 설정
			cipher.init(Cipher.ENCRYPT_MODE, genKey, paramSpec);
			cipherMsg = cipher.doFinal(plain.getBytes());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return byteToHex(cipherMsg);
	}
	//128 비트 = 16바이트
	private static byte[] makeKey(String key) {
		//key : abc1234567
		int len = key.length();
		char ch = 'A';
		for(int i = len; i < 16; i++) {
			key += ch++;
		}
		return key.substring(0,16).getBytes();
	}

	public static String decrypt(String cipherMsg, String key) {
		// TODO Auto-generated method stub
		byte[] plainMsg = new byte[1024];
		try {
			Key genKey = new SecretKeySpec(makeKey(key),"AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, genKey, paramSpec);
			plainMsg = cipher.doFinal(hexToByte(cipherMsg.trim()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new String(plainMsg).trim();
	}

	public static String makehash(String msg) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] plain = msg.getBytes();
		byte[] hash = md.digest(plain);
		return byteToHex(hash);
	}

}
