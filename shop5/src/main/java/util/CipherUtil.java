package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CipherUtil {
	
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

}
