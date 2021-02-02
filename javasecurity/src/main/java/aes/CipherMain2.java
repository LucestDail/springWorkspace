package aes;

public class CipherMain2 {
	public static void main(String[] args) {
		String plain1 = "admin@admin";
		String key = "admin";
		String cipher1 = CipherUtil.encrypt(plain1,key);
		System.out.println("암호문 : " + cipher1);
		String cipher2 = CipherUtil.decrypt(cipher1,key);
		System.out.println("복호문 : " + cipher2);
	}
}
